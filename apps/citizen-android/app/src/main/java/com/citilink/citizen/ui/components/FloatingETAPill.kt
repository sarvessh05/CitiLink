package com.citilink.citizen.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import com.citilink.citizen.ui.theme.PrimaryLight
import com.citilink.citizen.ui.theme.SecondaryTransitBlue
import com.citilink.citizen.ui.theme.SurfaceContainerLowLight

@Composable
fun FloatingETAPill(
    busLabel: String,
    etaMinutes: Int,
    occupancyPercent: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(color = SurfaceContainerLowLight, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.DirectionsBus,
            contentDescription = null,
            tint = SecondaryTransitBlue,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = busLabel,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryLight
        )
        Text(
            text = "•",
            fontSize = 12.sp,
            color = Color.Gray
        )
        Text(
            text = if (etaMinutes <= 0) "Arriving now" else "${etaMinutes}m away",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = SecondaryTransitBlue
        )
        Spacer(modifier = Modifier.width(2.dp))
        OccupancyBadge(occupancyPercent = occupancyPercent, showLabel = false)
    }
}
