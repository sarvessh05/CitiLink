from fastapi import APIRouter, status
from app.core.config import settings
from app.core.database import check_db_health
from app.schemas.health import HealthCheckResponse
from datetime import datetime

router = APIRouter()


@router.get(
    "/health",
    response_model=HealthCheckResponse,
    status_code=status.HTTP_200_OK,
    summary="Service Health Check",
    description="Returns API status, version, timestamp, and database connectivity."
)
async def get_health():
    db_status = await check_db_health()
    overall_status = "healthy" if db_status.get("status") == "healthy" else "degraded"
    
    return HealthCheckResponse(
        status=overall_status,
        project=settings.PROJECT_NAME,
        version=settings.VERSION,
        timestamp=datetime.utcnow(),
        database=db_status,
        environment=settings.ENVIRONMENT
    )
