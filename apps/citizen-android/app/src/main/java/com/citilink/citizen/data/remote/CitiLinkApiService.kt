package com.citilink.citizen.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

data class ApiHealthResponse(
    @SerializedName("status") val status: String,
    @SerializedName("project") val project: String? = null,
    @SerializedName("version") val version: String? = null
)

data class ApiBookingRequest(
    @SerializedName("route_id") val routeId: String,
    @SerializedName("from_stop") val fromStop: String,
    @SerializedName("to_stop") val toStop: String,
    @SerializedName("passenger_count") val passengerCount: Int,
    @SerializedName("is_senior_citizen") val isSeniorCitizen: Boolean
)

data class ApiBookingResponse(
    @SerializedName("ticket_id") val ticketId: String,
    @SerializedName("pin") val pin: String,
    @SerializedName("route_number") val routeNumber: String,
    @SerializedName("from_stop") val fromStop: String,
    @SerializedName("to_stop") val toStop: String,
    @SerializedName("passenger_count") val passengerCount: Int,
    @SerializedName("fare_paid") val farePaid: Int,
    @SerializedName("status") val status: String,
    @SerializedName("created_at") val createdAt: String? = null
)

data class ApiBusTelemetry(
    @SerializedName("bus_id") val busId: String,
    @SerializedName("fleet_number") val fleetNumber: String,
    @SerializedName("bus_number") val busNumber: String,
    @SerializedName("eta_minutes") val etaMinutes: Int,
    @SerializedName("occupancy_percent") val occupancyPercent: Int,
    @SerializedName("available_seats") val availableSeats: Int,
    @SerializedName("next_stop") val nextStop: String,
    @SerializedName("is_electric") val isElectric: Boolean
)

data class ApiRouteCorridorResponse(
    @SerializedName("route_id") val routeId: String,
    @SerializedName("route_name") val routeName: String,
    @SerializedName("buses") val buses: List<ApiBusTelemetry>
)

interface CitiLinkApiService {

    @GET("api/v1/health")
    suspend fun checkHealth(): ApiHealthResponse

    @POST("api/v1/tickets/book")
    suspend fun bookTicket(@Body request: ApiBookingRequest): ApiBookingResponse

    @GET("api/v1/routes/{route_id}/live")
    suspend fun getLiveRouteCorridor(@Path("route_id") routeId: String): ApiRouteCorridorResponse
}
