package com.example.recipeapp.ui.resolver

import com.example.recipeapp.model.IngredientVariant
import com.example.recipeapp.model.Method
import com.example.recipeapp.model.RecipeV2
import com.example.recipeapp.model.StepV2
import java.math.BigDecimal
import java.math.MathContext

/**
 * Result of a recipe resolution attempt.
 *
 * @param resolved The fully resolved recipe, or null if resolution failed completely.
 * @param requestedServings The serving count that was requested (may differ from [resolved].servings).
 * @param warnings Non-fatal issues (e.g. unparseable conditions, unknown fields).
 * @param errors Fatal issues that prevent a valid resolution.
 */
data class ResolutionResult(
    val resolved: RecipeV2?,
    val requestedServings: Int? = null,
    val warnings: List<String> = emptyList(),
    val errors: List<String> = emptyList(),
) {
    val isValid: Boolean
        get() = errors.isEmpty() && resolved != null

    /** Returns the requested servings if provided, otherwise the resolved recipe's base servings. */
    val servings: Int
        get() = requestedServings ?: resolved?.servings ?: 0

    val method: Method?
        get() = resolved?.methods?.firstOrNull()

    val resolvedIngredients: List<IngredientVariant>
        get() = resolved?.ingredients ?: emptyList()

    val prepSteps: List<StepV2>
        get() = resolved?.steps?.filter { it.phase.name == "prep" } ?: emptyList()

    val cookSteps: List<StepV2>
        get() = resolved?.steps?.filter { it.phase.name == "cook" } ?: emptyList()

    companion object {
        fun success(recipe: RecipeV2, requestedServings: Int? = null): ResolutionResult =
            ResolutionResult(resolved = recipe, requestedServings = requestedServings)

        fun failure(errors: List<String>, requestedServings: Int? = null): ResolutionResult =
            ResolutionResult(resolved = null, requestedServings = requestedServings, errors = errors)

        fun partial(recipe: RecipeV2, warnings: List<String>, requestedServings: Int? = null): ResolutionResult =
            ResolutionResult(resolved = recipe, requestedServings = requestedServings, warnings = warnings)
    }
}
