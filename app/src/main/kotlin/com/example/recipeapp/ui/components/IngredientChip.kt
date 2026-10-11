package com.example.recipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.recipeapp.domain.parser.IngredientRef
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerRadius
import kotlinx.coroutines.delay

/**
 * Tappable ingredient mention: medium weight, 2dp orange underline. Tap opens a popover with
 * Amount (large serif figure plus note) and, when [tips] exist, a Tips tab.
 */
@Composable
fun IngredientChip(
    ref: IngredientRef,
    onDismiss: () -> Unit = {},
) {
    var size by remember { mutableStateOf(IntSize.Zero) }
    var tapCount by remember { mutableStateOf(0) }

    LaunchedEffect(tapCount) {
        if (tapCount > 0) {
            delay(1500L)
            tapCount = 0
        }
    }

    Box {
        Text(
            ref.name,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = Simmer.colors.textPrimary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .clip(SimmerRadius.xs)
                .clickable { tapCount = if (tapCount < 2) tapCount + 1 else 0 }
                .onSizeChanged { size = it },
        )

        if (tapCount == 1 && size != IntSize.Zero) {
            Popup(
                onDismissRequest = {
                    tapCount = 0
                    onDismiss()
                },
                offset = IntOffset(size.width + 8, 0),
            ) {
                Box(
                    Modifier
                        .background(Color.White)
                        .padding(8.dp),
                ) {
                    Text(ref.amount)
                }
            }
        } else if (tapCount == 2 && size != IntSize.Zero) {
            Popup(
                onDismissRequest = {
                    tapCount = 0
                    onDismiss()
                },
                offset = IntOffset(0, size.height + 8),
            ) {
                Column(
                    Modifier
                        .background(Color.White)
                        .padding(8.dp),
                ) {
                    ref.tips.forEach { Text(it) }
                }
            }
        }
    }
}

/**
 * Backward-compatible overload preserving the original signature.
 * Constructs an [IngredientRef] internally and delegates to the new API.
 */
@Composable
fun IngredientChip(
    name: String,
    amount: String,
    modifier: String? = null,
    note: String? = null,
    tips: List<String> = emptyList(),
    label: String? = null,
    onDismiss: () -> Unit = {},
) {
    val ref = IngredientRef(
        id = name,
        name = name,
        amount = listOfNotNull(amount, modifier, note).filter { it.isNotBlank() }.joinToString(" "),
        tips = tips,
    )
    IngredientChip(ref, onDismiss)
}
