package com.example.recipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.Step
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerBorder
import com.example.recipeapp.ui.theme.SimmerRadius
import com.example.recipeapp.ui.theme.SimmerSpace
import com.example.recipeapp.ui.theme.SimmerType

/**
 * Full cooking step view: header, timer, clickable refs, and action buttons.
 *
 * @param step the step data
 * @param ingredients ingredients for ref parsing
 * @param viewModel the step's ViewModel (provides state + actions)
 * @param modifier composable modifier
 * @param onRefClick callback when a tappable ref is tapped
 */
@Composable
fun CookingStepView(
    step: Step,
    ingredients: List<Ingredient>,
    viewModel: CookingStepViewModel,
    modifier: Modifier = Modifier,
    onRefClick: (String) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Wire ref click callback
    viewModel.onRefClick = onRefClick

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(SimmerRadius.card)
            .background(Simmer.colors.surfaceCard)
            .border(SimmerBorder.width, Simmer.colors.borderSubtle, SimmerRadius.card)
            .padding(SimmerSpace.lg),
        verticalArrangement = Arrangement.spacedBy(SimmerSpace.sm),
    ) {
        // Header row
        HeaderRow(
            headerText = uiState.headerText,
            isDone = uiState.isDone,
        )

        // Timer + instruction row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SimmerSpace.md),
            verticalAlignment = Alignment.Top,
        ) {
            // Timer view
            StepTimerView(
                elapsedMs = uiState.elapsedMs,
                durationMs = uiState.durationMs,
                modifier = Modifier.width(80.dp),
            )

            // Instruction text with clickable refs
            InstructionText(
                instructionText = uiState.instructionText,
                refs = uiState.refs,
                onRefClickAction = onRefClick,
                modifier = Modifier.weight(1f),
            )
        }

        // Action row: Start + Done buttons
        ActionRow(
            isDone = uiState.isDone,
            isTimerRunning = uiState.isTimerRunning,
            onStartTimer = { viewModel.startTimer() },
            onToggleDone = { viewModel.toggleDone(!uiState.isDone) },
        )
    }
}

@Composable
private fun HeaderRow(
    headerText: String,
    isDone: Boolean,
    modifier: Modifier = Modifier,
) {
    val c = Simmer.colors
    Text(
        text = headerText,
        style = SimmerType.microUppercase,
        color = if (isDone) c.textMuted else c.textAccent,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun InstructionText(
    instructionText: String,
    refs: List<TextRef>,
    onRefClickAction: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val annotatedString = buildAnnotatedString {
        append(instructionText)
        for (ref in refs.sortedBy { it.start }) {
            addStyle(
                style = SpanStyle(
                    color = Simmer.colors.accent,
                    textDecoration = TextDecoration.Underline,
                ),
                start = ref.start,
                end = ref.end,
            )
            addStringAnnotation(
                tag = "REF",
                annotation = ref.id,
                start = ref.start,
                end = ref.end,
            )
        }
    }

    ClickableText(
        text = annotatedString,
        style = MaterialTheme.typography.bodyLarge.copy(color = Simmer.colors.textPrimary),
        modifier = modifier,
        onClick = { offset ->
            annotatedString.getStringAnnotations("REF", offset, offset)
                .firstOrNull()
                ?.let { onRefClickAction(it.item) }
        },
    )
}

@Composable
private fun ActionRow(
    isDone: Boolean,
    isTimerRunning: Boolean,
    onStartTimer: () -> Unit,
    onToggleDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(SimmerSpace.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Start button (visible when not done and timer not running)
        if (!isDone && !isTimerRunning) {
            SimmerButton(
                text = "Start Timer",
                onClick = onStartTimer,
                variant = SimmerButtonVariant.Primary,
                iconLeft = Icons.Outlined.PlayArrow,
                modifier = Modifier.weight(1f),
            )
        }

        // Done button (toggles done state)
        SimmerButton(
            text = if (isDone) "Undo Done" else "Mark Done",
            onClick = onToggleDone,
            variant = if (isDone) SimmerButtonVariant.Secondary else SimmerButtonVariant.Primary,
            iconLeft = Icons.Outlined.Check,
            modifier = Modifier.weight(1f),
        )
    }
}
