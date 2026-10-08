---
name: Urban Pulse Mobility
colors:
  surface: '#f8f9ff'
  surface-dim: '#cbdbf5'
  surface-bright: '#f8f9ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#eff4ff'
  surface-container: '#e5eeff'
  surface-container-high: '#dce9ff'
  surface-container-highest: '#d3e4fe'
  on-surface: '#0b1c30'
  on-surface-variant: '#45464d'
  inverse-surface: '#213145'
  inverse-on-surface: '#eaf1ff'
  outline: '#76777d'
  outline-variant: '#c6c6cd'
  surface-tint: '#565e74'
  primary: '#000000'
  on-primary: '#ffffff'
  primary-container: '#131b2e'
  on-primary-container: '#7c839b'
  inverse-primary: '#bec6e0'
  secondary: '#0051d5'
  on-secondary: '#ffffff'
  secondary-container: '#316bf3'
  on-secondary-container: '#fefcff'
  tertiary: '#000000'
  on-tertiary: '#ffffff'
  tertiary-container: '#001f26'
  on-tertiary-container: '#0090a9'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#dae2fd'
  primary-fixed-dim: '#bec6e0'
  on-primary-fixed: '#131b2e'
  on-primary-fixed-variant: '#3f465c'
  secondary-fixed: '#dbe1ff'
  secondary-fixed-dim: '#b4c5ff'
  on-secondary-fixed: '#00174b'
  on-secondary-fixed-variant: '#003ea8'
  tertiary-fixed: '#acedff'
  tertiary-fixed-dim: '#4cd7f6'
  on-tertiary-fixed: '#001f26'
  on-tertiary-fixed-variant: '#004e5c'
  background: '#f8f9ff'
  on-background: '#0b1c30'
  surface-variant: '#d3e4fe'
typography:
  display:
    fontFamily: Plus Jakarta Sans
    fontSize: 40px
    fontWeight: '800'
    lineHeight: 48px
    letterSpacing: -0.02em
  display-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '800'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.015em
  headline-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 26px
    fontWeight: '700'
    lineHeight: 34px
    letterSpacing: -0.015em
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 22px
    fontWeight: '700'
    lineHeight: 28px
    letterSpacing: -0.01em
  headline-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 24px
  title-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '600'
    lineHeight: 22px
  title-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '600'
    lineHeight: 20px
  body-lg:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  body-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '400'
    lineHeight: 16px
  label-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 14px
    fontWeight: '700'
    lineHeight: 18px
    letterSpacing: 0.01em
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 12px
    fontWeight: '700'
    lineHeight: 16px
    letterSpacing: 0.02em
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 10px
    fontWeight: '800'
    lineHeight: 14px
    letterSpacing: 0.04em
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-mobile: 0.75rem
  gutter-desktop: 1.5rem
  margin: 1rem
  margin-mobile: 1rem
  margin-desktop: 2rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 0.75rem
  space-lg: 1.25rem
  space-xl: 2rem
---

## Brand & Style

This design system targets daily public transit commuters navigating tier-2 and metropolitan Indian transit corridors—specifically modeled around the dynamic Nashik bus, feeder, and multimodal transit landscape. Commuters rely on fast, split-second decisions while moving through high-glare outdoor sunlight, packed terminal queues, and fluctuating connectivity.

The aesthetic fuses Google Maps' geographic utility, Uber’s real-time transactional clarity, and contemporary Indian mobility engineering. The visual style embodies **Corporate / Modern High-Legibility Utilitarianism**—built on high contrast, physical depth through layered surface cards, instant visual triage, and oversized, thumb-friendly touch targets. The experience must instill unwavering reliability, precision, calm authority amidst commuter chaos, and frictionless navigation.

## Colors

The palette leverages high-contrast deep slates for authority, crisp electric blues for directional guidance, and calibrated situational accents:

- **Primary (`#0F172A`)**: Deep Navy/Slate Slate-900. Anchors navigation headers, high-importance typographic elements, key structural anchors, and bottom sheets.
- **Secondary (`#2563EB`)**: Transit Blue. Serves as active route polylines, primary action drivers, live GPS vehicle blips, and primary interactive states.
- **Tertiary (`#06B6D4`)**: Electric Cyan. Used selectively for live ETA updates, dynamic radar ripples, active stop pulse indicators, and feeder interlink badges.
- **Neutral Palette**: Base surfaces use `#F8FAFC` (Slate-50) and `#F1F5F9` (Slate-100) to cut harsh outdoor glare while maintaining clear contrast against pure `#FFFFFF` sheet cards. Text levels cascade down to `#334155` (Slate-700) and `#64748B` (Slate-500).

### Contextual Occupancy & Transit Signals
Occupancy status is treated with critical functional rigor across cards and radar tags:
- **Available (`#059669`)**: Emerald green indicates seat availability ($\le 60\%$). Paired with light tint `#ECFDF5`.
- **Moderate (`#D97706`)**: Amber indicates standing room only ($60\% - 90\%$). Paired with light tint `#FFFBEB`.
- **Crowded (`#DC2626`)**: Vivid coral red signals overcrowded conditions ($> 90\%$). Paired with light tint `#FEF2F2`.
- **Platform/Route Pin Callout**: Pure black `#020617` with high-contrast `#FFFFFF` alphanumeric typography for bus service numbers (e.g., *14-B*, *CityLink Rapid*).

## Typography

The dual-type strategy combines **Plus Jakarta Sans** for geo-spatial anchors, display digits, route numbers, and scannable interface titles with **Inter** for dense legibility across stop listings, transit alerts, and schedules.

- Numerical route badges and ETAs demand immediate optical clarity; they render in `Plus Jakarta Sans` with tabular figures enabled (`tnum`).
- Indian route nomenclature often pairs bilingual Hindi/Marathi transliteration beside English route designations; `body-md` accommodates these inline pairs without vertical baseline shift.
- Micro tags (fare tokens, bus floor types, crowd levels) rely on uppercase `label-sm` with letter spacing to prevent visual bleed on low-cost IPS mobile screens.

## Layout & Spacing

The layout is built around a **mobile-first fluid map-underlay structure** with floating cards, persistent drag bottom-sheets, and an adaptive 4-column (mobile) to 12-column (tablet/desktop) fluid grid.

- **Breakpoints**:
  - `Mobile` (< 640px): 4-column system, 16px page margins, dynamic persistent bottom sheet occupying `38vh` (collapsed) to `88vh` (expanded).
  - `Tablet` (640px – 1024px): 8-column layout, left docked inspection pane (380px fixed) with map fill.
  - `Desktop/Kiosk` (> 1024px): 12-column container, max content constraint of 1280px or dual split view (station timetable dashboard left, live radar telemetry right).
- **Rhythm & Touch**: Minimum interactive bounding boxes are set strictly to 48x48px to accommodate single-hand transit use while walking or commuting on bumpy roads.

## Elevation & Depth

Visual hierarchy combines **crisp physical surface separation** with **ambient cool-tinted elevation**:

- **Level 0 (Canvas/Map Base)**: Underlay vector basemap running slightly muted road geometry.
- **Level 1 (Docked/Inset Cards)**: `#FFFFFF` surfaces with a 1px border stroke (`#E2E8F0`). Flat elevation, relying on contrast separation against `#F1F5F9`.
- **Level 2 (Floating Action Panels & Bottom Sheets)**: `#FFFFFF` cards elevated with dual-stage ambient shadow:
  - `0 4px 6px -1px rgba(15, 23, 42, 0.06), 0 2px 4px -2px rgba(15, 23, 42, 0.04)`
  - Outlined with a microscopic `rgba(15, 23, 42, 0.08)` border for definition in direct sunlight.
- **Level 3 (Interactive Modals, Live Radar Pills, Floating Route Action Buttons)**:
  - `0 12px 24px -4px rgba(15, 23, 42, 0.12), 0 4px 8px -2px rgba(15, 23, 42, 0.06)`
- **Glassmorphism Rule**: Restricted strictly to secondary floating overlays on the active map view (e.g., Compass, Recenter FAB, Layer Switcher), using `rgba(255, 255, 255, 0.88)` with `backdrop-filter: blur(12px)`.

## Shapes

The design system operates with **Level 2 (Rounded)** curvature tokens (base 8px / 0.5rem), providing a modern, ergonomic hand-feel that avoids toy-like bubbly aesthetics while softening sharp technical lines:

- **Base Components (Inputs, Stop list tiles, Standard Chips)**: `0.5rem` (`rounded-md`).
- **Sheet Shells & Modal Containers**: `1rem` (`rounded-lg`) along leading sheet headers to emphasize modular pull handles.
- **Status Pills, Travel Route Badges, Bus PIN tags**: Full circular pill styling (`9999px`) to immediately signal categorical live transit metadata distinct from rectangular structural content.

## Components

### Buttons
- **Primary Action**: `#2563EB` solid fill, white text, 48px height, `title-md` font weight, rounded `0.5rem`. Active state presses down to `#1D4ED8` with a subtle inset transform.
- **Quick-Book / SOS**: High-visibility `#0F172A` deep navy button with high-contrast `#38BDF8` icon accents for rapid single-tap ticketing and emergency safety callouts.

### Chips & Live Radar Pills
- **Radar Pill**: Floating status badge featuring an animated cyan radiating dot (`#06B6D4`), dark navy backdrop (`#0F172A`), and crisp white monospace ETA text (e.g., `LIVE • 4 MIN`).
- **Occupancy Filter Chip**: Pill shaped with a 1px border. Inactive state: `#F8FAFC` background with `#64748B` label. Active state: `#0F172A` background with white label and dedicated colored occupancy dot.

### Stop Timeline Connector (Transit Core)
- **Timeline Path**: 3px solid vertical rule connecting bus stops.
  - Passed stops: Muted `#CBD5E1`.
  - Upcoming active route: `#2563EB`.
- **Stop Nodes**:
  - Regular Stop: 12px outer circle, white center, 2px border `#64748B`.
  - Transfer/Hub Stop: 16px outer circle, double border with `#0F172A` hub mark.
  - Current Bus Position: 20px `#2563EB` glowing disc with white bus glyph, casting a localized `#2563EB` 30% alpha pulse.

### Route PIN Callout (Map Marker)
- High-contrast geometric bubble marker. Pure black `#020617` background with bold white bus code (e.g., `212A`), with a bottom directional chevron anchored to station coordinates. Accompanied by occupancy color-strip at the card's top edge.

### Transit Inputs
- Search bars have a 52px height, dual icon slots (left: origin/destination bullet; right: mic or QR scanner), filled with `#F1F5F9`, transitioning to white `#FFFFFF` with a 2px `#2563EB` border on focus.

### Cards
- **Trip Card**: White surface, 1px border `#E2E8F0`, interior padding of `space-md` (16px), containing:
  1. Service badge & terminus station name.
  2. Live ETA with directional indicator.
  3. Occupancy progress segment (3 horizontal segmented bars indicating load).
  4. Ticket fare tag right-aligned in `headline-sm`.