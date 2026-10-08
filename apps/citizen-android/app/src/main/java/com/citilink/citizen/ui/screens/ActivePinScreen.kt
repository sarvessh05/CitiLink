package com.citilink.citizen.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.Lightbulb
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.QrCode2
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material.icons.rounded.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citilink.citizen.ui.components.BentoCard
import com.citilink.citizen.ui.components.QrCodeModal
import com.citilink.citizen.ui.components.StopProgressTimeline
import com.citilink.citizen.ui.components.StopTimelineItem
import com.citilink.citizen.ui.theme.OccupancyGood
import com.citilink.citizen.ui.theme.OccupancyGoodContainer
import com.citilink.citizen.ui.theme.OnOccupancyGood
import com.citilink.citizen.ui.theme.OutlineLight
import com.citilink.citizen.ui.theme.OutlineVariantLight
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SecondaryContainerBlue
import com.citilink.citizen.ui.theme.SecondaryTransitBlue
import com.citilink.citizen.ui.theme.SeniorGold
import com.citilink.citizen.ui.theme.SeniorGoldContainer
import com.citilink.citizen.ui.theme.SurfaceContainerHighLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowestLight
import com.citilink.citizen.ui.theme.SurfaceLight

enum class JourneyStatus {
    AWAITING_BOARDING, // PIN active, waiting for any bus
    BOARDED,           // Conductor verified, on Bus #101
    APPROACHING_DEST,  // 1 stop away
    COMPLETED          // Alighted
}

@Composable
fun ActivePinScreen(
    ticket: BookedTicketResult,
    modifier: Modifier = Modifier
) {
    var showQrModal by remember { mutableStateOf(false) }
    var journeyStatus by remember { mutableStateOf(JourneyStatus.AWAITING_BOARDING) }
    var currentStopIndex by remember { mutableIntStateOf(1) } // 1: CBS, 2: Ashok Stambh, 3: RK, 4: Panchavati

    val stopsTimeline = listOf(
        StopTimelineItem(1, "CBS West Stand 3", "Departed", isPassed = currentStopIndex >= 1, isUserBoardingStop = true),
        StopTimelineItem(2, "Ashok Stambh", if (currentStopIndex == 1) "Next (2 min)" else "Passed", isPassed = currentStopIndex >= 2, isCurrentBusLocation = currentStopIndex == 1),
        StopTimelineItem(3, "Ravivar Karanja (RK)", if (currentStopIndex <= 2) "5 mins" else "Passed", isPassed = currentStopIndex >= 3, isCurrentBusLocation = currentStopIndex == 2),
        StopTimelineItem(4, "Panchavati Karanja", if (currentStopIndex <= 3) "8 mins" else "Arrived", isPassed = currentStopIndex >= 4, isCurrentBusLocation = currentStopIndex == 3, isUserDestinationStop = true)
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Top Screen Status Header
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Active Travel Pass",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryLight
                    )
                    Text(
                        text = "Valid on ANY incoming Route 12 bus",
                        fontSize = 12.sp,
                        color = OutlineLight
                    )
                }

                // Validity Countdown Chip
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerLowLight)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Timer,
                        contentDescription = null,
                        tint = SecondaryTransitBlue,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Expires in 82m",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryTransitBlue
                    )
                }
            }
        }

        // 2. THE HERO TRAVEL PIN CARD
        item {
            BentoCard(
                backgroundColor = if (ticket.isSeniorCitizen) SeniorGoldContainer else SurfaceContainerLowestLight,
                border = null,
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top Pass Status Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(PrimaryLight)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = ticket.routeNumber,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "CORRIDOR TRAVEL PIN",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = if (ticket.isSeniorCitizen) SeniorGold else SecondaryTransitBlue,
                                letterSpacing = 0.5.sp
                            )
                        }

                        // Paid Badge
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(OccupancyGoodContainer)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.CheckCircle,
                                contentDescription = null,
                                tint = OccupancyGood,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "₹${ticket.farePaid} PAID",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = OnOccupancyGood
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // THE GIANT 56sp BOLD TRAVEL PIN
                    Text(
                        text = ticket.pin,
                        fontSize = 58.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (ticket.isSeniorCitizen) SeniorGold else SecondaryTransitBlue,
                        letterSpacing = 4.sp,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = if (ticket.isSeniorCitizen) "SENIOR CITIZEN 2-DIGIT CODE" else "STANDARD 3-DIGIT TRAVEL PIN",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = OutlineLight,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Instructions Callout Banner
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (ticket.isSeniorCitizen) Color(0xFFFEF3C7) else SurfaceContainerLowLight)
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Lightbulb,
                                contentDescription = null,
                                tint = if (ticket.isSeniorCitizen) SeniorGold else SecondaryTransitBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Speak or show PIN \"${ticket.pin}\" to the conductor on ANY Route 12 bus arriving at your stop.",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryLight,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Route Span & Passenger Summary
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${ticket.fromStop} ➔ ${ticket.toStop}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryLight
                            )
                            Text(
                                text = "${ticket.passengerCount} Passenger(s) • ${ticket.paymentMode}",
                                fontSize = 11.sp,
                                color = OutlineLight
                            )
                        }

                        // Backup QR Button
                        OutlinedButton(
                            onClick = { showQrModal = true },
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = ButtonDefaults.ContentPadding
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.QrCode2,
                                contentDescription = null,
                                tint = PrimaryLight,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "QR Code",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryLight
                            )
                        }
                    }
                }
            }
        }

        // 3. LIVE JOURNEY STATUS TRACKER (Interactive Demo Simulator)
        item {
            BentoCard(
                backgroundColor = SurfaceContainerLowestLight
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.DirectionsBus,
                                contentDescription = null,
                                tint = SecondaryTransitBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Live Journey Telemetry",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryLight
                            )
                        }

                        // Interactive Simulator Button (For live presentation demo!)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(SurfaceContainerHighLight)
                                .clickable {
                                    journeyStatus = when (journeyStatus) {
                                        JourneyStatus.AWAITING_BOARDING -> JourneyStatus.BOARDED
                                        JourneyStatus.BOARDED -> {
                                            currentStopIndex = 2
                                            JourneyStatus.APPROACHING_DEST
                                        }
                                        JourneyStatus.APPROACHING_DEST -> {
                                            currentStopIndex = 4
                                            JourneyStatus.COMPLETED
                                        }
                                        JourneyStatus.COMPLETED -> {
                                            currentStopIndex = 1
                                            JourneyStatus.AWAITING_BOARDING
                                        }
                                    }
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "⚡ SIMULATE STEP",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = SecondaryTransitBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dynamic Journey Status Banner
                    when (journeyStatus) {
                        JourneyStatus.AWAITING_BOARDING -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SurfaceContainerLowLight)
                                    .padding(10.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(SecondaryTransitBlue, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Awaiting Boarding at CBS Stand 3",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryLight
                                    )
                                    Text(
                                        text = "Incoming: Bus 101 (3m away) • Bus 102 (7m away)",
                                        fontSize = 11.sp,
                                        color = OutlineLight
                                    )
                                }
                            }
                        }
                        JourneyStatus.BOARDED -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(OccupancyGoodContainer)
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    contentDescription = null,
                                    tint = OccupancyGood,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Verified on Bus #101 (MH-15-GV-0101)",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OnOccupancyGood
                                    )
                                    Text(
                                        text = "Seat allocated • Next stop: Ashok Stambh",
                                        fontSize = 11.sp,
                                        color = OnOccupancyGood
                                    )
                                }
                            }
                        }
                        JourneyStatus.APPROACHING_DEST -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color(0xFFFEF3C7))
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.NotificationsActive,
                                    contentDescription = null,
                                    tint = Color(0xFFD97706),
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Destination Approaching!",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF78350F)
                                    )
                                    Text(
                                        text = "Prepare to alight at Panchavati Karanja (Next Stop)",
                                        fontSize = 11.sp,
                                        color = Color(0xFF78350F)
                                    )
                                }
                            }
                        }
                        JourneyStatus.COMPLETED -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SurfaceContainerLowLight)
                                    .padding(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    contentDescription = null,
                                    tint = SecondaryTransitBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "Trip Completed Successfully",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryLight
                                    )
                                    Text(
                                        text = "Thank you for travelling with CitiLink NMPML Nashik",
                                        fontSize = 11.sp,
                                        color = OutlineLight
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Stepped Stop Progress Timeline
                    StopProgressTimeline(stops = stopsTimeline)
                }
            }
        }

        // 4. Offline Resilience Information
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Rounded.Lock,
                    contentDescription = null,
                    tint = OutlineLight,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Offline-Ready • Cached locally on Conductor Terminal",
                    fontSize = 11.sp,
                    color = OutlineLight
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Dynamic Backup QR Dialog
    if (showQrModal) {
        QrCodeModal(
            pin = ticket.pin,
            routeNumber = ticket.routeNumber,
            onDismissRequest = { showQrModal = false }
        )
    }
}
