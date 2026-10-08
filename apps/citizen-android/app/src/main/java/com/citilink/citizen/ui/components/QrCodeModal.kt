package com.citilink.citizen.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.QrCode2
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citilink.citizen.ui.theme.OutlineLight
import com.citilink.citizen.ui.theme.OutlineVariantLight
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SecondaryTransitBlue
import com.citilink.citizen.ui.theme.SurfaceContainerLowLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowestLight
import com.citilink.citizen.ui.theme.SurfaceLight

@Composable
fun QrCodeModal(
    pin: String,
    routeNumber: String,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = onDismissRequest,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryLight)
            ) {
                Text(text = "Close Backup QR", fontWeight = FontWeight.Bold)
            }
        },
        containerColor = SurfaceContainerLowestLight,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerLowLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.QrCode2,
                            contentDescription = null,
                            tint = SecondaryTransitBlue,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "Backup Scanner QR",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryLight
                        )
                        Text(
                            text = "PIN #$pin • $routeNumber",
                            fontSize = 11.sp,
                            color = SecondaryTransitBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                IconButton(
                    onClick = onDismissRequest,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = "Close",
                        tint = OutlineLight,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "If ambient bus noise is high, show this dynamic encrypted QR to the conductor for sub-second camera scanning.",
                    fontSize = 12.sp,
                    color = OutlineLight,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // High-Contrast QR Code Visual Pattern Box
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .border(2.dp, PrimaryLight, RoundedCornerShape(16.dp))
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val gridSize = 9
                        val cellSize = size.width / gridSize

                        // Draw stylized high-contrast QR Matrix blocks
                        val pattern = listOf(
                            listOf(1,1,1,1,1,0,1,1,1),
                            listOf(1,0,0,0,1,0,1,0,1),
                            listOf(1,0,1,0,1,1,1,0,1),
                            listOf(1,0,0,0,1,0,0,1,1),
                            listOf(1,1,1,1,1,1,0,1,0),
                            listOf(0,0,1,0,1,1,1,0,1),
                            listOf(1,1,0,1,0,0,1,1,1),
                            listOf(1,0,1,1,1,0,0,0,1),
                            listOf(1,1,1,0,1,1,1,1,1)
                        )

                        for (r in 0 until gridSize) {
                            for (c in 0 until gridSize) {
                                if (pattern[r][c] == 1) {
                                    drawRect(
                                        color = Color(0xFF0B1C30),
                                        topLeft = Offset(c * cellSize, r * cellSize),
                                        size = Size(cellSize - 1f, cellSize - 1f)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "AUTH CODE: NMPML-R12-PIN-$pin",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = PrimaryLight,
                    letterSpacing = 0.5.sp
                )
            }
        }
    )
}
