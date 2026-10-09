package com.example.recipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerBorder
import com.example.recipeapp.ui.theme.SimmerRadius
import com.example.recipeapp.ui.theme.SimmerSpace

enum class SimmerCardVariant { Base, Feature, Cream, CreamSoft, FeatureProduct, Photographic }

/**
 * Flat card: 12dp radius, 2dp outline. Only FeatureProduct carries a shadow, and only in light theme.
 * No nested cards, no coloured left borders.
 */
@Composable
fun SimmerCard(
    modifier: Modifier = Modifier,
    variant: SimmerCardVariant = SimmerCardVariant.Base,
    padding: Dp? = null,
    borderColor: Color? = null,
    borderWidth: Dp = SimmerBorder.width,
    content: @Composable ColumnScope.() -> Unit,
) {
    val c = Simmer.colors
    val bg: Color
    val border: Color
    val defaultPad: Dp
    when (variant) {
        SimmerCardVariant.Base -> { bg = c.surfaceCard; border = c.borderSubtle; defaultPad = SimmerSpace.xl }
        SimmerCardVariant.Feature -> { bg = c.surfaceCard; border = c.borderSubtle; defaultPad = SimmerSpace.xxl }
        SimmerCardVariant.Cream -> { bg = c.surfaceWarm; border = c.borderWarm; defaultPad = SimmerSpace.xxl }
        SimmerCardVariant.CreamSoft -> { bg = c.surfaceWarmSoft; border = Color.Transparent; defaultPad = SimmerSpace.xxl }
        SimmerCardVariant.FeatureProduct -> { bg = c.surfaceCard; border = c.borderSubtle; defaultPad = SimmerSpace.xxl }
        SimmerCardVariant.Photographic -> { bg = c.surfaceCode; border = if (c.isDark) c.borderDefault else c.surfaceCode; defaultPad = 0.dp }
    }
    val lift = if (variant == SimmerCardVariant.FeatureProduct && !c.isDark) {
        Modifier.shadow(4.dp, SimmerRadius.card, clip = false)
    } else Modifier

    Column(
        modifier
            .then(lift)
            .clip(SimmerRadius.card)
            .background(bg)
            .border(borderWidth, borderColor ?: border, SimmerRadius.card)
            .padding(padding ?: defaultPad),
        content = content,
    )
}
