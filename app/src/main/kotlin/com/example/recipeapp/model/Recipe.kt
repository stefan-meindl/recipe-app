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
 *         { "description": "Mix dry ingredients", "durationMinutes": 2, "tips": ["Sift together"] }
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
    val durationMinutes: Int? = null,
    val tips: List<String>? = null,
)
