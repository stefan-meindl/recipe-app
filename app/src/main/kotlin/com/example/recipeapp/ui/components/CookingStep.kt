package com.example.recipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerBorder
import com.example.recipeapp.ui.theme.SimmerRadius
import com.example.recipeapp.ui.theme.SimmerSpace
import com.example.recipeapp.ui.theme.SimmerType

/**
 * One cooking step: "STEP n" eyebrow, title, optional tap-to-start timer, body copy.
 * The card variant gets a 2dp subtle outline (3dp orange when [active]); the flat variant is a divider row.
 */
@Composable
fun CookingStep(
    title: String,
    modifier: Modifier = Modifier,
    stepLabel: String? = null,
    durationSeconds: Int = 0,
    stepId: String = "",
    timerLabel: String? = null,
    card: Boolean = true,
    active: Boolean = false,
    onTimerStart: () -> Unit = {},
    onTimerComplete: (String) -> Unit = {},
    body: @Composable ColumnScope.() -> Unit = {},
) {
    val c = Simmer.colors
    val shell = if (card) {
        Modifier
            .clip(SimmerRadius.card)
            .background(c.surfaceCard)
            .border(
                if (active) SimmerBorder.strong else SimmerBorder.width,
                if (active) c.borderAccent else c.borderSubtle,
                SimmerRadius.card,
            )
            .padding(SimmerSpace.lg)
    } else {
        Modifier.padding(vertical = SimmerSpace.lg)
    }
    Column(modifier.fillMaxWidth().then(shell), verticalArrangement = Arrangement.spacedBy(SimmerSpace.sm)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SimmerSpace.md),
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                if (stepLabel != null) {
                    Text(
                        stepLabel,
                        style = SimmerType.microUppercase,
                        color = if (active) c.textAccent else c.textTertiary,
                    )
                }
                if (title.isNotBlank()) {
                    Text(title, style = MaterialTheme.typography.titleLarge, color = c.textPrimary)
                }
            }
            if (durationSeconds > 0) {
                StepTimer(
                    durationSeconds = durationSeconds,
                    stepId = stepId,
                    label = timerLabel,
                    onStart = onTimerStart,
                    onComplete = onTimerComplete,
                )
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(SimmerSpace.xs), content = body)
    }
}
