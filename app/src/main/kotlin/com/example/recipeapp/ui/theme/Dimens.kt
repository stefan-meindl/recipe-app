package com.example.recipeapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/** Spacing scale from tokens/spacing.css (4px base, 8px rhythm). */
object SimmerSpace {
    val xxs = 4.dp
    val xs = 8.dp
    val sm = 12.dp
    val md = 16.dp
    val lg = 20.dp
    val xl = 24.dp
    val xxl = 32.dp
    val xxxl = 40.dp
}

/** Radius scale from tokens/shape.css. Buttons are never pills. */
object SimmerRadius {
    val xs = RoundedCornerShape(4.dp)
    val sm = RoundedCornerShape(6.dp)
    val md = RoundedCornerShape(8.dp)
    val lg = RoundedCornerShape(12.dp)
    val xl = RoundedCornerShape(16.dp)
    val xxl = RoundedCornerShape(20.dp)
    val full = RoundedCornerShape(percent = 50)
    val button = md
    val input = md
    val card = lg
}

/** Thick, rounded outlines: 2dp everywhere, 3dp for emphasis. */
object SimmerBorder {
    val width = 2.dp
    val strong = 3.dp
    val focusRing = 3.dp
    val focusOffset = 2.dp
    val minTouchTarget = 44.dp
    val minListRow = 56.dp
}
