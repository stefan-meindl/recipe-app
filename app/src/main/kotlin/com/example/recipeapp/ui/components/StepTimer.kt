package com.example.recipeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Pause
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerBorder
import com.example.recipeapp.ui.theme.SimmerRadius
import com.example.recipeapp.ui.theme.SimmerType
import kotlinx.coroutines.delay

enum class TimerStatus { Idle, Running, Paused, Done }

private fun pad(n: Int) = n.toString().padStart(2, '0')

/** m:ss, or h:mm:ss from an hour up. */
fun formatClock(totalSeconds: Int): String {
    val s = totalSeconds.coerceAtLeast(0)
    val h = s / 3600
    val m = s % 3600 / 60
    return if (h > 0) "$h:${pad(m)}:${pad(s % 60)}" else "$m:${pad(s % 60)}"
}

/** Label shown before the timer starts: "12 min", "1 h 30 min", or a clock for odd seconds. */
fun formatIdle(totalSeconds: Int): String {
    if (totalSeconds % 60 != 0) return formatClock(totalSeconds)
    val h = totalSeconds / 3600
    val m = totalSeconds % 3600 / 60
    return when {
        h == 0 -> "$m min"
        m == 0 -> "$h h"
        else -> "$h h $m min"
    }
}

/**
 * Orange tap-to-start timer. Idle -> Running -> Paused -> Running ... -> Done (tap to reset).
 * Filled orange while idle or running; outlined while paused; inverse fill when done.
 * State lives in memory only, so it resets if the composable leaves composition.
 */
@Composable
fun StepTimer(
    durationSeconds: Int,
    modifier: Modifier = Modifier,
    label: String? = null,
    onStart: () -> Unit = {},
    onComplete: () -> Unit = {},
) {
    val c = Simmer.colors
    var status by remember { mutableStateOf(TimerStatus.Idle) }
    var remainingMs by remember { mutableLongStateOf(durationSeconds * 1000L) }
    val completeCallback by rememberUpdatedState(onComplete)

    LaunchedEffect(status) {
        if (status != TimerStatus.Running) return@LaunchedEffect
        val end = System.currentTimeMillis() + remainingMs
        while (true) {
            val left = end - System.currentTimeMillis()
            if (left <= 0) {
                remainingMs = 0
                status = TimerStatus.Done
                completeCallback()
                break
            }
            remainingMs = left
            delay(250)
        }
    }

    fun reset() {
        status = TimerStatus.Idle
        remainingMs = durationSeconds * 1000L
    }

    val interaction = remember { MutableInteractionSource() }
    val pressed by interaction.collectIsPressedAsState()
    val idle = status == TimerStatus.Idle
    val running = status == TimerStatus.Running
    val paused = status == TimerStatus.Paused
    val done = status == TimerStatus.Done
    val filled = idle || running
    val remainingSec = kotlin.math.ceil(remainingMs / 1000.0).toInt()

    val bg = when {
        done -> c.surfaceInverse
        filled -> if (pressed) c.accentPressed else c.accent
        else -> Color.Transparent
    }
    val borderColor = when {
        done -> c.surfaceInverse
        filled && pressed -> c.accentPressed
        else -> c.accent
    }
    val fg = when {
        done -> c.textInverse
        filled -> c.accentFg
        else -> c.textAccent
    }
    val text = when {
        idle -> label ?: formatIdle(durationSeconds)
        done -> "Done"
        else -> formatClock(remainingSec)
    }
    val icon = when {
        idle -> Icons.Outlined.Timer
        running -> Icons.Outlined.Pause
        paused -> Icons.Outlined.PlayArrow
        else -> Icons.Outlined.Check
    }
    val description = when {
        idle -> "Start ${formatIdle(durationSeconds)} timer"
        running -> "Pause timer, ${formatClock(remainingSec)} left"
        paused -> "Resume timer, ${formatClock(remainingSec)} left"
        else -> "Timer finished, tap to reset"
    }
    val progress = if (durationSeconds > 0) (1f - remainingMs / (durationSeconds * 1000f)).coerceIn(0f, 1f) else 0f

    Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            Modifier
                .sizeIn(minWidth = 104.dp, minHeight = SimmerBorder.minTouchTarget)
                .clip(SimmerRadius.md)
                .background(bg)
                .border(SimmerBorder.width, borderColor, SimmerRadius.md)
                .semantics { contentDescription = description }
                .clickable(interactionSource = interaction, indication = null, role = Role.Button) {
                    when (status) {
                        TimerStatus.Idle -> { status = TimerStatus.Running; onStart() }
                        TimerStatus.Running -> status = TimerStatus.Paused
                        TimerStatus.Paused -> { status = TimerStatus.Running; onStart() }
                        TimerStatus.Done -> reset()
                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            Row(
                Modifier.padding(start = 14.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                SimmerIcon(icon, fg, size = 18.dp)
                Text(text, style = SimmerType.buttonMd.copy(fontSize = 15.sp, fontFeatureSettings = "tnum"), color = fg, maxLines = 1)
            }
            if (running || paused) {
                Box(
                    Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 12.dp, end = 12.dp, bottom = 5.dp)
                        .fillMaxWidth()
                        .height(3.dp)
                        .clip(SimmerRadius.full)
                        .background(if (running) Color.Black.copy(alpha = 0.22f) else c.borderSubtle),
                ) {
                    Box(
                        Modifier
                            .fillMaxWidth(progress)
                            .height(3.dp)
                            .clip(SimmerRadius.full)
                            .background(if (running) c.accentFg else c.accent),
                    )
                }
            }
        }
        if (paused || done) {
            Box(
                Modifier
                    .size(SimmerBorder.minTouchTarget)
                    .clip(SimmerRadius.md)
                    .border(SimmerBorder.width, c.borderDefault, SimmerRadius.md)
                    .semantics { contentDescription = "Reset timer" }
                    .clickable(role = Role.Button) { reset() },
                contentAlignment = Alignment.Center,
            ) {
                SimmerIcon(Icons.Outlined.Refresh, c.textSecondary, size = 18.dp)
            }
        }
    }
}
