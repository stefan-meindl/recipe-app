package com.example.recipeapp.ui.resolver

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Scales ingredient quantities by a ratio of requested servings to base servings.
 *
 * Handles fractional quantities (e.g. "1.5 cups", "200 g") and returns a properly formatted string.
 * Uses [BigDecimal] for precision to avoid floating-point drift.
 *
 * @param quantity The original quantity string (e.g. "200 g", "1.5", "2")
 * @param baseServings The recipe's base serving count (must be >= 1)
 * @param requestedServings The desired serving count (must be >= 1)
 * @return The scaled quantity string, or the original if unparseable
 */
object QuantityScaler {

    /**
     * Scale a quantity string by the ratio of requestedServings / baseServings.
     */
    fun scale(quantity: String, baseServings: Int, requestedServings: Int): String {
        require(baseServings >= 1) { "Base servings must be at least 1, was $baseServings" }
        require(requestedServings >= 1) { "Requested servings must be at least 1, was $requestedServings" }

        val trimmed = quantity.trim()
        val match = Regex("""^([\d.]+)\s*(.*)$""").find(trimmed)

        return if (match != null) {
            val numberStr = match.groupValues[1]
            val unit = match.groupValues[2].trim()

            val number = numberStr.toBigDecimalOrNull()
            if (number != null) {
                val ratio = BigDecimal.valueOf(requestedServings.toLong())
                    .divide(BigDecimal.valueOf(baseServings.toLong()), 10, RoundingMode.HALF_UP)
                val scaled = number * ratio

                val formatted = formatBigDecimal(scaled)
                if (unit.isNotEmpty()) "$formatted $unit" else formatted
            } else {
                quantity // return unchanged if not a valid number
            }
        } else {
            quantity // return unchanged if no numeric part found
        }
    }

    /**
     * Scale a numeric quantity (Double) by the ratio of requestedServings / baseServings.
     */
    fun scaleNumeric(quantity: Double, baseServings: Int, requestedServings: Int): Double {
        require(baseServings >= 1) { "Base servings must be at least 1, was $baseServings" }
        require(requestedServings >= 1) { "Requested servings must be at least 1, was $requestedServings" }

        val ratio = BigDecimal.valueOf(requestedServings.toLong())
            .divide(BigDecimal.valueOf(baseServings.toLong()), 10, RoundingMode.HALF_UP)
        val scaled = BigDecimal.valueOf(quantity) * ratio
        return scaled.toDouble()
    }

    private fun formatBigDecimal(value: BigDecimal): String {
        // Remove trailing zeros and unnecessary decimal point
        val stripped = value.stripTrailingZeros()
        return if (stripped.scale() <= 0) {
            stripped.toPlainString()
        } else {
            val plain = stripped.toPlainString()
            // Limit to reasonable decimal places
            if (plain.contains(".")) {
                val parts = plain.split(".")
                val decimals = parts[1]
                if (decimals.length > 10) {
                    // Round to 10 decimal places max
                    value.setScale(10, RoundingMode.HALF_UP).toPlainString()
                } else {
                    plain
                }
            } else {
                plain
            }
        }
    }
}
