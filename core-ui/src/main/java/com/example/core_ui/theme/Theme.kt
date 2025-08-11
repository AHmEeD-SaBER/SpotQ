package com.example.core_ui.theme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Turquoise,
    onPrimary = White,
    primaryContainer = Turquoise.copy(alpha = 0.85f),
    onPrimaryContainer = Black,
    secondary = SlateGray,
    onSecondary = White,
    tertiary = ButtonLight,        // Button color for light theme
    onTertiary = White,           // Button text color for light theme
    background = BackgroundLight,
    onBackground = SlateGray,
    surface = BackgroundLight,
    onSurface = SlateGray
)

private val DarkColors = darkColorScheme(
    primary = TurquoiseDark,
    onPrimary = White,
    primaryContainer = Turquoise.copy(alpha = 0.7f),
    onPrimaryContainer = White,
    secondary = Turquoise,
    onSecondary = Black,
    tertiary = ButtonDark,        // Button color for dark theme
    onTertiary = White,           // Button text color for dark theme
    background = BackgroundDark,
    onBackground = White,
    surface = BackgroundDark,
    onSurface = White
)

@Composable
fun SpotQTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
