package com.citilink.citizen.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.citilink.citizen.ui.theme.OutlineVariantLight
import com.citilink.citizen.ui.theme.SurfaceContainerLowestLight

@Composable
fun BentoCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    backgroundColor: Color = SurfaceContainerLowestLight,
    shape: Shape = RoundedCornerShape(16.dp),
    elevation: Dp = 2.dp,
    border: BorderStroke? = BorderStroke(1.dp, OutlineVariantLight.copy(alpha = 0.35f)),
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        modifier = modifier
            .clip(shape)
            .then(
                if (onClick != null) Modifier.clickable { onClick() } else Modifier
            ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        border = border
    ) {
        Box(
            modifier = Modifier.padding(14.dp),
            content = content
        )
    }
}
