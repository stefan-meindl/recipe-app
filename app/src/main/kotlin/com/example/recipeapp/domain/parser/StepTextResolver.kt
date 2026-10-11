package com.example.recipeapp.domain.parser

import com.example.recipeapp.model.Ingredient

/**
 * Sealed interface representing a parsed segment of a step body.
 */
sealed interface StepSegment

/** A plain text segment with no ingredient reference. */
data class PlainText(val text: String) : StepSegment

/** An ingredient reference extracted from step body text. */
data class IngredientRef(
    val id: String,
    val name: String,
    val amount: String,
    val tips: List<String>,
) : StepSegment

/**
 * Resolves a step body string into a list of [StepSegment]s.
 *
 * Text wrapped in curly braces `{...}` is treated as an ingredient reference.
 * The key is extracted by stripping leading numbers and common modifier words
 * (`cloves`, `slices`, `pieces`, `minced`, `chopped`, `diced`, `whole`),
 * then taking the last remaining word.
 *
 * Ingredients are matched by checking if the ingredient name contains the key (case-insensitive).
 * Matching uses longest-name-first ordering to prefer more specific matches.
 *
 * @param stepBody the step body text to parse
 * @param ingredients the list of ingredients to cross-reference
 * @return a list of [StepSegment]s
 */
fun resolve(stepBody: String, ingredients: List<Ingredient>): List<StepSegment> {
    val regex = Regex("\\{([^}]+)\\}")
    val segments = mutableListOf<StepSegment>()
    var lastIndex = 0

    for (match in regex.findAll(stepBody)) {
        // Add plain text before this match
        if (match.range.start > lastIndex) {
            segments.add(PlainText(stepBody.substring(lastIndex, match.range.start)))
        }

        val captured = match.groupValues[1]
        val key = extractKey(captured)

        val sortedIngredients = ingredients.sortedByDescending { it.name.length }
        val matched = sortedIngredients.firstOrNull { ing ->
            ing.name.contains(key, ignoreCase = true)
        }

        if (matched != null) {
            segments.add(
                IngredientRef(
                    id = matched.name,
                    name = matched.name,
                    amount = matched.quantity,
                    tips = matched.tips ?: emptyList(),
                )
            )
        } else {
            segments.add(PlainText(match.value))
        }

        lastIndex = match.range.endInclusive + 1
    }

    // Add remaining plain text after last match
    if (lastIndex < stepBody.length) {
        segments.add(PlainText(stepBody.substring(lastIndex)))
    }

    return segments
}

/**
 * Extracts the ingredient key from a captured group.
 * Strips leading numbers and common modifier words, then takes the last remaining word.
 */
private fun extractKey(captured: String): String {
    // Common modifier words to strip
    val modifierWords = setOf(
        "cloves", "slices", "pieces", "minced", "chopped", "diced", "whole",
        "cups", "tablespoons", "teaspoons", "tbsp", "tsp", "oz", "lbs", "kg",
        "grams", "g", "ml", "liters", "pinch", "pinches", "bunch", "bunches",
        "can", "cans", "package", "packages", "bag", "bags", "box", "boxes",
        "large", "medium", "small", "fresh", "dried", "frozen", "thawed",
    )

    // Split into words, remove leading numbers and modifier words
    val words = captured.lowercase().split(Regex("\\s+"))
    val filtered = words.dropWhile { it in modifierWords || it.all { c -> c.isDigit() || c == '.' } }

    // Take the last remaining word as the key
    return filtered.lastOrNull()?.trim() ?: captured.trim()
}
