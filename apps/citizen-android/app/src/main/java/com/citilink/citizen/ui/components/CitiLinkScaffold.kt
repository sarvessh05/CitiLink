package com.citilink.citizen.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.LocalPolice
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import com.citilink.citizen.ui.theme.AlertRed
import com.citilink.citizen.ui.theme.OccupancyFullContainer
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SurfaceLight

@Composable
fun CitiLinkScaffold(
    selectedTab: CitizenNavTab,
    onTabSelected: (CitizenNavTab) -> Unit,
    screenSubtitle: String = "Live Radar",
    activePinCount: Int = 1,
    content: @Composable () -> Unit
) {
    var showSosDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopNavBar(
                currentScreenSubtitle = screenSubtitle,
                onSosClick = { showSosDialog = true }
            )
        },
        bottomBar = {
            BottomNavBar(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
                activePinCount = activePinCount
            )
        },
        containerColor = SurfaceLight
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            content()
        }
    }

    if (showSosDialog) {
        AlertDialog(
            onDismissRequest = { showSosDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(OccupancyFullContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Warning,
                            contentDescription = null,
                            tint = AlertRed,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Emergency Transit SOS",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryLight
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = "Do you need urgent assistance? CitiLink will instantly alert the Nashik Central Depot Control and share your live GPS coordinates.",
                        fontSize = 13.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceLight)
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.LocalPolice,
                            contentDescription = null,
                            tint = PrimaryLight,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Nashik Transit Helpline",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "1800-233-0244 / 112",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showSosDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = AlertRed)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Call,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Call Control", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showSosDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}
