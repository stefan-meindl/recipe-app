package com.example.recipeapp.ui.resolver

import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.PreparationMethod
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.Step

/**
 * Pure, framework-free recipe resolver.
 *
 * Given a [Recipe] and a selection request ([selection]), it returns the exact
 * ingredients and steps to display for that selection, with ingredient quantities
 * scaled to the requested servings. Being pure (no Android, no DI, no state) makes
 * it trivially unit-testable and reusable from the ViewModel or a future deep-link.
 */
object RecipeResolver {

    /** A request to resolve a specific recipe to a specific preparation method. */
    data class Selection(
        val recipeId: String,
        val methodId: String? = null,
        val servings: Int? = null,
    )

    /** The resolved, ready-to-display output for a selection. */
    data class Resolution(
        val recipe: Recipe,
        val method: PreparationMethod,
        val servings: Int,
        val multiplier: Double,
        val ingredients: List<Ingredient>,
        val steps: List<Step>,
    )

    /**
     * Resolve [recipe] to the requested [selection].
     *
     * @throws IllegalArgumentException if the recipeId is unknown, the requested
     *   methodId does not exist on the recipe, or servings would be below 1.
     */
    fun resolve(recipe: Recipe, selection: Selection): Resolution {
        require(recipe.id == selection.recipeId) {
            "Selection recipeId '${selection.recipeId}' does not match recipe id '${recipe.id}'"
        }

        val baseServings = recipe.servings.coerceAtLeast(1)
        val requested = selection.servings ?: baseServings
        val servings = requested
        require(servings >= 1) { "Servings must be at least 1, was $servings" }

        val method: PreparationMethod = if (selection.methodId != null) {
            recipe.preparationMethods.firstOrNull { it.id == selection.methodId }
                ?: throw IllegalArgumentException(
                    "No preparation method found for id '${selection.methodId}' on recipe '${recipe.id}'"
                )
        } else {
            recipe.preparationMethods.firstOrNull()
                ?: throw IllegalArgumentException(
                    "Recipe '${recipe.id}' has no preparation methods"
                )
        }

        val multiplier = servings.toDouble() / baseServings.toDouble()

        val ingredients = method.ingredients.map { it.scaledBy(multiplier) }
        val steps = method.steps

        return Resolution(
            recipe = recipe,
            method = method,
            servings = servings,
            multiplier = multiplier,
            ingredients = ingredients,
            steps = steps,
        )
    }

    /**
     * Convenience overload that resolves a recipe directly.
     * @param methodId optional; defaults to the recipe's first method.
     * @param servings optional; defaults to the recipe's default servings.
     */
    fun resolve(
        recipe: Recipe,
        methodId: String? = null,
        servings: Int? = null,
    ): Resolution = resolve(recipe, Selection(recipe.id, methodId, servings))

    /**
     * Find a recipe by id in a collection.
     * @return the matching recipe, or null if none match.
     */
    fun findRecipe(recipes: List<Recipe>, recipeId: String): Recipe? =
        recipes.firstOrNull { it.id == recipeId }
}
