package com.citilink.citizen.data

import com.citilink.citizen.ui.components.OccupancyLevel

data class BusArrival(
    val id: String,
    val busNumber: String,          // e.g. "MH-15-GV-0101"
    val fleetNumber: String,        // e.g. "Bus #101"
    val routeNumber: String,        // e.g. "Route 12"
    val routeName: String,          // e.g. "CBS ➔ Panchavati"
    val destination: String,        // e.g. "Panchavati / Nimani"
    val etaMinutes: Int,            // e.g. 3
    val totalSeats: Int = 52,
    val availableSeats: Int,        // e.g. 24
    val occupancyPercent: Int,      // e.g. 54
    val isElectricBus: Boolean = false,
    val nextStopName: String
)

data class NearbyStopInfo(
    val id: String,
    val name: String,
    val standLabel: String,         // e.g. "Stand 3"
    val walkingDistanceMeters: Int, // e.g. 120
    val activeBusesCount: Int
)

data class TransitHub(
    val id: String,
    val name: String,
    val terminalName: String,
    val standLabel: String,
    val areaDescription: String,
    val nearbyStops: List<NearbyStopInfo>,
    val liveBuses: List<BusArrival>,
    val quickSearchTags: List<String>,
    val totalNearbyBusesCount: Int
)

object NashikTransitRepository {

    val HUB_CBS = TransitHub(
        id = "hub_cbs",
        name = "New CBS (Takkar Bazaar)",
        terminalName = "CBS West City Terminal",
        standLabel = "Stand 3 (City Bus)",
        areaDescription = "Downtown Central Hub • Near Canada Corner",
        totalNearbyBusesCount = 8,
        quickSearchTags = listOf("Panchavati Karanja", "Nimani Bus Stand", "Nashik Road Rly", "Tapovan"),
        nearbyStops = listOf(
            NearbyStopInfo("stop_cbs_3", "CBS West Terminal", "Stand 3", 110, 4),
            NearbyStopInfo("stop_cbs_inter", "Takkar Bazaar Intercity Gate", "Stand 1", 240, 2),
            NearbyStopInfo("stop_ashok", "Ashok Stambh North Stand", "Platform A", 650, 2)
        ),
        liveBuses = listOf(
            BusArrival(
                id = "bus_101",
                busNumber = "MH-15-GV-0101",
                fleetNumber = "Bus #101",
                routeNumber = "Route 12",
                routeName = "CBS ➔ Panchavati Express",
                destination = "Panchavati / Nimani",
                etaMinutes = 3,
                availableSeats = 28,
                occupancyPercent = 46,
                isElectricBus = true,
                nextStopName = "Ashok Stambh"
            ),
            BusArrival(
                id = "bus_102",
                busNumber = "MH-15-GV-0102",
                fleetNumber = "Bus #102",
                routeNumber = "Route 12",
                routeName = "CBS ➔ Panchavati",
                destination = "Panchavati / Nimani",
                etaMinutes = 7,
                availableSeats = 14,
                occupancyPercent = 73,
                isElectricBus = false,
                nextStopName = "Canada Corner"
            ),
            BusArrival(
                id = "bus_156",
                busNumber = "MH-15-AK-2244",
                fleetNumber = "Bus #156",
                routeNumber = "Route 156",
                routeName = "CBS ➔ Nashik Road Rly",
                destination = "Nashik Road Rly Stn",
                etaMinutes = 11,
                availableSeats = 4,
                occupancyPercent = 92,
                isElectricBus = false,
                nextStopName = "Dwarka Circle"
            )
        )
    )

    val HUB_NASHIK_ROAD = TransitHub(
        id = "hub_nashik_road",
        name = "Nashik Road Rly Station",
        terminalName = "Railway City Terminal Stand 1",
        standLabel = "Stand 1 (Platform Road)",
        areaDescription = "Main Railway Terminal • Sinnar Phata",
        totalNearbyBusesCount = 6,
        quickSearchTags = listOf("New CBS Takkar Bazaar", "Nimani Bus Stand", "Satpur MIDC", "Ambad MIDC"),
        nearbyStops = listOf(
            NearbyStopInfo("stop_nr_1", "Nashik Road Main Stand", "Stand 1", 90, 3),
            NearbyStopInfo("stop_nr_sub", "Sinnar Phata Junction", "Stand 4", 320, 2),
            NearbyStopInfo("stop_bitle", "Bitco Hospital Stand", "Platform B", 500, 1)
        ),
        liveBuses = listOf(
            BusArrival(
                id = "bus_201",
                busNumber = "MH-15-GV-0201",
                fleetNumber = "Bus #201",
                routeNumber = "Route 101",
                routeName = "Nashik Road ➔ CBS City Fast",
                destination = "New CBS Terminal",
                etaMinutes = 2,
                availableSeats = 32,
                occupancyPercent = 38,
                isElectricBus = true,
                nextStopName = "Bitco Hospital"
            ),
            BusArrival(
                id = "bus_202",
                busNumber = "MH-15-GV-0202",
                fleetNumber = "Bus #202",
                routeNumber = "Route 156",
                routeName = "Nashik Road ➔ Satpur Industrial",
                destination = "Satpur MIDC Stand",
                etaMinutes = 8,
                availableSeats = 18,
                occupancyPercent = 65,
                isElectricBus = false,
                nextStopName = "Dwarka"
            )
        )
    )

    val HUB_NIMANI = TransitHub(
        id = "hub_nimani",
        name = "Nimani Bus Station (Panchavati)",
        terminalName = "Nimani Central Terminal",
        standLabel = "Stand 2 (Godavari Side)",
        areaDescription = "Panchavati Temple Area • Old Nashik Hub",
        totalNearbyBusesCount = 5,
        quickSearchTags = listOf("New CBS", "Tapovan Terminal", "Ramkund", "Dindori Road"),
        nearbyStops = listOf(
            NearbyStopInfo("stop_nim_2", "Nimani Stand 2", "Stand 2", 80, 3),
            NearbyStopInfo("stop_pk", "Panchavati Karanja", "Stand 1", 340, 2)
        ),
        liveBuses = listOf(
            BusArrival(
                id = "bus_103",
                busNumber = "MH-15-GV-0103",
                fleetNumber = "Bus #103",
                routeNumber = "Route 12",
                routeName = "Nimani ➔ CBS Downtown",
                destination = "New CBS Stand 3",
                etaMinutes = 4,
                availableSeats = 26,
                occupancyPercent = 50,
                isElectricBus = true,
                nextStopName = "Ravivar Karanja"
            ),
            BusArrival(
                id = "bus_166",
                busNumber = "MH-15-AK-3112",
                fleetNumber = "Bus #166",
                routeNumber = "Route 166",
                routeName = "Nimani ➔ Tapovan Pilgrimage",
                destination = "Tapovan Depot",
                etaMinutes = 9,
                availableSeats = 8,
                occupancyPercent = 85,
                isElectricBus = false,
                nextStopName = "Malegaon Stand"
            )
        )
    )

    val HUB_COLLEGE_ROAD = TransitHub(
        id = "hub_college_road",
        name = "College Road (BYK Circle)",
        terminalName = "BYK Campus Bus Stand",
        standLabel = "Stand 1 (North Bound)",
        areaDescription = "University & Student Corridor • Gangapur Road",
        totalNearbyBusesCount = 4,
        quickSearchTags = listOf("New CBS", "City Center Mall", "Gangapur Dam", "Jehan Circle"),
        nearbyStops = listOf(
            NearbyStopInfo("stop_byk", "BYK College Stand", "Stand 1", 60, 2),
            NearbyStopInfo("stop_kthm", "KTHM College Gate", "Stand 3", 450, 2)
        ),
        liveBuses = listOf(
            BusArrival(
                id = "bus_205",
                busNumber = "MH-15-GV-0205",
                fleetNumber = "Bus #205",
                routeNumber = "Route 205",
                routeName = "College Road ➔ CBS City",
                destination = "New CBS Terminal",
                etaMinutes = 5,
                availableSeats = 30,
                occupancyPercent = 42,
                isElectricBus = true,
                nextStopName = "Vidya Vikas Circle"
            )
        )
    )

    val ALL_HUBS = listOf(HUB_CBS, HUB_NASHIK_ROAD, HUB_NIMANI, HUB_COLLEGE_ROAD)
}
