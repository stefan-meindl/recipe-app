package com.example.recipeapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.recipeapp.R

// Simmer typography: Instrument Serif (display), Inter (UI prose), JetBrains Mono (code).
// Font files live in res/font. They were converted from the design system's woff2 files,
// because Android cannot load woff2. Inter and JetBrains Mono are variable fonts (wght axis).

val InstrumentSerif = FontFamily(
    Font(R.font.instrument_serif_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.instrument_serif_italic, FontWeight.Normal, FontStyle.Italic),
)

private fun inter(weight: FontWeight) = Font(
    resId = R.font.inter_variable,
    weight = weight,
    variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight)),
)

val Inter = FontFamily(
    inter(FontWeight.Normal),
    inter(FontWeight.Medium),
    inter(FontWeight.SemiBold),
)

private fun mono(weight: FontWeight) = Font(
    resId = R.font.jetbrains_mono_variable,
    weight = weight,
    variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight)),
)

val JetBrainsMono = FontFamily(
    mono(FontWeight.Normal),
    mono(FontWeight.Medium),
)

/** Simmer text styles with no Material 3 slot (typography.css: stat-display, micro-uppercase, button-md, code-md). */
object SimmerType {
    val statDisplay = TextStyle(
        fontFamily = InstrumentSerif, fontWeight = FontWeight.Normal,
        fontSize = 56.sp, lineHeight = 62.sp, letterSpacing = (-1).sp,
    )
    val microUppercase = TextStyle(
        fontFamily = Inter, fontWeight = FontWeight.SemiBold,
        fontSize = 11.sp, lineHeight = 15.sp, letterSpacing = 1.sp,
    )
    val buttonMd = TextStyle(
        fontFamily = Inter, fontWeight = FontWeight.Medium,
        fontSize = 14.sp, lineHeight = 18.sp,
    )
    val captionBold = TextStyle(
        fontFamily = Inter, fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp, lineHeight = 18.sp,
    )
    val codeMd = TextStyle(
        fontFamily = JetBrainsMono, fontWeight = FontWeight.Normal,
        fontSize = 14.sp, lineHeight = 21.sp,
    )
}

val SimmerTypography = Typography(
    displayLarge = TextStyle(fontFamily = InstrumentSerif, fontWeight = FontWeight.Normal, fontSize = 57.sp, lineHeight = 64.sp, letterSpacing = (-1.5).sp),
    displayMedium = TextStyle(fontFamily = InstrumentSerif, fontWeight = FontWeight.Normal, fontSize = 45.sp, lineHeight = 52.sp, letterSpacing = (-1).sp),
    displaySmall = TextStyle(fontFamily = InstrumentSerif, fontWeight = FontWeight.Normal, fontSize = 36.sp, lineHeight = 44.sp, letterSpacing = (-0.5).sp),
    headlineLarge = TextStyle(fontFamily = InstrumentSerif, fontWeight = FontWeight.Normal, fontSize = 32.sp, lineHeight = 40.sp, letterSpacing = (-0.5).sp),
    headlineMedium = TextStyle(fontFamily = InstrumentSerif, fontWeight = FontWeight.Normal, fontSize = 28.sp, lineHeight = 36.sp),
    headlineSmall = TextStyle(fontFamily = InstrumentSerif, fontWeight = FontWeight.Normal, fontSize = 24.sp, lineHeight = 32.sp),
    titleLarge = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Medium, fontSize = 22.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Medium, fontSize = 16.sp, lineHeight = 24.sp),
    titleSmall = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp),
    bodyLarge = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 21.sp),
    bodySmall = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Normal, fontSize = 13.sp, lineHeight = 18.sp),
    labelLarge = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 18.sp),
    labelMedium = TextStyle(fontFamily = Inter, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 17.sp),
    labelSmall = TextStyle(fontFamily = Inter, fontWeight = FontWeight.SemiBold, fontSize = 11.sp, lineHeight = 15.sp, letterSpacing = 1.sp),
)
