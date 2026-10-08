package com.citilink.citizen.data

import com.citilink.citizen.data.remote.ApiBookingRequest
import com.citilink.citizen.data.remote.ApiClient
import com.citilink.citizen.ui.screens.BookedTicketResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CitiLinkRepository {

    var isLiveApiMode: Boolean = false
        private set

    fun setLiveApiMode(enabled: Boolean) {
        isLiveApiMode = enabled
    }

    suspend fun checkBackendConnection(): Boolean = withContext(Dispatchers.IO) {
        try {
            val response = ApiClient.service.checkHealth()
            response.status == "healthy" || response.status == "ok"
        } catch (e: Exception) {
            false
        }
    }

    suspend fun bookCorridorTicket(
        routeId: String = "route_12",
        fromStop: String,
        toStop: String,
        passengerCount: Int,
        isSeniorCitizen: Boolean,
        paymentMode: String
    ): BookedTicketResult = withContext(Dispatchers.IO) {
        val baseFare = if (toStop.contains("Panchavati")) 20 else 15
        val totalFare = baseFare * passengerCount

        if (isLiveApiMode) {
            try {
                val apiResponse = ApiClient.service.bookTicket(
                    ApiBookingRequest(
                        routeId = routeId,
                        fromStop = fromStop,
                        toStop = toStop,
                        passengerCount = passengerCount,
                        isSeniorCitizen = isSeniorCitizen
                    )
                )

                return@withContext BookedTicketResult(
                    pin = apiResponse.pin,
                    routeNumber = apiResponse.routeNumber,
                    fromStop = apiResponse.fromStop,
                    toStop = apiResponse.toStop,
                    passengerCount = apiResponse.passengerCount,
                    isSeniorCitizen = isSeniorCitizen,
                    farePaid = apiResponse.farePaid,
                    paymentMode = paymentMode
                )
            } catch (e: Exception) {
                // Fallback seamlessly to offline generated PIN
            }
        }

        // Offline / Demo Mode Generation
        val fallbackPin = if (isSeniorCitizen) "42" else "142"
        return@withContext BookedTicketResult(
            pin = fallbackPin,
            routeNumber = "Route 12",
            fromStop = fromStop,
            toStop = toStop,
            passengerCount = passengerCount,
            isSeniorCitizen = isSeniorCitizen,
            farePaid = totalFare,
            paymentMode = paymentMode
        )
    }
}
