package com.example.recipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerRadius
import com.example.recipeapp.ui.theme.SimmerType

enum class SimmerBadgeVariant { Primary, Cream, Dark }

/** Full-pill badge (the only place besides pill tabs where a full radius is allowed). */
@Composable
fun SimmerBadge(
    text: String,
    modifier: Modifier = Modifier,
    variant: SimmerBadgeVariant = SimmerBadgeVariant.Cream,
    icon: ImageVector? = null,
) {
    val c = Simmer.colors
    val (bg, fg) = when (variant) {
        SimmerBadgeVariant.Primary -> c.accent to c.accentFg
        SimmerBadgeVariant.Cream -> c.surfaceWarmStrong to c.textPrimary
        SimmerBadgeVariant.Dark -> c.surfaceInverse to c.textInverse
    }
    Row(
        modifier.clip(SimmerRadius.full).background(bg).padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        icon?.let { SimmerIcon(it, fg, size = 14.dp) }
        Text(text, style = SimmerType.captionBold, color = fg, maxLines = 1)
    }
}
