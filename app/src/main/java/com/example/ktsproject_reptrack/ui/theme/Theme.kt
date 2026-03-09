package com.example.ktsproject_reptrack.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AppOrange,
    onPrimary = AppTextOnAccent,
    primaryContainer = AppOrange,
    onPrimaryContainer = AppTextPrimary,

    secondary = AppOrange.copy(alpha = 0.8f),
    onSecondary = AppTextOnAccent,
    secondaryContainer = AppOrange.copy(alpha = 0.2f),
    onSecondaryContainer = AppTextPrimary,

    background = AppBackground,
    onBackground = AppTextPrimary,

    surface = AppSurface,
    onSurface = AppTextPrimary,
    surfaceVariant = AppSurface,
    onSurfaceVariant = AppTextPrimary.copy(alpha = 0.7f),

    outline = AppTextPrimary.copy(alpha = 0.3f),
    error = AppError,
    onError = AppTextOnAccent
)

@Composable
fun KTSProjectRepTrackTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
