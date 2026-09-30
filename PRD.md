# CitiLink — Product Requirements Document (PRD)

| Document Version | 1.0.0 |
| :--- | :--- |
| **Product Name** | CitiLink |
| **Category** | Intelligent Public Transportation & Digital Mobility |
| **Target Cities** | Tier 1/2/3 Cities (e.g., Nashik, Pune, Indore, Ahmedabad) |
| **Status** | Approved for V1 Engineering Implementation |

---

# 1. Executive Summary & Vision

Public bus transit systems in Indian cities face a dual dilemma:
1. **Citizens** suffer from severe transit uncertainty—not knowing when the next bus will arrive, whether it will have seats, and facing long ticket-counter queues or loose cash disputes.
2. **Conductors & Transit Authorities** struggle with slow manual ticketing, fare evasion, unrecorded cash ridership, and total lack of real-time passenger occupancy data across route corridors.

**CitiLink** solves this with a streamlined three-pillar ecosystem:
1. **Route-Corridor Flexible Ticketing**: Citizens book once for a route (e.g., Route 12) and receive a 2- or 3-digit **Travel PIN** valid on **any bus** running on that corridor.
2. **Sub-Second Boarding**: Conductors verify passengers in < 2 seconds via a dedicated numerical keypad (or QR/lookup fallback) or issue 2-tap cash tickets.
3. **Sensorless Segment Occupancy**: By synthesizing digital boardings, cash entries, and GPS bus location, CitiLink generates real-time segment-by-segment crowd intelligence (🟢🟡🔴) without requiring hardware sensors.

---

# 2. User Personas & Problem Statements

### Persona 1: Rahul (Daily Daily Commuter / Student)
* **Goal**: Wants to reach college quickly from CBS to Panchavati.
* **Pain Points**: Doesn't know if Bus 101 or Bus 102 is coming first. Hates waiting in ticket lines or carrying exact change. Wants to board whichever bus arrives first.
* **Solution**: CitiLink shows Route 12 live buses. Rahul books a ₹20 ticket, gets PIN `142`. Bus 102 arrives first, he hops on, says "142", and takes his seat.

### Persona 2: Anandibai (Senior Citizen Commuter)
* **Goal**: Easy travel without complicated smartphone gymnastics.
* **Pain Points**: Hard to read small text, difficult to remember long 6-digit OTPs or scan blurry QR codes on a bumping bus.
* **Solution**: CitiLink automatically allocates an ultra-short 2-digit Senior PIN (`42`). She easily remembers and speaks "42" to the conductor.

### Persona 3: Eknath (Bus Conductor)
* **Goal**: Validate tickets and collect cash fares quickly amidst a crowded bus before the next stop arrives.
* **Pain Points**: Paper ticket machines are slow. QR scanners fail in low light or moving buses. Network drops in underpasses or rural stretches.
* **Solution**: Big-button keypad PIN entry, 2-tap Quick Cash ticketing, full offline functionality with automatic background syncing when connectivity returns.

### Persona 4: Transit Depot Manager / Municipal Authority
* **Goal**: Live visibility over fleet deployment, route revenue leakage, and passenger congestion hotspots.
* **Solution**: Web dashboard with live corridor radar, revenue reconciliation (Digital vs Cash), and segment occupancy analytics.

---

# 3. Detailed Product Requirements

## 3.1 Route-Corridor Multi-Bus Travel PIN Subsystem

### Requirements & Business Logic
1. **Corridor-Level Allocation**:
   - Tickets are booked for a `route_id` + `from_stop_id` + `to_stop_id` tuple.
   - The assigned Travel PIN is active across **all buses operating on that route** during the validity window (default: 90 minutes).
2. **PIN Categories**:
   - **Senior Citizen PIN**: 2 digits (`10` to `99`), 90 available slots per corridor.
   - **Standard PIN**: 3 digits (`100` to `999`), 900 available slots per corridor.
   - String formatting: Leading zeros are strictly preserved if configured (e.g. `07` remains `07`).
3. **Collision Avoidance**:
   - The PIN allocator selects an unused PIN from the route corridor's active pool using a round-robin or least-recently-used allocation strategy.
   - If the pool reaches $\ge 90\%$ utilization, the backend flags a warning and expands to 4-digit fallback if necessary.
4. **Atomic Bus Binding on Board**:
   - When Conductor on Bus $B_1$ submits PIN `P`, the backend/manifest marks the ticket as `BOARDED` with `trip_run_id = B_1.trip_id`.
   - Any subsequent attempt to enter PIN `P` on Bus $B_2$ returns `ALREADY_BOARDED_ON_BUS_B1`.

## 3.2 Citizen Mobile Application (Android / Compose)

### Core User Flows & UI Specifications
1. **Home / Discovery Screen**:
   - Nearby stops auto-detected via GPS.
   - Search bar: Origin & Destination selection with auto-suggestions.
   - Route list displaying all routes connecting selected stops.
2. **Live Bus Radar & "Where Is My Bus?"**:
   - Map view (MapLibre / OpenStreetMap) showing active buses moving along the route.
   - Arrival ETA countdown: "Bus 101: 3 mins away", "Bus 102: 8 mins away".
   - Current stop and passed stops progress bar.
3. **Live Segment Availability Indicator**:
   - 🟢 **Good Availability** (< 60% occupancy): "Plenty of seats available"
   - 🟡 **Moderate / Standing** (60–90% occupancy): "Standing room only"
   - 🔴 **Crowded / Full** (> 90% occupancy): "Bus is full"
4. **Booking & Instant Payment**:
   - Fare calculation engine based on stop distance/stage matrix.
   - Group booking selector (1 to 6 passengers).
   - Passenger type selector (Standard / Senior Citizen).
   - Payment integration via Razorpay (UPI Intent / GPay / PhonePe / Paytm / Cards).
5. **Active Ticket Screen**:
   - Prominent, high-visibility PIN card (e.g. `142` in 48sp bold font).
   - Route number, From/To stop names, Passenger count, Fare paid badge (`₹40 PAID`).
   - Backup dynamic QR code button.
   - Live journey status: "Bus Boarded: MH-15-AB-1234", "Next Stop: Ashok Stambh", "Your Destination: 2 stops away".

## 3.3 Conductor Mobile Application (Android / Compose)

### Operational Principles: Speed, High Contrast, Offline-First
1. **Shift Login & Trip Initiation**:
   - Conductor logs in with Conductor ID / PIN.
   - Selects assigned Bus Number (e.g. `MH-15-AB-1234`) and Route (e.g. `Route 12: CBS ➔ Panchavati`).
   - Taps `[START TRIP]` ➔ downloads latest Route Corridor Manifest to local Room SQLite database.
2. **Primary Keypad Verification Screen**:
   - Full-screen high-contrast 3x4 numeric keypad (`1` through `9`, `Clear`, `0`, `Enter`).
   - Conductor inputs PIN (e.g., `142`).
   - Instant response (< 100ms from local DB):
     - Displays: Passenger Name, Group Size (`3 Passengers`), `CBS ➔ Panchavati`, `₹60 PAID`.
     - Prominent green `[CONFIRM BOARD]` button.
     - Audio chime on successful validation.
3. **Quick Cash Boarding Screen**:
   - 2-tap workflow:
     - Tap 1: Select Destination Stop (buttons pre-ordered by upcoming route stops).
     - Tap 2: Select Passenger Count (`1`, `2`, `3`, `4+`) ➔ Total fare dynamically calculated.
     - Tap `[CASH BOARD]`.
   - Increments local occupancy counter immediately and records cash transaction.
4. **Live Onboard Occupancy Header**:
   - Always visible at top: `Onboard: 38 / 52 Seats` | `Next Stop: CBS`.
5. **Fallback Tools**:
   - Built-in QR scanner tab.
   - Search by Phone / Name tab.

## 3.4 Backend & Data Processing Engine (FastAPI + PostgreSQL)

1. **GPS Ingestion & Stop Progression Engine**:
   - Ingests GPS telemetry payloads from bus OBD/GPS devices or Driver app (`bus_id`, `lat`, `lon`, `speed`, `heading`, `timestamp`).
   - Matches coordinates against route polyline and stop geofences ($R = 30\text{m}$).
   - Updates current stop index, next stop ETA, and flags passed stops.
2. **Real-time Occupancy Calculator**:
   - Computes segment occupancy matrix per active trip run.
   - Incorporates boarding increments and destination alighting decrements.
3. **No-Show Automation Daemon**:
   - Background worker inspecting paid, unboarded tickets.
   - If all buses active on Route $R$ during the ticket validity window pass the passenger's origin stop + $N$ buffer stops (default: 2), ticket status transitions to `NO_SHOW`.
4. **Offline Sync Reconciliation Endpoint**:
   - Receives batched local event logs from Conductor app (`POST /api/v1/conductor/sync`).
   - Idempotent upserts keyed on UUID `event_id`.
   - Resolves conflicts with timestamp-based ordering.

## 3.5 Admin & Fleet Management Web Portal

1. **Fleet & Conductor Management**:
   - Register buses, capacity limits (seated/standing), GPS device IMEIs.
   - Create conductor profiles, credentials, and depot assignments.
2. **Route & Fare Matrix Configuration**:
   - Visual route polyline builder on OpenStreetMap.
   - Stop ordering, geofence radius definition, and stage-wise fare tables.
3. **Live Corridor Radar & Analytics**:
   - Map displaying live positions of all active buses.
   - Corridor congestion heatmaps and route passenger volume charts.
   - Revenue breakdown: Digital (UPI) vs Cash collections per bus / conductor / route.

---

# 4. Non-Functional Requirements (NFRs)

| Attribute | Target Metric |
| :--- | :--- |
| **Verification Latency** | $< 200\text{ms}$ on online API, $< 50\text{ms}$ on local Conductor SQLite manifest. |
| **Offline Resilience** | Conductor app must function seamlessly offline for 100% of validation and cash boarding operations for up to 8 hours. |
| **GPS Broadcast Latency** | $< 2\text{s}$ end-to-end latency from bus GPS transmission to Citizen app map UI. |
| **Concurrent Capacity** | Support 5,000 concurrent active tickets and 200 simultaneous bus trip streams in V1. |
| **Data Integrity** | Zero double-counting of passengers, zero lost offline cash transactions. |
| **Security & Privacy** | End-to-end encrypted HTTPS / WSS, hashed conductor PINs, token-based JWT auth. |
