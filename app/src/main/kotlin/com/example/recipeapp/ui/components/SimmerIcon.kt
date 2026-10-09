package com.example.recipeapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Decorative outline icon at a fixed size (14-16 inline, 18-20 UI, 24 tiles). */
@Composable
fun SimmerIcon(
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
    size: Dp = 16.dp,
    contentDescription: String? = null,
) {
    Icon(icon, contentDescription, modifier.size(size), tint)
}
