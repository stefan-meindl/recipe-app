package com.example.recipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerBorder
import com.example.recipeapp.ui.theme.SimmerRadius
import com.example.recipeapp.ui.theme.SimmerSpace

/** Checkable list row, at least 56dp tall. Checked rows are struck through and dimmed. */
@Composable
fun ShoppingListItem(
    name: String,
    checked: Boolean,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    amount: String? = null,
    note: String? = null,
    divider: Boolean = false,
) {
    val c = Simmer.colors
    val rule = c.borderSubtle
    Row(
        modifier
            .fillMaxWidth()
            .drawBehind {
                if (divider) {
                    val h = SimmerBorder.width.toPx()
                    drawLine(rule, Offset(0f, h / 2), Offset(size.width, h / 2), strokeWidth = h)
                }
            }
            .toggleable(value = checked, role = Role.Checkbox, onValueChange = onToggle)
            .heightIn(min = SimmerBorder.minListRow)
            .padding(horizontal = SimmerSpace.md, vertical = SimmerSpace.sm),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SimmerSpace.md),
    ) {
        Box(
            Modifier
                .size(24.dp)
                .clip(SimmerRadius.sm)
                .background(if (checked) c.accent else Color.Transparent)
                .border(SimmerBorder.width, if (checked) c.accent else c.borderStrong, SimmerRadius.sm),
            contentAlignment = Alignment.Center,
        ) {
            if (checked) SimmerIcon(Icons.Outlined.Check, c.accentFg, size = 16.dp)
        }
        Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                name,
                style = MaterialTheme.typography.bodyLarge,
                color = if (checked) c.textTertiary else c.textPrimary,
                textDecoration = if (checked) TextDecoration.LineThrough else TextDecoration.None,
            )
            note?.let { Text(it, style = MaterialTheme.typography.bodySmall, color = c.textTertiary) }
        }
        amount?.let { Text(it, style = MaterialTheme.typography.titleSmall, color = c.textTertiary, maxLines = 1) }
    }
}
