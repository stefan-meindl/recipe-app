package com.example.recipeapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipeapp.ui.theme.Simmer

/** Format seconds as mm:ss (or h:mm:ss for hours). */
private fun formatClock(totalSeconds: Int): String {
    val s = totalSeconds.coerceAtLeast(0)
    val h = s / 3600
    val m = s % 3600 / 60
    return if (h > 0) "$h:${pad(m)}:${pad(s % 60)}" else "$m:${pad(s % 60)}"
}

private fun pad(n: Int) = n.toString().padStart(2, '0')

/**
 * Compact timer display: mm:ss with a circular progress ring.
 * Recomposes on state change.
 */
@Composable
fun StepTimerView(
    elapsedMs: Long,
    durationMs: Long,
    modifier: Modifier = Modifier,
) {
    val c = Simmer.colors
    val progress = if (durationMs > 0) elapsedMs.toFloat() / durationMs.toFloat() else 1f
    val clampedProgress = progress.coerceIn(0f, 1f)

    Box(
        modifier = modifier.size(72.dp),
        contentAlignment = Alignment.Center,
    ) {
        // Progress ring
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 4f
            val diameter = size.minDimension - strokeWidth
            val center = Offset(size.width / 2f, size.height / 2f)

            // Background ring
            drawArc(
                color = c.borderSubtle.copy(alpha = 0.3f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = Offset(
                    center.x - diameter / 2f,
                    center.y - diameter / 2f,
                ),
                size = Size(diameter, diameter),
                style = Stroke(strokeWidth),
            )

            // Progress arc
            if (clampedProgress > 0f) {
                drawArc(
                    color = c.accent,
                    startAngle = -90f,
                    sweepAngle = 360f * clampedProgress,
                    useCenter = false,
                    topLeft = Offset(
                        center.x - diameter / 2f,
                        center.y - diameter / 2f,
                    ),
                    size = Size(diameter, diameter),
                    style = Stroke(strokeWidth),
                )
            }
        }

        // Time text
        val totalSeconds = (elapsedMs / 1000).toInt()
        val remainingSeconds = ((durationMs - elapsedMs) / 1000).toInt().coerceAtLeast(0)
        val displaySeconds = if (clampedProgress >= 1f) 0 else remainingSeconds
        Text(
            text = formatClock(displaySeconds),
            fontSize = 16.sp,
            color = c.textPrimary,
        )
    }
}
