package com.citilink.citizen.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.DirectionsWalk
import androidx.compose.material.icons.rounded.CardMembership
import androidx.compose.material.icons.rounded.ConfirmationNumber
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.NearMe
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Radar
import androidx.compose.material.icons.rounded.Search
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
import com.citilink.citizen.data.TransitHub
import com.citilink.citizen.ui.components.BentoCard
import com.citilink.citizen.ui.components.HubSwitcherModal
import com.citilink.citizen.ui.components.LiveBusArrivalCard
import com.citilink.citizen.ui.theme.OutlineLight
import com.citilink.citizen.ui.theme.OutlineVariantLight
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SecondaryTransitBlue
import com.citilink.citizen.ui.theme.SeniorGold
import com.citilink.citizen.ui.theme.SeniorGoldContainer
import com.citilink.citizen.ui.theme.SurfaceContainerHighLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowestLight
import com.citilink.citizen.ui.theme.SurfaceLight

@Composable
fun HomeScreen(
    selectedHub: TransitHub,
    onHubSelected: (TransitHub) -> Unit,
    onNavigateToCorridor: () -> Unit,
    onNavigateToActivePin: () -> Unit,
    onBookBus: (BusArrival) -> Unit,
    activeTravelPin: String? = "142",
    modifier: Modifier = Modifier
) {
    var showHubSwitcher by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Commuter Greeting & Dynamic Hub Stand Pill
        item {
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Good evening, Rahul",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight,
                            letterSpacing = (-0.3).sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "👋", fontSize = 18.sp)
                    }

                    // Interactive Hub Location Pill (Tap to switch)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(SurfaceContainerLowLight)
                            .clickable { showHubSwitcher = true }
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.NearMe,
                            contentDescription = null,
                            tint = SecondaryTransitBlue,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${selectedHub.name} • ${selectedHub.standLabel}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryLight
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "▾",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryTransitBlue
                        )
                    }
                }

                // Notification Bell
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainerLowLight)
                        .clickable { },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Notifications,
                        contentDescription = "Alerts",
                        tint = PrimaryLight,
                        modifier = Modifier.size(20.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(SecondaryTransitBlue)
                            .align(Alignment.TopEnd)
                            .padding(top = 2.dp, end = 2.dp)
                    )
                }
            }
        }

        // 2. Destination Search Bar
        item {
            BentoCard(
                onClick = onNavigateToCorridor,
                backgroundColor = SurfaceContainerLowestLight
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLowLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "Search",
                                tint = SecondaryTransitBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Where are you going?",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryLight
                            )
                            Text(
                                text = "Search stop, route or city hub",
                                fontSize = 12.sp,
                                color = OutlineLight
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainerHighLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Mic,
                                contentDescription = "Voice Search",
                                tint = PrimaryLight,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Contextual Search Chips based on Selected Hub
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        selectedHub.quickSearchTags.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(SurfaceContainerLowLight)
                                    .clickable { onNavigateToCorridor() }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = tag,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = SecondaryTransitBlue
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Quick-Action Bento Grid
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Card 1: Live Corridor Radar
                BentoCard(
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToCorridor,
                    backgroundColor = SurfaceContainerLowestLight
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLowLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Radar,
                                    contentDescription = "Radar",
                                    tint = SecondaryTransitBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(SurfaceContainerHighLight)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "${selectedHub.totalNearbyBusesCount} ACTIVE",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SecondaryTransitBlue
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "Live Radar",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )
                        Text(
                            text = "Route 12 Corridor",
                            fontSize = 11.sp,
                            color = OutlineLight
                        )
                    }
                }

                // Card 2: Active Travel PIN or Passes
                BentoCard(
                    modifier = Modifier.weight(1f),
                    onClick = onNavigateToActivePin,
                    backgroundColor = if (activeTravelPin != null) SurfaceContainerLowLight else SurfaceContainerLowestLight
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (activeTravelPin != null) SecondaryTransitBlue else SurfaceContainerHighLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.ConfirmationNumber,
                                    contentDescription = "PIN",
                                    tint = if (activeTravelPin != null) Color.White else PrimaryLight,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            if (activeTravelPin != null) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(999.dp))
                                        .background(SecondaryTransitBlue)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "PIN #$activeTravelPin",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = if (activeTravelPin != null) "Active Travel PIN" else "Smart Pass",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )
                        Text(
                            text = if (activeTravelPin != null) "Ready for any bus" else "NMPML Monthly",
                            fontSize = 11.sp,
                            color = OutlineLight
                        )
                    }
                }
            }
        }

        // 4. Nearby Stands Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Nearby Transit Stands",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryLight
                    )
                    Text(
                        text = "${selectedHub.nearbyStops.size} Stands",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SecondaryTransitBlue
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    selectedHub.nearbyStops.forEach { stop ->
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainerLowestLight)
                                .border(1.dp, OutlineVariantLight.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                                .clickable { onNavigateToCorridor() }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.DirectionsWalk,
                                contentDescription = null,
                                tint = SecondaryTransitBlue,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = stop.name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryLight
                                )
                                Text(
                                    text = "${stop.standLabel} • ${stop.walkingDistanceMeters}m walk",
                                    fontSize = 10.sp,
                                    color = OutlineLight
                                )
                            }
                        }
                    }
                }
            }
        }

        // 5. Live Incoming Buses Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Live Incoming Buses",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryLight
                    )
                    Text(
                        text = "Arriving at ${selectedHub.standLabel}",
                        fontSize = 11.sp,
                        color = OutlineLight
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerLowLight)
                        .clickable { onNavigateToCorridor() }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "All Corridors",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryTransitBlue
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                        contentDescription = null,
                        tint = SecondaryTransitBlue,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }

        // 6. Live Buses Dynamic Feed
        items(selectedHub.liveBuses) { bus ->
            LiveBusArrivalCard(
                bus = bus,
                onBookClick = { onBookBus(bus) },
                onCardClick = { onNavigateToCorridor() }
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Modal Sheet for Switching Hubs
    if (showHubSwitcher) {
        HubSwitcherModal(
            selectedHub = selectedHub,
            onHubSelected = {
                onHubSelected(it)
                showHubSwitcher = false
            },
            onDismissRequest = { showHubSwitcher = false }
        )
    }
}
