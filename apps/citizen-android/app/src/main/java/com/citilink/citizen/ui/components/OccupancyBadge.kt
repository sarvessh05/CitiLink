package com.citilink.citizen.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.citilink.citizen.ui.theme.OccupancyFull
import com.citilink.citizen.ui.theme.OccupancyFullContainer
import com.citilink.citizen.ui.theme.OccupancyGood
import com.citilink.citizen.ui.theme.OccupancyGoodContainer
import com.citilink.citizen.ui.theme.OccupancyModerate
import com.citilink.citizen.ui.theme.OccupancyModerateContainer
import com.citilink.citizen.ui.theme.OnOccupancyFull
import com.citilink.citizen.ui.theme.OnOccupancyGood
import com.citilink.citizen.ui.theme.OnOccupancyModerate

enum class OccupancyLevel {
    GOOD,      // < 60%
    MODERATE,  // 60% - 90%
    FULL       // > 90%
}

@Composable
fun OccupancyBadge(
    occupancyPercent: Int,
    modifier: Modifier = Modifier,
    showLabel: Boolean = true,
    customText: String? = null
) {
    val level = when {
        occupancyPercent < 60 -> OccupancyLevel.GOOD
        occupancyPercent <= 90 -> OccupancyLevel.MODERATE
        else -> OccupancyLevel.FULL
    }

    val (bgColor, dotColor, textColor, defaultLabel) = when (level) {
        OccupancyLevel.GOOD -> Quadruple(
            OccupancyGoodContainer,
            OccupancyGood,
            OnOccupancyGood,
            "Good Seats"
        )
        OccupancyLevel.MODERATE -> Quadruple(
            OccupancyModerateContainer,
            OccupancyModerate,
            OnOccupancyModerate,
            "Standing"
        )
        OccupancyLevel.FULL -> Quadruple(
            OccupancyFullContainer,
            OccupancyFull,
            OnOccupancyFull,
            "Full"
        )
    }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot_pulse"
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(color = bgColor, shape = RoundedCornerShape(999.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(7.dp)
                .scale(scale)
                .background(color = dotColor, shape = CircleShape)
        )

        if (showLabel) {
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = customText ?: defaultLabel,
                color = textColor,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.2.sp
            )
        }
    }
}

private data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
