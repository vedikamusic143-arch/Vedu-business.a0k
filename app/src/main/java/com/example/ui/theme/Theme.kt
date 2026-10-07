package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val VeduColorScheme = darkColorScheme(
    primary = VeduCyan,
    onPrimary = VeduObsidian,
    primaryContainer = VeduSurfaceHighlight,
    onPrimaryContainer = VeduTextPrimary,
    secondary = VeduBlue,
    onSecondary = VeduTextPrimary,
    secondaryContainer = VeduSurfaceLight,
    onSecondaryContainer = VeduTextPrimary,
    tertiary = VeduEmerald,
    onTertiary = VeduObsidian,
    background = VeduObsidian,
    onBackground = VeduTextPrimary,
    surface = VeduMidnight,
    onSurface = VeduTextPrimary,
    surfaceVariant = VeduSurface,
    onSurfaceVariant = VeduTextSecondary,
    outline = VeduBorder,
    outlineVariant = VeduBorderSubtle,
    error = VeduCrimson,
    onError = VeduTextPrimary
)

@Composable
fun VeduTheme(
    darkTheme: Boolean = true, // VEDU defaults to the futuristic enterprise command center theme
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = VeduColorScheme,
        typography = Typography,
        content = content
    )
}
