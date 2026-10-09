package com.example.recipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerBorder
import com.example.recipeapp.ui.theme.SimmerRadius
import com.example.recipeapp.ui.theme.SimmerSpace
import com.example.recipeapp.ui.theme.SimmerType

data class SimmerTabItem(val value: String, val label: String, val count: Int? = null)

/** Filter chips: inverse fill when active. Full pill radius is allowed here. */
@Composable
fun PillTabs(
    items: List<SimmerTabItem>,
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val c = Simmer.colors
    Row(
        modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(SimmerSpace.xs),
    ) {
        items.forEach { item ->
            val active = item.value == value
            val fg = if (active) c.textInverse else c.textTertiary
            Row(
                Modifier
                    .clip(SimmerRadius.full)
                    .background(if (active) c.surfaceInverse else c.surfaceCard)
                    .border(SimmerBorder.width, if (active) c.borderInverse else c.borderDefault, SimmerRadius.full)
                    .selectable(selected = active, role = Role.Tab, onClick = { onChange(item.value) })
                    .heightIn(min = SimmerBorder.minTouchTarget)
                    .padding(horizontal = SimmerSpace.md, vertical = SimmerSpace.xs),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(item.label, style = SimmerType.buttonMd, color = fg, maxLines = 1)
                item.count?.let { Text(it.toString(), style = MaterialTheme.typography.labelMedium, color = fg) }
            }
        }
    }
}

/** Underline tabs: a 3dp rounded orange indicator under the active label, 2dp rule below the row. */
@Composable
fun SegmentedTabs(
    items: List<SimmerTabItem>,
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    fullWidth: Boolean = false,
) {
    val c = Simmer.colors
    val rule = c.borderSubtle
    Row(
        modifier
            .drawBehind {
                val h = SimmerBorder.width.toPx()
                drawRect(rule, topLeft = androidx.compose.ui.geometry.Offset(0f, size.height - h), size = androidx.compose.ui.geometry.Size(size.width, h))
            }
            .then(if (fullWidth) Modifier.fillMaxWidth() else Modifier.horizontalScroll(rememberScrollState())),
        horizontalArrangement = Arrangement.spacedBy(SimmerSpace.xxs),
    ) {
        items.forEach { item ->
            val active = item.value == value
            SegmentedTab(item, active, fullWidth, onClick = { onChange(item.value) })
        }
    }
}

@Composable
private fun RowScope.SegmentedTab(item: SimmerTabItem, active: Boolean, fullWidth: Boolean, onClick: () -> Unit) {
    val c = Simmer.colors
    Column(
        Modifier
            .then(if (fullWidth) Modifier.weight(1f) else Modifier)
            .selectable(selected = active, role = Role.Tab, onClick = onClick)
            .heightIn(min = SimmerBorder.minTouchTarget),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(Modifier.padding(horizontal = SimmerSpace.md, vertical = SimmerSpace.sm)) {
            Text(item.label, style = SimmerType.buttonMd, color = if (active) c.textAccent else c.textTertiary, maxLines = 1)
        }
        Box(
            Modifier
                .padding(horizontal = SimmerSpace.md)
                .fillMaxWidth()
                .height(SimmerBorder.strong)
                .clip(SimmerRadius.full)
                .background(if (active) c.accent else Color.Transparent),
        )
    }
}
