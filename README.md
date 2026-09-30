# CitiLink — Transit & Digital Ticketing Platform

CitiLink is a high-throughput transit and digital ticketing platform designed for public bus networks (such as Nashik City Transport). It introduces a **Multi-Bus Route Corridor Travel PIN system** that decouples passenger ticket purchases from specific physical bus units, enabling frictionless boarding on *any* upcoming bus serving the route corridor.

---

## 🏛️ System Architecture Overview

* **Backend**: FastAPI (Python 3.11+), SQLAlchemy 2.0 (Asyncio), PostGIS, Alembic, Pydantic v2.
* **Citizen Mobile App**: Android Kotlin / Jetpack Compose.
* **Conductor Mobile App**: Android Kotlin / Jetpack Compose + Embedded Offline Room SQLite.
* **Admin Dashboard**: React + Vite + Tailwind CSS.

---

## 🚀 Getting Started (Backend)

### Prerequisites
* Python 3.11+
* PostgreSQL 16+ with PostGIS extension enabled

### 1. Environment Setup
Clone the repository and set up a virtual environment:

```bash
cd backend
python -m venv .venv

# On Windows (PowerShell):
.\.venv\Scripts\Activate.ps1

# On Linux/macOS:
source .venv/bin/activate
```

Install backend dependencies:
```bash
pip install -r requirements.txt
```

### 2. Configure Environment Variables
Copy `.env.example` to `.env` and set your PostgreSQL credentials:
```bash
cp .env.example .env
```

### 3. Run Migrations
```bash
alembic upgrade head
```

### 4. Run Development Server
```bash
uvicorn app.main:app --reload --host 0.0.0.0 --port 8000
```

* **Interactive API Documentation (Swagger)**: [http://localhost:8000/api/v1/docs](http://localhost:8000/api/v1/docs)
* **Alternative API Documentation (ReDoc)**: [http://localhost:8000/api/v1/redoc](http://localhost:8000/api/v1/redoc)
* **Health Check**: [http://localhost:8000/api/v1/health](http://localhost:8000/api/v1/health)

### 5. Running Tests
```bash
pytest -v
```

---

## 📂 Project Structure

```text
CitiLink/
├── backend/
│   ├── alembic/                # Database migrations
│   ├── alembic.ini             # Alembic configuration
│   ├── app/
│   │   ├── api/                # API router & v1 endpoints
│   │   │   └── v1/
│   │   │       ├── endpoints/  # Health, Auth, Routes, Tickets, Telemetry
│   │   │       └── api.py
│   │   ├── core/               # App configuration, DB engine, logging
│   │   ├── models/             # SQLAlchemy ORM models
│   │   ├── schemas/            # Pydantic validation schemas
│   │   ├── services/           # Business logic & PIN allocation algorithms
│   │   └── main.py             # FastAPI entrypoint & middleware
│   ├── tests/                  # Pytest test suite
│   ├── pyproject.toml
│   ├── requirements.txt
│   └── .env.example
├── ARCHITECTURE.md             # Technical architecture & DDL design
├── CITILINK_FINAL.md           # Product & system specification
├── PRD.md                      # Product Requirements Document
├── TASK.md                     # Engineering roadmap & task checklist
└── README.md
```
