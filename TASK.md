# CitiLink — V1 Implementation Task List (TASK.md)

This document provides a granular, phase-by-phase engineering task breakdown for building the complete CitiLink platform (FastAPI Backend, PostgreSQL/PostGIS, Citizen Android App, Conductor Android App, and Admin Web Dashboard).

---

## Phase 1: Foundation, Database & Core Backend API

- [x] **Task 1.1: Project Setup & Repository Scaffold**
  - [x] Initialize Python FastAPI project with Poetry/uv or Pipenv (`backend/`).
  - [x] Configure PostgreSQL + PostGIS database connection with SQLAlchemy 2.0 (asyncio) and Alembic migrations.
  - [x] Configure environment variables (`.env`), CORS, logging, and Pytest test harness.
  - [x] Acceptance: Server boots cleanly, connects to PostgreSQL, passes health check `GET /api/v1/health`.

- [ ] **Task 1.2: Database Models & Migrations**
  - [ ] Implement SQLAlchemy models for `User`, `ConductorProfile`, `Stop`, `Route`, `RouteStop`, `Bus`, `TripRun`, `Ticket`, `PinAllocation`, `BoardingEvent`, and `BusGpsLog`.
  - [ ] Implement Alembic migration scripts and verify PostgreSQL ENUMs and spatial indices.
  - [ ] Create seed data script (`seeds/seed_nashik_network.py`) populating Nashik Route 12 (CBS ➔ Ashok Stambh ➔ Ravivar Karanja ➔ Panchavati) with 10 stops, 4 buses, and test conductors.
  - [ ] Acceptance: Database seeds cleanly and passes integrity validation tests.

- [ ] **Task 1.3: Authentication & User Management**
  - [ ] Implement JWT authentication for Citizens (Phone OTP login simulation/provider).
  - [ ] Implement Conductor authentication (Employee ID + Secure PIN).
  - [ ] Implement Role-Based Access Control (RBAC) middleware for `CITIZEN`, `CONDUCTOR`, and `ADMIN`.
  - [ ] Acceptance: Unit tests verifying token creation, renewal, and unauthorized route protection.

---

## Phase 2: Route-Corridor PIN Engine & Ticketing Subsystem

- [ ] **Task 2.1: Multi-Bus Route Corridor PIN Allocator**
  - [ ] Implement `PinAllocatorService` managing separate pools:
    - Senior Citizen pool (`00` to `99`, 100 codes).
    - Standard Citizen pool (`100` to `999`, 900 codes).
  - [ ] Implement concurrency-safe allocation using PostgreSQL row locking (`SELECT FOR UPDATE SKIP LOCKED`).
  - [ ] Add route corridor collision checks ensuring a PIN is never double-allocated across active unboarded tickets on the same route.
  - [ ] Add string formatter ensuring leading zeros (`07`) are never lost.
  - [ ] Acceptance: Stress test generating 500 concurrent PIN allocations on Route 12 without collisions.

- [ ] **Task 2.2: Fare Calculation & Ticket Booking Engine**
  - [ ] Implement stage-based and distance-based fare calculation service (`RouteStop.fare_from_origin` matrix).
  - [ ] Implement `POST /api/v1/tickets/book` endpoint:
    - Validates route, boarding stop, destination stop, and passenger count.
    - Reserves corridor Travel PIN.
    - Creates `TICKET` record with status `BOOKED`.
  - [ ] Implement Razorpay payment order generation and webhook/verify endpoint `POST /api/v1/tickets/verify-payment` transitioning ticket to `PAID`.
  - [ ] Acceptance: Complete booking lifecycle test from booking to paid ticket with PIN.

- [ ] **Task 2.3: Atomic Bus Binding & Conductor Verification Engine**
  - [ ] Implement `POST /api/v1/conductor/verify-pin` endpoint:
    - Resolves Travel PIN against the conductor's assigned route corridor.
    - Returns passenger details, group count, and payment badge.
  - [ ] Implement `POST /api/v1/conductor/board` endpoint:
    - Atomically binds the ticket to the specific `trip_run_id` of the boarding bus.
    - Updates ticket status to `BOARDED`.
    - Logs `BoardingEvent` with client timestamp.
    - Increments bus occupancy by `passenger_count`.
    - Prevents any subsequent boarding attempt on another bus with error `ALREADY_BOARDED`.
  - [ ] Acceptance: Concurrency test boarding a single PIN on Bus 101, rejecting simultaneous board attempt on Bus 102.

---

## Phase 3: GPS Telemetry, Stop Progression & Segment Occupancy

- [ ] **Task 3.1: GPS Ingestion & Geofencing Worker**
  - [ ] Implement `POST /api/v1/gps/telemetry` endpoint for bus telemetry ingestion.
  - [ ] Implement spatial geofencing algorithm (PostGIS ST_DWithin) matching bus coordinates to route stops ($R = 35\text{m}$).
  - [ ] Update `TripRun.current_stop_sequence` automatically as bus enters and exits stop geofences.
  - [ ] Acceptance: Simulated GPS track advances bus along Route 12 stops sequentially.

- [ ] **Task 3.2: Sensorless Segment Occupancy Engine**
  - [ ] Implement dynamic occupancy calculator:
    - Aggregates all `BOARDED` digital tickets where `from_seq <= current_seq < to_seq`.
    - Aggregates all active cash tickets for the current segment.
  - [ ] Implement automated alighting: when bus passes a ticket's `to_stop_sequence`, transition ticket to `COMPLETED` and release seat.
  - [ ] Implement citizen availability classification (🟢 Good $\le 60\%$, 🟡 Limited $60\text{--}90\%$, 🔴 Full $> 90\%$).
  - [ ] Acceptance: Unit tests verifying occupancy increment on boarding and decrement on passing destination.

- [ ] **Task 3.3: Background No-Show Automation Daemon**
  - [ ] Implement scheduled background worker (APScheduler / Celery / FastAPI task).
  - [ ] Identify `PAID` tickets where all corridor buses have passed the boarding stop by $+2$ stops.
  - [ ] Mark tickets as `NO_SHOW`, release reserved PIN back to the pool, and trigger refund policy if applicable.
  - [ ] Acceptance: Test verifying automatic transition of abandoned tickets to `NO_SHOW`.

---

## Phase 4: Conductor Android Mobile Application

- [ ] **Task 4.1: Project Setup & Architecture Scaffold**
  - [ ] Initialize Android Kotlin project (`apps/conductor-android/`) with Jetpack Compose, Material 3, Navigation Compose, and Hilt DI.
  - [ ] Setup Room SQLite local database and DataStore preferences.
  - [ ] Implement Network layer with Retrofit, OkHttp, and offline interceptors.

- [ ] **Task 4.2: Conductor Authentication & Trip Setup UI**
  - [ ] Build Conductor Login screen (Employee ID + PIN Keypad).
  - [ ] Build Trip Start screen: Select Bus Number (`MH-15-AB-1234`), Route (`Route 12`), and Direction.
  - [ ] Download and cache active Route Corridor Manifest on `[START TRIP]`.
  - [ ] Acceptance: Conductor can log in, select trip, and load manifest locally.

- [ ] **Task 4.3: High-Speed Numeric Keypad & PIN Verification UI**
  - [ ] Build high-contrast 3x4 numeric keypad Compose component with audio haptic feedback.
  - [ ] Implement sub-50ms local Room lookup on 3-digit/2-digit PIN input.
  - [ ] Build Passenger Confirmation Modal displaying Name, Pax Count, Origin ➔ Dest, Fare Paid.
  - [ ] Implement one-tap `[BOARD]` action updating local occupancy counter.
  - [ ] Acceptance: Benchmarked PIN entry to board confirmation in $< 2$ seconds.

- [ ] **Task 4.4: Quick Cash Boarding (2-Tap Workflow)**
  - [ ] Build Quick Cash Boarding tab:
    - Ordered destination stop grid based on remaining route stops.
    - Pax count selector (`1`, `2`, `3`, `4+`).
    - Large green `[CASH BOARD ₹XX]` button.
  - [ ] Record anonymous cash ticket into local Room DB and increment occupancy.
  - [ ] Acceptance: Cash ticket issuance completed in $< 3$ seconds.

- [ ] **Task 4.5: Offline Sync Engine & Fallback Tools**
  - [ ] Implement `OfflineSyncWorker` (Android WorkManager) that detects network reconnection and flushes pending `local_events` to `POST /api/v1/conductor/sync`.
  - [ ] Integrate Camera QR Scanner (ML Kit) for backup QR validation.
  - [ ] Build Passenger Search fallback (search by Phone / Name / Ticket ID).
  - [ ] Acceptance: App validates tickets and issues cash tickets while in Airplane Mode, syncing seamlessly upon reconnection.

---

## Phase 5: Citizen Android Mobile Application

- [ ] **Task 5.1: Project Setup & UI Theme Scaffold**
  - [ ] Initialize Android Kotlin project (`apps/citizen-android/`) with Jetpack Compose, Material 3, and Hilt.
  - [ ] Implement CitiLink premium design system (Vibrant transit colors, sleek dark/light mode, smooth animations).

- [ ] **Task 5.2: Stop & Route Discovery Flow**
  - [ ] Implement Location Services (FusedLocationProvider) to auto-detect nearest bus stops.
  - [ ] Build Origin & Destination Search screen with auto-complete and recent routes.
  - [ ] Build Route Results screen showing connecting routes, upcoming bus arrivals, and live segment occupancy badges (🟢 Good, 🟡 Limited, 🔴 Full).

- [ ] **Task 5.3: "Where Is My Bus?" Live Radar Map**
  - [ ] Integrate MapLibre / OpenStreetMap Compose SDK.
  - [ ] Render bus route polyline and stop pins.
  - [ ] Stream live bus markers with real-time ETA countdown and stop progress bar.
  - [ ] Acceptance: Citizen can track incoming buses on the map with smooth marker animation.

- [ ] **Task 5.4: Booking, Payment & Active Travel PIN Card**
  - [ ] Build Ticket Booking screen (Passenger count, Senior Citizen toggle, Fare summary).
  - [ ] Integrate Razorpay SDK for instant UPI / card checkout.
  - [ ] Build Active Ticket Screen:
    - Prominent bold PIN Display (`142` or `42`).
    - Clear instructions: *"Show or speak this PIN to the conductor on ANY Route 12 bus."*
    - Dynamic backup QR code.
    - Live journey tracker updating stop-by-stop once boarded.
  - [ ] Acceptance: End-to-end booking flow yielding active PIN card.

- [ ] **Task 5.5: Citizen Registration, Phone OTP & Senior KYC Profile**
  - [ ] Build Phone Number Login & Registration modal with 6-digit OTP verification simulation.
  - [ ] Implement Senior Citizen (60+) KYC verification enabling automated 2-digit PIN routing.
  - [ ] Implement Secure JWT Token caching and dynamic Dark Mode / Light Mode theme switching.


---

## Phase 6: Admin Web Portal & Fleet Analytics

- [ ] **Task 6.1: Admin Dashboard Scaffold**
  - [ ] Initialize React + Vite + TypeScript project (`web/admin-portal/`) with Tailwind CSS and Lucide icons.
  - [ ] Setup React Router, TanStack Query, and Zustand state management.
  - [ ] Implement Admin Authentication and secure route layout.

- [ ] **Task 6.2: Route, Fleet & Conductor Management**
  - [ ] Build Stops & Route Polyline Editor (Interactive map to place stops, define sequences, and fares).
  - [ ] Build Bus Fleet Management (Add buses, configure seated/standing capacities, assign GPS device IMEIs).
  - [ ] Build Conductor Management (Register conductors, assign shifts and routes).

- [ ] **Task 6.3: Live Corridor Radar & Revenue Analytics**
  - [ ] Build Live Corridor Map displaying all active buses, current occupancies, and speeds in real time.
  - [ ] Build Revenue & Passenger Volume Dashboard (Digital vs Cash revenue breakdown, peak load graphs, route utilization metrics).
  - [ ] Acceptance: Admin can monitor live bus movements and download trip audit reports.

---

## Phase 7: End-to-End Integration, Field Testing & Polish

- [ ] **Task 7.1: End-to-End Corridor Test Scenario**
  - [ ] Simulate 3 buses running simultaneously on Route 12 (Bus 101, 102, 103).
  - [ ] Book 5 passenger tickets (standard + senior).
  - [ ] Board passengers on different arriving buses using the same route-level PIN pool.
  - [ ] Verify atomic binding, occupancy recalculation, and no double-boarding conflicts.

- [ ] **Task 7.2: Resilience & Network Chaos Testing**
  - [ ] Test Conductor app operating in disconnected mode for 30 minutes, recording 50 boardings and cash sales.
  - [ ] Reconnect network and verify zero data loss and correct backend state reconciliation.

- [ ] **Task 7.3: Performance & Load Testing**
  - [ ] Run Locust / k6 load tests simulating 2,000 requests/sec on PIN verification and GPS ingestion.
  - [ ] Verify $< 150\text{ms}$ p95 response time.

- [ ] **Task 7.4: Decommission & Cleanup of Client Mock Data Engines**
  - [ ] Refactor `CitiLinkRepository` and mobile data sources to strictly decouple temporary in-memory mock datasets (`NashikTransitData.kt`).
  - [ ] Deprecate hardcoded simulator triggers once live PostgreSQL PostGIS telemetry feeds and real WebSocket/HTTP streams are operational.
  - [ ] Verify production release builds enforce live backend endpoints and Room SQLite caching with zero demo/mock test artifacts.

