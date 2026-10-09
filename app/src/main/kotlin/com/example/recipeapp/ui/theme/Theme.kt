package com.example.recipeapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = Orange500,
    onPrimary = Black,
    primaryContainer = Glow300,
    onPrimaryContainer = Ink,
    secondary = Glow700,
    onSecondary = Black,
    secondaryContainer = Glow500,
    onSecondaryContainer = Ink,
    tertiary = Apricot,
    onTertiary = Ink,
    background = Canvas,
    onBackground = Ink,
    surface = Canvas,
    onSurface = Ink,
    surfaceVariant = CreamLight,
    onSurfaceVariant = InkTint,
    surfaceContainer = CreamDeeper,
    surfaceContainerHigh = Glow300,
    surfaceContainerHighest = Glow500,
    error = Orange600,
    onError = Black,
    outline = HairlineStrong,
    outlineVariant = Hairline
)

private val DarkColors = darkColorScheme(
    primary = Orange500,
    onPrimary = Black,
    primaryContainer = DarkGlow700,
    onPrimaryContainer = DarkCream,
    secondary = Glow700,
    onSecondary = Black,
    secondaryContainer = DarkGlow500,
    onSecondaryContainer = DarkCream,
    tertiary = Apricot,
    onTertiary = Ink,
    background = DarkCanvas,
    onBackground = DarkInk,
    surface = DarkCanvas,
    onSurface = DarkInk,
    surfaceVariant = DarkCreamDeeper,
    onSurfaceVariant = DarkInkTint,
    surfaceContainer = DarkBeigeDeep,
    surfaceContainerHigh = DarkGlow700,
    surfaceContainerHighest = DarkGlow500,
    error = Orange600,
    onError = Black,
    outline = DarkHairlineStrong,
    outlineVariant = DarkHairline
)

@Composable
fun SimmerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalSimmer provides if (darkTheme) SimmerDark else SimmerLight) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = SimmerTypography,
            shapes = SimmerShapes,
            content = content
        )
    }
}
