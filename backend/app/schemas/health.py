from pydantic import BaseModel, Field
from typing import Dict, Any, Optional
from datetime import datetime


class HealthCheckResponse(BaseModel):
    status: str = Field(..., examples=["healthy"])
    project: str = Field(..., examples=["CitiLink Transit & Ticketing Platform"])
    version: str = Field(..., examples=["1.0.0"])
    timestamp: datetime = Field(default_factory=datetime.utcnow)
    database: Dict[str, Any]
    environment: str = Field(..., examples=["development"])
