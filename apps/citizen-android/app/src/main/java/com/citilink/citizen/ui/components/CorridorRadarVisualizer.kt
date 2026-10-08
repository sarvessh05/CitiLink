package com.citilink.citizen.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.Radar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citilink.citizen.ui.theme.OccupancyGood
import com.citilink.citizen.ui.theme.OccupancyModerate
import com.citilink.citizen.ui.theme.OutlineLight
import com.citilink.citizen.ui.theme.OutlineVariantLight
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SecondaryContainerBlue
import com.citilink.citizen.ui.theme.SecondaryTransitBlue
import com.citilink.citizen.ui.theme.SurfaceContainerHighLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowestLight

data class RadarBusPin(
    val busId: String,
    val fleetNumber: String,
    val progressFraction: Float, // 0.0 (CBS) to 1.0 (Nimani)
    val etaString: String,
    val occupancyPercent: Int
)

@Composable
fun CorridorRadarVisualizer(
    activeBuses: List<RadarBusPin>,
    selectedBusId: String? = null,
    onBusClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "radar_sweep")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "radar_pulse"
    )

    BentoCard(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = SurfaceContainerLowestLight
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Card Header: Telemetry Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Radar,
                        contentDescription = null,
                        tint = SecondaryTransitBlue,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Corridor Live Telemetry",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryLight
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(PrimaryLight)
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .scale(pulseScale)
                            .background(Color(0xFF4CD7F6), CircleShape)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "RADAR ACTIVE",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Sub-container with Visual Track & Station Labels
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLowLight)
                    .padding(horizontal = 14.dp, vertical = 16.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Stop Names Row (Origin ➔ Waypoints ➔ Terminal)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = "ORIGIN",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = OutlineLight
                            )
                            Text(
                                text = "CBS",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryLight
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "WAYPOINT",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = OutlineLight
                            )
                            Text(
                                text = "Ashok Stambh",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryLight
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "HUB",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = OutlineLight
                            )
                            Text(
                                text = "Ravivar K.",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryLight
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "TERMINAL",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = SecondaryTransitBlue
                            )
                            Text(
                                text = "Panchavati",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryTransitBlue
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Track Canvas with 4 Stop Nodes + Segment Color Coding
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxWidth().height(20.dp).align(Alignment.Center)) {
                            val startX = 16.dp.toPx()
                            val endX = size.width - 16.dp.toPx()
                            val step = (endX - startX) / 3f

                            // Segment 1 (CBS ➔ Ashok Stambh): Free (🟢)
                            drawLine(
                                color = OccupancyGood,
                                start = Offset(startX, size.height / 2),
                                end = Offset(startX + step, size.height / 2),
                                strokeWidth = 5.dp.toPx(),
                                cap = StrokeCap.Round
                            )

                            // Segment 2 (Ashok Stambh ➔ RK): Moderate (🟡)
                            drawLine(
                                color = OccupancyModerate,
                                start = Offset(startX + step, size.height / 2),
                                end = Offset(startX + step * 2, size.height / 2),
                                strokeWidth = 5.dp.toPx()
                            )

                            // Segment 3 (RK ➔ Panchavati): Transit Blue (🔵)
                            drawLine(
                                color = SecondaryTransitBlue,
                                start = Offset(startX + step * 2, size.height / 2),
                                end = Offset(endX, size.height / 2),
                                strokeWidth = 5.dp.toPx(),
                                cap = StrokeCap.Round
                            )

                            // Station Markers
                            drawCircle(color = Color.White, radius = 7.dp.toPx(), center = Offset(startX, size.height / 2))
                            drawCircle(color = PrimaryLight, radius = 5.dp.toPx(), center = Offset(startX, size.height / 2))

                            drawCircle(color = Color.White, radius = 6.dp.toPx(), center = Offset(startX + step, size.height / 2))
                            drawCircle(color = OutlineLight, radius = 4.dp.toPx(), center = Offset(startX + step, size.height / 2))

                            drawCircle(color = Color.White, radius = 6.dp.toPx(), center = Offset(startX + step * 2, size.height / 2))
                            drawCircle(color = OutlineLight, radius = 4.dp.toPx(), center = Offset(startX + step * 2, size.height / 2))

                            drawCircle(color = Color.White, radius = 7.dp.toPx(), center = Offset(endX, size.height / 2))
                            drawCircle(color = SecondaryTransitBlue, radius = 5.dp.toPx(), center = Offset(endX, size.height / 2))
                        }

                        // Render Dynamic Bus Markers on the Track
                        activeBuses.forEach { bus ->
                            val isHighlighted = bus.busId == selectedBusId
                            val isTopMarker = bus.fleetNumber == "Bus #101"

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(if (isTopMarker) Alignment.TopStart else Alignment.BottomStart)
                                    .padding(start = ((bus.progressFraction * 240) + 12).dp)
                                    .clickable { onBusClick(bus.busId) }
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(999.dp))
                                        .background(if (isHighlighted) SecondaryContainerBlue else SecondaryTransitBlue)
                                        .border(1.dp, Color.White, CircleShape)
                                        .padding(horizontal = 6.dp, vertical = 3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.DirectionsBus,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(11.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "${bus.fleetNumber} • ${bus.etaString}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Segment Legend Key
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).background(OccupancyGood, CircleShape))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Fast / Free", fontSize = 10.sp, color = OutlineLight)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).background(OccupancyModerate, CircleShape))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Moderate Crowd", fontSize = 10.sp, color = OutlineLight)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).background(SecondaryTransitBlue, CircleShape))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "3.8 km Total Span", fontSize = 10.sp, color = OutlineLight)
                }
            }
        }
    }
}
