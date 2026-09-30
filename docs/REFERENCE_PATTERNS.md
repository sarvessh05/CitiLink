# CitiLink — Reference Patterns & Extracted Insights

This document consolidates reusable patterns, UI flows, algorithms, and architectural insights extracted from reference open-source repositories evaluated during project initialization.

---

## 1. Preserved Assets (in `assets/`)

| Category | File | Target Usage in CitiLink |
| :--- | :--- | :--- |
| **Audio** | `assets/audio/success_sound.mp3` | **Conductor App** (Task 4.3): High-tempo positive audio chime on successful PIN entry / QR scan verification. |
| **Lottie Animation** | `assets/lottie/ticket_success.json` | **Citizen App** (Task 5.2): Animated badge on payment confirmation and PIN assignment. |
| **Lottie Animation** | `assets/lottie/main_annimation.json` | **Citizen App** (Task 5.1): Splash / Onboarding transit bus animation. |
| **Typography** | `assets/fonts/poppins_semibold.ttf` | **Android & Web Typography**: Clean numerical rendering for 2-digit / 3-digit PINs. |
| **Vector Drawables** | `assets/drawables/baseline_*.xml` | **Android Material 3 UI**: 24+ vector transit, route, compass, bus, and user icons. |

---

## 2. Key Architectural Patterns & Implementations

### A. Realtime QR & Digital Ticket Verification (Next.js / TypeScript)
* **Source**: `next-transport-qrcode-app`
* **Pattern**:
  - Encodes payload: `{ ticket_id, route_id, pin, expires_at, signature }`.
  - Signature calculated via HMAC-SHA256 with server-side secret key to prevent client tampering.
  - Live browser camera scanner (`react-qr-scanner` with `facingMode: { ideal: 'environment' }`) for Depot Admin Web Dashboard verification.
* **Application in CitiLink**:
  - Reused in **Admin Web Dashboard** (Phase 6) and **Conductor Fallback QR Scanner** (Phase 4).

### B. High-Frequency GPS Tracking & Geofencing (Android Native)
* **Source**: `TransitAI_Passenger (NavixP)`
* **Pattern**:
  - `FusedLocationProviderClient` with `PRIORITY_HIGH_ACCURACY` and 1–2s location request intervals.
  - Foreground Service with persistent notification to prevent Android OS battery optimization killers.
* **Application in CitiLink**:
  - Used in **Bus Telemetry Daemon** (Task 3.1) and **Driver / Conductor App GPS Streamer**.

### C. Offline Verification & Queue Sync
* **Source**: `TransitAI` & `GoTicket`
* **Pattern**:
  - Pre-fetch trip manifest upon trip initiation (`GET /api/v1/conductor/manifest`).
  - Cache active corridor PINs in local SQLite / Room DB.
  - Maintain offline event queue with UUIDv4 `client_event_id` for idempotent sync.
* **Application in CitiLink**:
  - Core component of **Conductor Android App** (Task 4.1 & Task 4.4).

---

## 3. Discarded Elements (Marked as Redundant)
* Generic Flutter mockup UIs with static data (`BusEase`, `UniGo`, `Ticky`).
* Static mock React Native UI (`GoTicket-Mobile`).
* Hardcoded Firebase Firestore models that bypass relational integrity.
