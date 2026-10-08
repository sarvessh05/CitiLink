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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.LocationCity
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citilink.citizen.data.NashikTransitRepository
import com.citilink.citizen.data.TransitHub
import com.citilink.citizen.ui.theme.OutlineLight
import com.citilink.citizen.ui.theme.OutlineVariantLight
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SecondaryContainerBlue
import com.citilink.citizen.ui.theme.SecondaryTransitBlue
import com.citilink.citizen.ui.theme.SurfaceContainerLowLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowestLight
import com.citilink.citizen.ui.theme.SurfaceLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HubSwitcherModal(
    selectedHub: TransitHub,
    onHubSelected: (TransitHub) -> Unit,
    onDismissRequest: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = SurfaceLight
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Demo Location Switcher",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryLight
                    )
                    Text(
                        text = "Switch Nashik hub to test dynamic UI & arrivals",
                        fontSize = 12.sp,
                        color = OutlineLight
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowLight)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "NASHIK NMPML",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = SecondaryTransitBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(NashikTransitRepository.ALL_HUBS) { hub ->
                    val isSelected = hub.id == selectedHub.id

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (isSelected) SurfaceContainerLowLight else SurfaceContainerLowestLight)
                            .border(
                                width = if (isSelected) 1.5.dp else 1.dp,
                                color = if (isSelected) SecondaryTransitBlue else OutlineVariantLight.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clickable {
                                onHubSelected(hub)
                                onDismissRequest()
                            }
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) SecondaryTransitBlue else SurfaceContainerLowLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Place,
                                contentDescription = null,
                                tint = if (isSelected) Color.White else SecondaryTransitBlue,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = hub.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryLight
                            )
                            Text(
                                text = hub.standLabel,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SecondaryTransitBlue
                            )
                            Text(
                                text = "${hub.totalNearbyBusesCount} live buses • ${hub.areaDescription}",
                                fontSize = 11.sp,
                                color = OutlineLight
                            )
                        }

                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(SecondaryTransitBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Check,
                                    contentDescription = "Selected",
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}
