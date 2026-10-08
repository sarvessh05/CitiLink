package com.citilink.citizen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ElectricBolt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citilink.citizen.data.BusArrival
import com.citilink.citizen.ui.theme.OutlineLight
import com.citilink.citizen.ui.theme.OutlineVariantLight
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SecondaryTransitBlue
import com.citilink.citizen.ui.theme.SurfaceContainerHighLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowestLight

@Composable
fun LiveBusArrivalCard(
    bus: BusArrival,
    onBookClick: (BusArrival) -> Unit,
    onCardClick: (BusArrival) -> Unit,
    modifier: Modifier = Modifier
) {
    BentoCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onCardClick(bus) },
        backgroundColor = SurfaceContainerLowestLight
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Top Row: Route & Fleet Badge + Live ETA Countdown
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Route Pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(PrimaryLight)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = bus.routeNumber,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }

                    Text(
                        text = bus.fleetNumber,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryLight
                    )

                    if (bus.isElectricBus) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(0xFFE0F2FE))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ElectricBolt,
                                contentDescription = "Electric Bus",
                                tint = Color(0xFF0284C7),
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(2.dp))
                            Text(
                                text = "EV",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0284C7)
                            )
                        }
                    }
                }

                // ETA Countdown Chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowLight)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (bus.etaMinutes <= 0) "Arriving Now" else "${bus.etaMinutes} min away",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryTransitBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Middle Row: Destination & Route Name
            Text(
                text = bus.destination,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryLight
            )
            Text(
                text = "Next: ${bus.nextStopName} • Reg: ${bus.busNumber}",
                fontSize = 12.sp,
                color = OutlineLight,
                modifier = Modifier.padding(top = 1.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom Row: Occupancy Badge + 1-Tap Booking Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Occupancy Pill with seat numbers
                OccupancyBadge(
                    occupancyPercent = bus.occupancyPercent,
                    customText = "${bus.availableSeats} Seats Free (${bus.occupancyPercent}%)"
                )

                Button(
                    onClick = { onBookClick(bus) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SecondaryTransitBlue),
                    contentPadding = ButtonDefaults.ContentPadding
                ) {
                    Text(
                        text = "Book PIN",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}
