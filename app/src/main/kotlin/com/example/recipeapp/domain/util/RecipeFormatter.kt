package com.example.recipeapp.domain.util

/**
 * Pure Kotlin formatting utilities for recipe quantities and times.
 * Zero Android/Context dependencies.
 */
object RecipeFormatter {

    /** Common unit abbreviations that should never be pluralized. */
    private val abbreviations = setOf(
        "g", "kg", "mg", "tsp", "tbsp", "oz", "lb", "l", "ml"
    )

    /**
     * Format a quantity with unit, handling quarter-based fractions,
     * metric unit rollover (g→kg, ml→l), and deterministic pluralization.
     */
    fun fmtQty(quantity: Double, unit: String): String {
        // Round to nearest 0.25 to avoid floating-point drift
        val rounded = Math.round(quantity * 4.0) / 4.0

        // Unit rollover: g→kg, ml→l at >= 1000
        val rolloverMap = mapOf("g" to "kg", "ml" to "l")
        var finalQty = rounded
        var finalUnit = unit
        var hadRollover = false

        if (rounded >= 1000 && rolloverMap.containsKey(unit)) {
            finalUnit = rolloverMap[unit]!!
            finalQty = rounded / 1000.0
            hadRollover = true
        }

        // Separate integer and fractional parts
        val intPart = finalQty.toLong()
        val frac = finalQty - intPart

        // Fraction mapping with threshold-based rounding for float safety
        val fracPart = when {
            frac < 0.125 -> ""
            frac < 0.375 -> "¼"
            frac < 0.625 -> "½"
            frac < 0.875 -> "¾"
            else -> " 1" // round up to next integer
        }

        // Build the quantity string
        // Use space between int and frac only when unit rollover occurred
        val qtyStr = if (intPart == 0L && fracPart.isEmpty()) {
            "0"
        } else if (intPart == 0L) {
            fracPart
        } else if (fracPart.isEmpty()) {
            intPart.toString()
        } else {
            if (hadRollover) "$intPart $fracPart" else "$intPart$fracPart"
        }

        // Pluralize unit (skip for abbreviations)
        val pluralized = if (finalUnit in abbreviations) {
            finalUnit
        } else {
            pluralize(finalUnit, finalQty > 1.0)
        }

        return "$qtyStr $pluralized"
    }

    /**
     * Format total minutes into a human-readable time string.
     * Omits hours if 0, omits minutes if 0.
     */
    fun fmtTime(totalMinutes: Int): String {
        val hours = totalMinutes / 60
        val mins = totalMinutes % 60

        val parts = mutableListOf<String>()

        if (hours > 0) {
            parts.add(if (hours == 1) "1 h" else "$hours h")
        }

        if (mins > 0) {
            parts.add(if (mins == 1) "1 min" else "$mins min")
        }

        return if (parts.isEmpty()) {
            "0 min"
        } else {
            parts.joinToString(" ")
        }
    }

    /**
     * Deterministic English pluralization using suffix rules.
     */
    private fun pluralize(word: String, isPlural: Boolean): String {
        if (!isPlural) return word

        val lower = word.lowercase()
        return when {
            lower.endsWith("y") && lower.length > 1 && !isVowel(lower[lower.length - 2]) ->
                word.dropLast(1) + "ies"
            lower.endsWith("s") || lower.endsWith("sh") || lower.endsWith("ch") ||
                lower.endsWith("x") || lower.endsWith("z") ->
                word + "es"
            else ->
                word + "s"
        }
    }

    private fun isVowel(c: Char): Boolean =
        c in "aeiouAEIOU"
}
