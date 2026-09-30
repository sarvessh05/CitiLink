import pytest
import httpx
from app.core.config import settings


@pytest.mark.asyncio
async def test_root_endpoint(client: httpx.AsyncClient):
    """Test the root welcome endpoint."""
    response = await client.get("/")
    assert response.status_code == 200
    data = response.json()
    assert "message" in data
    assert settings.PROJECT_NAME in data["message"]
    assert "docs" in data
    assert "health" in data


@pytest.mark.asyncio
async def test_health_check_endpoint(client: httpx.AsyncClient):
    """Test GET /api/v1/health endpoint."""
    response = await client.get(f"{settings.API_V1_STR}/health")
    assert response.status_code == 200
    data = response.json()
    assert "status" in data
    assert data["status"] in ["healthy", "degraded"]
    assert data["project"] == settings.PROJECT_NAME
    assert data["version"] == settings.VERSION
    assert "timestamp" in data
    assert "database" in data
    assert "environment" in data
