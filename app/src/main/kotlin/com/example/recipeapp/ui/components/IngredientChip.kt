package com.example.recipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.window.PopupPositionProvider
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerBorder
import com.example.recipeapp.ui.theme.SimmerRadius
import com.example.recipeapp.ui.theme.SimmerSpace
import com.example.recipeapp.ui.theme.SimmerType

/** Places the popover under the anchor, clamped to the window, flipping above when it would overflow. */
private object BelowAnchor : PopupPositionProvider {
    override fun calculatePosition(anchorBounds: IntRect, windowSize: IntSize, layoutDirection: LayoutDirection, popupContentSize: IntSize): IntOffset {
        val margin = 24
        val gap = 20
        val x = (anchorBounds.center.x - popupContentSize.width / 2)
            .coerceIn(margin, (windowSize.width - popupContentSize.width - margin).coerceAtLeast(margin))
        val below = anchorBounds.bottom + gap
        val y = if (below + popupContentSize.height > windowSize.height - margin &&
            anchorBounds.top - gap - popupContentSize.height > margin
        ) anchorBounds.top - gap - popupContentSize.height else below
        return IntOffset(x, y)
    }
}

/**
 * Tappable ingredient mention: medium weight, 2dp orange underline. Tap opens a popover with
 * Amount (large serif figure plus note) and, when [tips] exist, a Tips tab.
 */
@Composable
fun IngredientChip(
    name: String,
    amount: String?,
    modifier: Modifier = Modifier,
    note: String? = null,
    tips: List<String> = emptyList(),
    label: String = name,
) {
    val c = Simmer.colors
    var open by remember { mutableStateOf(false) }
    var tab by remember { mutableStateOf("amount") }
    val hasTips = tips.isNotEmpty()

    Box(modifier) {
        Text(
            label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = c.textPrimary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clip(SimmerRadius.xs)
                .background(if (open) c.surfaceWarmStrong else androidx.compose.ui.graphics.Color.Transparent)
                .clickable(role = Role.Button) { open = !open }
                .padding(horizontal = 2.dp, vertical = 1.dp),
        )
        if (open) {
            Popup(popupPositionProvider = BelowAnchor, onDismissRequest = { open = false }, properties = PopupProperties(focusable = true)) {
                Column(
                    Modifier
                        .widthIn(max = 300.dp)
                        .clip(SimmerRadius.lg)
                        .background(c.surfaceCard)
                        .border(SimmerBorder.width, c.borderStrong, SimmerRadius.lg)
                        .semantics { contentDescription = name },
                ) {
                    Row(
                        Modifier.fillMaxWidth().padding(start = SimmerSpace.md, end = 6.dp, top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(name.replaceFirstChar { it.uppercase() }, style = MaterialTheme.typography.titleMedium, color = c.textPrimary, modifier = Modifier.weight(1f))
                        Box(
                            Modifier.size(40.dp).clip(SimmerRadius.md).clickable(role = Role.Button) { open = false }
                                .semantics { contentDescription = "Close" },
                            contentAlignment = Alignment.Center,
                        ) { SimmerIcon(Icons.Outlined.Close, c.textTertiary, size = 18.dp) }
                    }
                    if (hasTips) {
                        Box(Modifier.padding(horizontal = SimmerSpace.xs)) {
                            SegmentedTabs(
                                items = listOf(SimmerTabItem("amount", "Amount"), SimmerTabItem("tips", "Tips")),
                                value = tab, onChange = { tab = it }, fullWidth = true,
                            )
                        }
                    }
                    Column(
                        Modifier.padding(
                            start = SimmerSpace.md, end = SimmerSpace.md, bottom = SimmerSpace.md,
                            top = if (hasTips) SimmerSpace.md else SimmerSpace.xxs,
                        ),
                        verticalArrangement = Arrangement.spacedBy(SimmerSpace.sm),
                    ) {
                        if (tab == "amount" || !hasTips) {
                            Text(amount ?: "To taste", style = SimmerType.statDisplay.copy(fontSize = 40.sp, lineHeight = 44.sp), color = c.textPrimary)
                            note?.let { Text(it, style = MaterialTheme.typography.bodyMedium, color = c.textTertiary) }
                        } else {
                            tips.forEach { tip ->
                                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                                    SimmerIcon(Icons.Outlined.Lightbulb, c.textAccent, size = 18.dp)
                                    Text(tip, style = MaterialTheme.typography.bodyMedium, color = c.textSecondary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
