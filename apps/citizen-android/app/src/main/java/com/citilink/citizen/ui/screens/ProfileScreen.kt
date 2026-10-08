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
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.CardMembership
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.DirectionsBus
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.HeadsetMic
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.QrCode2
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
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
import com.citilink.citizen.ui.components.BentoCard
import com.citilink.citizen.ui.theme.OccupancyGood
import com.citilink.citizen.ui.theme.OccupancyGoodContainer
import com.citilink.citizen.ui.theme.OnOccupancyGood
import com.citilink.citizen.ui.theme.OutlineLight
import com.citilink.citizen.ui.theme.OutlineVariantLight
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SecondaryTransitBlue
import com.citilink.citizen.ui.theme.SurfaceContainerHighLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowestLight
import com.citilink.citizen.ui.theme.SurfaceLight

data class PastTripItem(
    val id: String,
    val routeNumber: String,
    val origin: String,
    val destination: String,
    val dateString: String,
    val pinUsed: String,
    val fare: Int,
    val busNumber: String
)

@Composable
fun ProfileScreen(
    onRebookRoute: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isCacheRefreshed by remember { mutableStateOf(false) }

    val pastTrips = listOf(
        PastTripItem("t_1", "Route 12", "CBS West Stand 3", "Panchavati Karanja", "Today • 08:30 PM", "142", 20, "MH-15-GV-0101"),
        PastTripItem("t_2", "Route 101", "Nashik Road Rly Stn", "CBS Takkar Bazaar", "Yesterday • 06:15 PM", "219", 15, "MH-15-GV-0201"),
        PastTripItem("t_3", "Route 205", "College Road BYK", "CBS Downtown", "06 Oct • 09:40 AM", "087", 15, "MH-15-GV-0205")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Screen Title Header
        item {
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Passenger Profile",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryLight
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SurfaceContainerLowLight)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Verified,
                        contentDescription = null,
                        tint = SecondaryTransitBlue,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "VERIFIED CITIZEN",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = SecondaryTransitBlue
                    )
                }
            }
        }

        // 2. User Info Card
        item {
            BentoCard(
                backgroundColor = SurfaceContainerLowestLight
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(PrimaryLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = "Avatar",
                            tint = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Rahul Deshmukh",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )
                        Text(
                            text = "+91 98220 44102 • Nashik Urban",
                            fontSize = 12.sp,
                            color = OutlineLight
                        )
                        Text(
                            text = "Primary Hub: New CBS Takkar Bazaar",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SecondaryTransitBlue,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }
            }
        }

        // 3. Smart Transit Pass Card
        item {
            BentoCard(
                backgroundColor = PrimaryLight,
                border = null
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.CardMembership,
                                contentDescription = null,
                                tint = Color(0xFF4CD7F6),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "NMPML Monthly Pass",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(Color(0xFF131B2E))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "ACTIVE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF4CD7F6)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "ALL-CORRIDOR UNLIMITED",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.LightGray,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Valid till 31 Oct 2026 • 23 Days Left",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Progress Bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(Color.DarkGray)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.65f)
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(SecondaryTransitBlue)
                        )
                    }
                }
            }
        }

        // 4. Past Travel PINs & Trip Receipts
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Trips & PIN History",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryLight
                    )
                    Text(
                        text = "${pastTrips.size} Completed",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SecondaryTransitBlue
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                pastTrips.forEach { trip ->
                    BentoCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        backgroundColor = SurfaceContainerLowestLight
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
                                        .size(38.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SurfaceContainerLowLight),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.ReceiptLong,
                                        contentDescription = null,
                                        tint = SecondaryTransitBlue,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                                Column {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = trip.routeNumber,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = PrimaryLight
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "PIN #${trip.pinUsed}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Black,
                                            color = SecondaryTransitBlue
                                        )
                                    }
                                    Text(
                                        text = "${trip.origin} ➔ ${trip.destination}",
                                        fontSize = 11.sp,
                                        color = OutlineLight
                                    )
                                    Text(
                                        text = "${trip.dateString} • ₹${trip.fare} Paid",
                                        fontSize = 10.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            // Re-book Button
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLowLight)
                                    .clickable { onRebookRoute(trip.routeNumber) }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Re-Book",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryTransitBlue
                                )
                            }
                        }
                    }
                }
            }
        }

        // 5. Transit Authority Support & Safety Helpline
        item {
            BentoCard(
                backgroundColor = SurfaceContainerLowestLight
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Transit Authority & Helplines",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryLight
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Depot Central Control
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLowLight)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.HeadsetMic,
                                contentDescription = null,
                                tint = SecondaryTransitBlue,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Takkar Bazaar Central Control",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryLight
                                )
                                Text(
                                    text = "0253-2309308 • NMPML Depot",
                                    fontSize = 11.sp,
                                    color = OutlineLight
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Rounded.Call,
                            contentDescription = "Call",
                            tint = SecondaryTransitBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Women & Commuter Safety Helpline
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLowLight)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.Security,
                                contentDescription = null,
                                tint = Color(0xFF10B981),
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Nashik Police Transit Safety",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryLight
                                )
                                Text(
                                    text = "1090 / 112 • 24/7 Emergency Line",
                                    fontSize = 11.sp,
                                    color = OutlineLight
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.Rounded.Call,
                            contentDescription = "Call",
                            tint = Color(0xFF10B981),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // 6. System Cache & Version Info
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CitiLink Citizen V1.0.0 (Nashik)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryLight
                    )
                    Text(
                        text = if (isCacheRefreshed) "Manifest Cache: Synced (Just now)" else "Manifest Cache: Active (14 stops cached)",
                        fontSize = 10.sp,
                        color = if (isCacheRefreshed) OccupancyGood else OutlineLight
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerLowLight)
                        .clickable { isCacheRefreshed = true }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "Refresh Cache",
                        tint = SecondaryTransitBlue,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Re-Sync",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryTransitBlue
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
