package com.citilink.citizen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citilink.citizen.ui.theme.OccupancyGood
import com.citilink.citizen.ui.theme.OutlineLight
import com.citilink.citizen.ui.theme.OutlineVariantLight
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SecondaryTransitBlue
import com.citilink.citizen.ui.theme.SurfaceContainerHighLight

data class StopTimelineItem(
    val sequenceNumber: Int,
    val stopName: String,
    val etaString: String? = null,
    val isPassed: Boolean = false,
    val isCurrentBusLocation: Boolean = false,
    val isUserBoardingStop: Boolean = false,
    val isUserDestinationStop: Boolean = false
)

@Composable
fun StopProgressTimeline(
    stops: List<StopTimelineItem>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        stops.forEachIndexed { index, item ->
            val isLast = index == stops.size - 1

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Timeline Column (Node + Line)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(32.dp)
                ) {
                    when {
                        item.isCurrentBusLocation -> {
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .background(SecondaryTransitBlue, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.DirectionsBus,
                                    contentDescription = "Current Bus",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        item.isUserDestinationStop -> {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .background(OccupancyGood, CircleShape)
                                    .border(2.dp, Color.White, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(Color.White, CircleShape)
                                )
                            }
                        }
                        item.isPassed -> {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(SurfaceContainerHighLight, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = "Passed",
                                    tint = OutlineLight,
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                        else -> {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .background(Color.White, CircleShape)
                                    .border(
                                        2.dp,
                                        if (item.isUserBoardingStop) SecondaryTransitBlue else OutlineVariantLight,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (item.isUserBoardingStop) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .background(SecondaryTransitBlue, CircleShape)
                                    )
                                }
                            }
                        }
                    }

                    if (!isLast) {
                        Box(
                            modifier = Modifier
                                .width(3.dp)
                                .height(38.dp)
                                .background(
                                    if (item.isPassed) OutlineVariantLight.copy(alpha = 0.5f)
                                    else SecondaryTransitBlue.copy(alpha = 0.35f),
                                    RoundedCornerShape(2.dp)
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Stop Details Column
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = if (isLast) 0.dp else 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.stopName,
                            fontSize = 14.sp,
                            fontWeight = if (item.isCurrentBusLocation || item.isUserBoardingStop || item.isUserDestinationStop)
                                FontWeight.Bold else FontWeight.Normal,
                            color = if (item.isPassed) OutlineLight else PrimaryLight,
                            modifier = Modifier.weight(1f)
                        )

                        if (item.etaString != null) {
                            Text(
                                text = item.etaString,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (item.isPassed) OutlineLight else SecondaryTransitBlue
                            )
                        }
                    }

                    if (item.isUserBoardingStop) {
                        Text(
                            text = "Your Boarding Stop",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryTransitBlue,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    } else if (item.isUserDestinationStop) {
                        Text(
                            text = "Your Destination",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = OccupancyGood,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }
    }
}
