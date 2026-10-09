package com.example.recipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerBorder
import com.example.recipeapp.ui.theme.SimmerRadius
import com.example.recipeapp.ui.theme.SimmerType

enum class SimmerButtonVariant { Primary, Dark, Secondary, Cream, OnCream, Link }

private data class ButtonColors(val bg: Color, val fg: Color, val border: Color, val pressedBg: Color, val pressedBorder: Color)

/**
 * Simmer button. Pressed state deepens the fill one step (no hover, no lift).
 * 8dp radius, 2dp outline, 44dp minimum touch target. One primary action per view.
 */
@Composable
fun SimmerButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: SimmerButtonVariant = SimmerButtonVariant.Primary,
    enabled: Boolean = true,
    fullWidth: Boolean = false,
    iconLeft: ImageVector? = null,
    iconRight: ImageVector? = null,
) {
    val c = Simmer.colors
    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val click = Modifier.clickable(
        interactionSource = interaction,
        indication = null,
        enabled = enabled,
        role = Role.Button,
        onClick = onClick,
    )

    if (variant == SimmerButtonVariant.Link) {
        val color = if (enabled) c.textAccent else c.textDisabled
        Row(
            modifier.then(click).heightIn(min = SimmerBorder.minTouchTarget),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            iconLeft?.let { SimmerIcon(it, color) }
            Text(
                text, style = SimmerType.buttonMd, color = color,
                textDecoration = if (pressed) TextDecoration.Underline else TextDecoration.None,
            )
            iconRight?.let { SimmerIcon(it, color) }
        }
        return
    }

    val v = when (variant) {
        SimmerButtonVariant.Primary -> ButtonColors(c.accent, c.accentFg, c.accent, c.accentPressed, c.accentPressed)
        SimmerButtonVariant.Dark -> ButtonColors(c.surfaceInverse, c.textInverse, c.surfaceInverse, c.inversePressed, c.inversePressed)
        SimmerButtonVariant.Secondary -> ButtonColors(Color.Transparent, c.textPrimary, c.borderStrong, c.surfaceSubtle, c.borderStrong)
        SimmerButtonVariant.Cream -> ButtonColors(c.surfaceWarm, c.textOnWarm, c.borderWarm, c.surfaceWarmStrong, c.borderWarm)
        else -> ButtonColors(c.surfaceCard, c.textPrimary, c.borderWarm, c.surfaceWarm, c.borderWarm)
    }
    val bg = if (!enabled) c.disabledBg else if (pressed) v.pressedBg else v.bg
    val borderColor = if (!enabled) c.disabledBg else if (pressed) v.pressedBorder else v.border
    val fg = if (enabled) v.fg else c.disabledFg

    Row(
        modifier
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier)
            .clip(SimmerRadius.button)
            .background(bg)
            .border(SimmerBorder.width, borderColor, SimmerRadius.button)
            .then(click)
            .heightIn(min = SimmerBorder.minTouchTarget)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
    ) {
        iconLeft?.let { SimmerIcon(it, fg) }
        Text(text, style = SimmerType.buttonMd, color = fg)
        iconRight?.let { SimmerIcon(it, fg) }
    }
}
