package com.citilink.citizen.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.ConfirmationNumber
import androidx.compose.material.icons.rounded.Elderly
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Payment
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citilink.citizen.ui.components.BentoCard
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class BookedTicketResult(
    val pin: String,
    val routeNumber: String,
    val fromStop: String,
    val toStop: String,
    val passengerCount: Int,
    val isSeniorCitizen: Boolean,
    val farePaid: Int,
    val paymentMode: String
)

@Composable
fun BookingScreen(
    onBackClick: () -> Unit,
    onBookingSuccess: (BookedTicketResult) -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()

    var fromStop by remember { mutableStateOf("CBS West Stand 3") }
    var toStop by remember { mutableStateOf("Panchavati Karanja") }
    var passengerCount by remember { mutableIntStateOf(1) }
    var isSeniorCitizen by remember { mutableStateOf(false) }
    var selectedPaymentMode by remember { mutableStateOf("UPI / GPay") }
    var isProcessingPayment by remember { mutableStateOf(false) }

    val baseFarePerPax = if (toStop.contains("Panchavati")) 20 else 15
    val totalFare = baseFarePerPax * passengerCount

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceLight)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. Navigation Header
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                            text = "Book Route Corridor Ticket",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )
                        Text(
                            text = "Route 12 • Valid across all incoming buses",
                            fontSize = 11.sp,
                            color = OutlineLight
                        )
                    }
                }
            }

            // 2. Stop Selection Card
            item {
                BentoCard(
                    backgroundColor = SurfaceContainerLowestLight
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Journey Stops",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Origin Stop
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLowLight)
                                .padding(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(PrimaryLight, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "BOARDING STOP",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = OutlineLight
                                )
                                Text(
                                    text = fromStop,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryLight
                                )
                            }
                        }

                        // Swap Divider
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            HorizontalDivider(
                                color = OutlineVariantLight.copy(alpha = 0.4f),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceContainerHighLight)
                                    .clickable {
                                        val temp = fromStop
                                        fromStop = toStop
                                        toStop = temp
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.SwapVert,
                                    contentDescription = "Swap Stops",
                                    tint = SecondaryTransitBlue,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        // Destination Stop
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLowLight)
                                .padding(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .background(SecondaryTransitBlue, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "DESTINATION STOP",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = SecondaryTransitBlue
                                )
                                Text(
                                    text = toStop,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryLight
                                )
                            }
                        }
                    }
                }
            }

            // 3. Passenger Count Selector
            item {
                BentoCard(
                    backgroundColor = SurfaceContainerLowestLight
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Passengers",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryLight
                            )
                            Text(
                                text = "Single Travel PIN covers group",
                                fontSize = 11.sp,
                                color = OutlineLight
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (passengerCount > 1) SurfaceContainerHighLight else SurfaceContainerLowLight)
                                    .clickable(enabled = passengerCount > 1) { passengerCount-- },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Remove,
                                    contentDescription = "Decrease",
                                    tint = if (passengerCount > 1) PrimaryLight else OutlineLight,
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            Text(
                                text = "$passengerCount",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = PrimaryLight,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (passengerCount < 6) SecondaryTransitBlue else SurfaceContainerLowLight)
                                    .clickable(enabled = passengerCount < 6) { passengerCount++ },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Add,
                                    contentDescription = "Increase",
                                    tint = if (passengerCount < 6) Color.White else OutlineLight,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // 4. Senior Citizen (60+ years) Toggle Card — (Triggers 2-Digit PIN Pool)
            item {
                BentoCard(
                    backgroundColor = if (isSeniorCitizen) SeniorGoldContainer else SurfaceContainerLowestLight,
                    border = if (isSeniorCitizen) null else null
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(if (isSeniorCitizen) SeniorGold else SurfaceContainerLowLight),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Elderly,
                                    contentDescription = null,
                                    tint = if (isSeniorCitizen) Color.White else PrimaryLight,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Senior Citizen (60+)",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryLight
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(if (isSeniorCitizen) SeniorGold else SurfaceContainerHighLight)
                                            .padding(horizontal = 5.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = "2-DIGIT PIN",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            color = if (isSeniorCitizen) Color.White else SecondaryTransitBlue
                                        )
                                    }
                                }

                                Text(
                                    text = "Allocates a short 2-digit PIN (10-99) for effortless voice confirmation",
                                    fontSize = 11.sp,
                                    color = if (isSeniorCitizen) Color(0xFF78350F) else OutlineLight,
                                    lineHeight = 15.sp,
                                    modifier = Modifier.padding(top = 1.dp)
                                )
                            }
                        }

                        Switch(
                            checked = isSeniorCitizen,
                            onCheckedChange = { isSeniorCitizen = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = SeniorGold
                            )
                        )
                    }
                }
            }

            // 5. Payment Methods
            item {
                BentoCard(
                    backgroundColor = SurfaceContainerLowestLight
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Payment Method",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        listOf("UPI / GPay / PhonePe", "CitiLink Transit Wallet", "Debit / Credit Card").forEach { mode ->
                            val isSelected = mode == selectedPaymentMode
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) SurfaceContainerLowLight else Color.Transparent)
                                    .border(
                                        1.dp,
                                        if (isSelected) SecondaryTransitBlue else OutlineVariantLight.copy(alpha = 0.3f),
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { selectedPaymentMode = mode }
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (mode.contains("Wallet")) Icons.Rounded.AccountBalanceWallet else Icons.Rounded.Payment,
                                        contentDescription = null,
                                        tint = if (isSelected) SecondaryTransitBlue else OutlineLight,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = mode,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = PrimaryLight
                                    )
                                }

                                if (isSelected) {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(CircleShape)
                                            .background(SecondaryTransitBlue),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Check,
                                            contentDescription = "Selected",
                                            tint = Color.White,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 6. Fare Breakdown Summary
            item {
                BentoCard(
                    backgroundColor = SurfaceContainerLowestLight
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Fare Breakdown",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Corridor Base Fare (₹$baseFarePerPax × $passengerCount)", fontSize = 12.sp, color = OutlineLight)
                            Text(text = "₹$totalFare", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = PrimaryLight)
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "NMPML Municipal Surcharge", fontSize = 12.sp, color = OutlineLight)
                            Text(text = "₹0 (Waived)", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF10B981))
                        }

                        HorizontalDivider(
                            color = OutlineVariantLight.copy(alpha = 0.4f),
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Total Payable", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = PrimaryLight)
                            Text(
                                text = "₹$totalFare",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Black,
                                color = SecondaryTransitBlue
                            )
                        }
                    }
                }
            }

            // 7. Security Guarantee Pill
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
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
                        text = "Encrypted Corridor PIN Allocation • Guaranteed Seat Availability",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = OutlineLight
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(84.dp))
            }
        }

        // 8. Bottom Sticky Payment Action Bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(SurfaceLight.copy(alpha = 0.95f))
                .padding(16.dp)
        ) {
            Button(
                onClick = {
                    if (!isProcessingPayment) {
                        isProcessingPayment = true
                        coroutineScope.launch {
                            val result = com.citilink.citizen.data.CitiLinkRepository.bookCorridorTicket(
                                routeId = "route_12",
                                fromStop = fromStop,
                                toStop = toStop,
                                passengerCount = passengerCount,
                                isSeniorCitizen = isSeniorCitizen,
                                paymentMode = selectedPaymentMode
                            )
                            delay(600) // Visual confirmation delay
                            isProcessingPayment = false
                            onBookingSuccess(result)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSeniorCitizen) SeniorGold else SecondaryTransitBlue
                ),
                enabled = !isProcessingPayment
            ) {
                if (isProcessingPayment) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Allocating Corridor PIN...",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.ConfirmationNumber,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Pay ₹$totalFare & Generate Travel PIN",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}
