package com.citilink.citizen.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = OnPrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryTransitBlue,
    onSecondary = OnSecondaryTransitBlue,
    secondaryContainer = SurfaceContainerLowLight,
    onSecondaryContainer = SecondaryTransitBlue,
    tertiary = SeniorGold,
    onTertiary = OnSeniorGold,
    tertiaryContainer = SeniorGoldContainer,
    onTertiaryContainer = OnSeniorGold,
    background = SurfaceLight,
    onBackground = OnSurfaceLight,
    surface = SurfaceLight,
    onSurface = OnSurfaceLight,
    surfaceVariant = SurfaceContainerLight,
    onSurfaceVariant = OnSurfaceVariantLight,
    outline = OutlineLight,
    outlineVariant = OutlineVariantLight,
    surfaceContainerLowest = SurfaceContainerLowestLight,
    surfaceContainerLow = SurfaceContainerLowLight,
    surfaceContainer = SurfaceContainerLight,
    surfaceContainerHigh = SurfaceContainerHighLight,
    surfaceContainerHighest = SurfaceContainerHighestLight
)

private val DarkColorScheme = darkColorScheme(
    primary = SurfaceContainerLowestLight,
    onPrimary = PrimaryLight,
    primaryContainer = PrimaryContainerLight,
    onPrimaryContainer = OnPrimaryContainerLight,
    secondary = SecondaryContainerBlue,
    onSecondary = OnSecondaryTransitBlue,
    secondaryContainer = PrimaryContainerLight,
    onSecondaryContainer = SurfaceContainerHighestLight,
    background = PrimaryLight,
    onBackground = SurfaceContainerLowestLight,
    surface = PrimaryLight,
    onSurface = SurfaceContainerLowestLight,
    surfaceVariant = PrimaryContainerLight,
    onSurfaceVariant = SurfaceContainerHighLight
)

@Composable
fun CitiLinkTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = colorScheme.surface.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CitiLinkTypography,
        content = content
    )
}