package com.example.recipeapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Semantic roles from the Simmer design system (tokens/colors.css), resolved per theme.
 * Components read these instead of raw palette values so they follow light and OLED dark.
 */
@Immutable
data class SimmerSemantic(
    val isDark: Boolean,
    // surfaces
    val surfacePage: Color,
    val surfaceSubtle: Color,
    val surfaceCard: Color,
    val surfaceWarm: Color,
    val surfaceWarmSoft: Color,
    val surfaceWarmStrong: Color,
    val surfaceInverse: Color,
    val surfaceCode: Color,
    // text
    val textPrimary: Color,
    val textSecondary: Color,
    val textTertiary: Color,
    val textMuted: Color,
    val textDisabled: Color,
    val textAccent: Color,
    val textOnAccent: Color,
    val textInverse: Color,
    val textOnWarm: Color,
    // borders
    val borderSubtle: Color,
    val borderDefault: Color,
    val borderStrong: Color,
    val borderWarm: Color,
    val borderAccent: Color,
    val borderInverse: Color,
    // accent and states
    val accent: Color,
    val accentPressed: Color,
    val accentFg: Color,
    val disabledBg: Color,
    val disabledFg: Color,
    val inversePressed: Color,
)

val SimmerLight = SimmerSemantic(
    isDark = false,
    surfacePage = Canvas, surfaceSubtle = Surface, surfaceCard = Canvas,
    surfaceWarm = Cream, surfaceWarmSoft = CreamLight, surfaceWarmStrong = CreamDeeper,
    surfaceInverse = Ink, surfaceCode = SurfaceCode,
    textPrimary = Ink, textSecondary = Slate, textTertiary = Steel, textMuted = Stone,
    textDisabled = Muted, textAccent = LinkLight, textOnAccent = Black,
    textInverse = Canvas, textOnWarm = Ink,
    borderSubtle = HairlineSoft, borderDefault = Hairline, borderStrong = HairlineStrong,
    borderWarm = BeigeDeep, borderAccent = Orange500, borderInverse = Ink,
    accent = Orange500, accentPressed = Orange600, accentFg = Black,
    disabledBg = Hairline, disabledFg = Muted, inversePressed = InkTint,
)

val SimmerDark = SimmerSemantic(
    isDark = true,
    surfacePage = DarkCanvas, surfaceSubtle = DarkSurface, surfaceCard = DarkCanvas,
    surfaceWarm = DarkCream, surfaceWarmSoft = DarkCreamLight, surfaceWarmStrong = DarkCreamDeeper,
    surfaceInverse = DarkInk, surfaceCode = DarkSurfaceCode,
    textPrimary = DarkInk, textSecondary = DarkSlate, textTertiary = DarkSteel, textMuted = DarkStone,
    textDisabled = DarkMuted, textAccent = DarkLink, textOnAccent = Black,
    textInverse = DarkCanvas, textOnWarm = DarkInk,
    borderSubtle = DarkHairlineSoft, borderDefault = DarkHairline, borderStrong = DarkHairlineStrong,
    borderWarm = DarkBeigeDeep, borderAccent = Orange500, borderInverse = DarkInk,
    accent = Orange500, accentPressed = Orange600, accentFg = Black,
    disabledBg = DarkHairline, disabledFg = DarkMuted, inversePressed = Color(0xFFD4D4D4),
)

val LocalSimmer = staticCompositionLocalOf { SimmerLight }

/** Access the active Simmer semantic colours: `Simmer.colors.surfaceWarm`. */
object Simmer {
    val colors: SimmerSemantic
        @Composable @ReadOnlyComposable get() = LocalSimmer.current
}
