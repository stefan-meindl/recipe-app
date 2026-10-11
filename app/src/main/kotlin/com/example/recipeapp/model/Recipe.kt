package com.example.recipeapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A complete recipe with multiple preparation methods.
 *
 * JSON format:
 * {
 *   "id": "pancakes",
 *   "title": "Pancakes",
 *   "prepTimeMinutes": 10,
 *   "cookTimeMinutes": 15,
 *   "servings": 4,
 *   "tags": ["breakfast", "vegetarian"],
 *   "images": ["https://example.com/pancakes.jpg"],
 *   "notes": "Best served with maple syrup.",
 *   "preparationMethods": [
 *     {
 *       "id": "stove",
 *       "displayName": "Stovetop",
 *       "ingredients": [
 *         { "name": "Flour", "quantity": "200 g", "alternatives": ["gluten-free flour"], "tips": ["Sift for fluffier pancakes"] }
 *       ],
 *       "steps": [
 *         { "id": "mix", "description": "Mix dry ingredients", "durationMinutes": 2, "tips": ["Sift together"] }
 *       ]
 *     }
 *   ]
 * }
 */
@Serializable
data class Recipe(
    val id: String,
    val title: String,
    val prepTimeMinutes: Int? = null,
    val cookTimeMinutes: Int? = null,
    val servings: Int = 4,
    val tags: List<String> = emptyList(),
    val images: List<String> = emptyList(),
    val notes: String? = null,
    val preparationMethods: List<PreparationMethod>,
)

@Serializable
data class PreparationMethod(
    val id: String,
    val displayName: String,
    val ingredients: List<Ingredient>,
    val steps: List<Step>,
)

@Serializable
data class Ingredient(
    val name: String,
    val quantity: String,
    val alternatives: List<String>? = null,
    val tips: List<String>? = null,
) {
    /** Scale the quantity string by a multiplier (e.g. servings adjustment). */
    fun scaledBy(multiplier: Double): Ingredient {
        val scaledQuantity = scaleQuantity(quantity, multiplier)
        return copy(quantity = scaledQuantity)
    }
}

@Serializable
data class Step(
    val description: String,
    val id: String = "",
    val title: String = "",
    val phase: String = "",
    val durationMinutes: Int? = null,
    val tips: List<String>? = null,
)

/**
 * Scale a quantity string by a multiplier.
 * Handles formats like "200 g", "1.5 cups", "2", etc.
 */
fun scaleQuantity(quantity: String, multiplier: Double): String {
    val trimmed = quantity.trim()
    val match = Regex("""^([\d.]+)\s*(.*)$""").find(trimmed)
    return if (match != null) {
        val number = match.groupValues[1].toDoubleOrNull() ?: 1.0
        val unit = match.groupValues[2].trim()
        val scaled = (number * multiplier).let {
            if (it == it.toLong().toDouble()) it.toLong().toString()
            else {
                val formatted = String.format(java.util.Locale.US, "%.2f", it)
                val trimmed = formatted.trimEnd('0').trimEnd('.')
                if (trimmed.isEmpty() || trimmed == "-0") "0" else trimmed
            }
        }
        if (unit.isNotEmpty()) "$scaled $unit" else scaled
    } else {
        quantity // return unchanged if no numeric part found
    }
}
