package com.citilink.citizen.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ConfirmationNumber
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citilink.citizen.data.BusArrival
import com.citilink.citizen.data.NashikTransitRepository
import com.citilink.citizen.ui.components.BentoCard
import com.citilink.citizen.ui.components.CorridorRadarVisualizer
import com.citilink.citizen.ui.components.OccupancyBadge
import com.citilink.citizen.ui.components.RadarBusPin
import com.citilink.citizen.ui.components.StopProgressTimeline
import com.citilink.citizen.ui.components.StopTimelineItem
import com.citilink.citizen.ui.theme.OccupancyGood
import com.citilink.citizen.ui.theme.OutlineLight
import com.citilink.citizen.ui.theme.OutlineVariantLight
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SecondaryContainerBlue
import com.citilink.citizen.ui.theme.SecondaryTransitBlue
import com.citilink.citizen.ui.theme.SeniorGold
import com.citilink.citizen.ui.theme.SurfaceContainerHighLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowestLight
import com.citilink.citizen.ui.theme.SurfaceLight

@Composable
fun CorridorRadarScreen(
    onBackClick: () -> Unit,
    onBookRouteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isReversedDirection by remember { mutableStateOf(false) }
    var selectedBusId by remember { mutableStateOf("bus_101") }

    val radarPins = listOf(
        RadarBusPin("bus_101", "Bus #101", 0.35f, "3m away", 46),
        RadarBusPin("bus_102", "Bus #102", 0.12f, "7m away", 73),
        RadarBusPin("bus_103", "Bus #103", 0.78f, "14m away", 50)
    )

    val stopsTimeline = listOf(
        StopTimelineItem(1, "CBS West Stand 3", "Departed", isPassed = true, isUserBoardingStop = true),
        StopTimelineItem(2, "Ashok Stambh (North)", "Next (1 min)", isPassed = false, isCurrentBusLocation = true),
        StopTimelineItem(3, "Ravivar Karanja (RK)", "4 mins", isPassed = false),
        StopTimelineItem(4, "Panchavati Karanja", "8 mins", isPassed = false, isUserDestinationStop = true),
        StopTimelineItem(5, "Nimani Bus Station Terminal", "12 mins", isPassed = false)
    )

    Box(modifier = modifier.fillMaxSize().background(SurfaceLight)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Navigation Top Header & Route Summary Bar
            item {
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLowestLight)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "Back",
                                tint = PrimaryLight,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = "Live Corridor Radar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryLight
                            )
                            Text(
                                text = "Nashik Route 12 High-Frequency",
                                fontSize = 11.sp,
                                color = OutlineLight
                            )
                        }
                    }

                    // Direction Switcher Button
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(SurfaceContainerLowLight)
                            .clickable { isReversedDirection = !isReversedDirection }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.SwapVert,
                            contentDescription = "Switch Direction",
                            tint = SecondaryTransitBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = if (!isReversedDirection) "CBS ➔ Nimani" else "Nimani ➔ CBS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryTransitBlue
                        )
                    }
                }
            }

            // 2. Corridor Quick Specs Card
            item {
                BentoCard(
                    backgroundColor = SurfaceContainerLowLight,
                    border = null
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PrimaryLight)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "12",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }

                            Column {
                                Text(
                                    text = if (!isReversedDirection) "CBS ➔ Panchavati Karanja" else "Nimani ➔ CBS Terminal",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryLight
                                )
                                Text(
                                    text = "⚡ HIGH-FREQUENCY FEEDER • ₹20",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SecondaryTransitBlue
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "18 min",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryTransitBlue
                            )
                            Text(
                                text = "3.8 km span",
                                fontSize = 11.sp,
                                color = OutlineLight
                            )
                        }
                    }
                }
            }

            // 3. "Universal Corridor Pass" Philosophy Callout Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFE5EEFF))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(SecondaryTransitBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Lightbulb,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "Universal Corridor Pass",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryLight
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(SecondaryTransitBlue)
                                        .padding(horizontal = 5.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "PIN POWERED",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = "Board ANY Route 12 bus with your single Travel PIN. Tickets are corridor-based, flexible, and never locked to a single vehicle.",
                                fontSize = 12.sp,
                                color = PrimaryLight.copy(alpha = 0.85f),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }

            // 4. Interactive Corridor Radar Visualizer Canvas
            item {
                CorridorRadarVisualizer(
                    activeBuses = radarPins,
                    selectedBusId = selectedBusId,
                    onBusClick = { selectedBusId = it }
                )
            }

            // 5. Active Incoming Buses on Corridor
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "3 Buses on Corridor",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )
                        Text(
                            text = "Every 4 mins",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SecondaryTransitBlue
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    NashikTransitRepository.HUB_CBS.liveBuses.filter { it.routeNumber == "Route 12" }.forEach { bus ->
                        BentoCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            onClick = { selectedBusId = bus.id },
                            backgroundColor = if (selectedBusId == bus.id) SurfaceContainerLowLight else SurfaceContainerLowestLight
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(if (selectedBusId == bus.id) SecondaryTransitBlue else SurfaceContainerHighLight),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.DirectionsBus,
                                            contentDescription = null,
                                            tint = if (selectedBusId == bus.id) Color.White else PrimaryLight,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }

                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = "${bus.fleetNumber} (${bus.busNumber})",
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = PrimaryLight
                                            )
                                            if (bus.isElectricBus) {
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(
                                                    text = "⚡ EV",
                                                    fontSize = 10.sp,
                                                    fontWeight = FontWeight.Black,
                                                    color = Color(0xFF0284C7)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "Next: ${bus.nextStopName}",
                                            fontSize = 11.sp,
                                            color = OutlineLight
                                        )
                                    }
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "${bus.etaMinutes} min away",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SecondaryTransitBlue
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    OccupancyBadge(
                                        occupancyPercent = bus.occupancyPercent,
                                        customText = "${bus.availableSeats} seats"
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 6. Stop Progress Timeline
            item {
                BentoCard(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = SurfaceContainerLowestLight
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Route 12 Sequential Stops",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        StopProgressTimeline(stops = stopsTimeline)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // 7. Sticky Bottom Floating CTA
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(SurfaceLight.copy(alpha = 0.95f))
                .padding(16.dp)
        ) {
            Button(
                onClick = onBookRouteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SecondaryTransitBlue)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ConfirmationNumber,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Book Route 12 Travel PIN — ₹20",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}
