package com.example.ktsproject_reptrack.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = DarkAccentOrange,
    onPrimary = DarkTextOnAccent,
    primaryContainer = DarkAccentOrange,
    onPrimaryContainer = DarkTextPrimary,

    secondary = DarkAccentRed,
    onSecondary = DarkTextOnAccent,
    secondaryContainer = DarkAccentRed,
    onSecondaryContainer = DarkTextPrimary,

    tertiary = DarkAccentGreen,
    onTertiary = DarkTextOnAccent,
    tertiaryContainer = DarkAccentGreen,
    onTertiaryContainer = DarkTextPrimary,

    background = DarkBackground,
    onBackground = DarkTextPrimary,

    surface = DarkCard,
    onSurface = DarkTextPrimary,
    surfaceVariant = DarkCardElevated,
    onSurfaceVariant = DarkTextSecondary,

    outline = DarkTextSecondary,
    error = DarkAccentRed,
    onError = DarkTextOnAccent
)

private val LightColorScheme = lightColorScheme(
    primary = LightAccentOrange,
    onPrimary = LightTextOnAccent,
    primaryContainer = LightAccentOrange,
    onPrimaryContainer = LightTextPrimary,

    secondary = LightAccentRed,
    onSecondary = LightTextOnAccent,
    secondaryContainer = LightAccentRed,
    onSecondaryContainer = LightTextPrimary,

    tertiary = LightAccentGreen,
    onTertiary = LightTextOnAccent,
    tertiaryContainer = LightAccentGreen,
    onTertiaryContainer = LightTextPrimary,

    background = LightBackground,
    onBackground = LightTextPrimary,

    surface = LightCard,
    onSurface = LightTextPrimary,
    surfaceVariant = LightCard,
    onSurfaceVariant = LightTextSecondary,

    outline = LightTextSecondary,
    error = LightAccentRed,
    onError = LightTextOnAccent
)

@Composable
fun KTSProjectRepTrackTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
