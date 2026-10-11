package com.example.recipeapp.ui.recipe

import com.example.recipeapp.model.PreparationMethod
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.Step

/**
 * Sealed interface representing the three stages of the recipe flow.
 * Overview → Selection → Cooking
 */
sealed interface RecipeFlowStage {
    object Overview : RecipeFlowStage
    object Selection : RecipeFlowStage
    object Cooking : RecipeFlowStage
}

/**
 * Complete state for a recipe flow instance, scoped to a single recipe.
 * Preserved across navigation jumps (e.g. shopping list detour).
 */
data class RecipeFlowState(
    val stage: RecipeFlowStage = RecipeFlowStage.Overview,
    val recipe: Recipe,
    val servings: Int,
    val selectedVariant: PreparationMethod?,
    val selectedMethod: PreparationMethod?,
) {
    companion object {
        fun fromRecipe(recipe: Recipe): RecipeFlowState {
            return RecipeFlowState(
                stage = RecipeFlowStage.Overview,
                recipe = recipe,
                servings = recipe.servings,
                selectedVariant = recipe.preparationMethods.firstOrNull(),
                selectedMethod = recipe.preparationMethods.firstOrNull(),
            )
        }
    }

    /** The effective servings value used for scaling. */
    val effectiveServings: Int
        get() = servings.coerceIn(1, 20)

    /** The currently active preparation method. */
    val activeMethod: PreparationMethod?
        get() = selectedMethod ?: selectedVariant

    /** All available preparation methods (variants). */
    val variants: List<PreparationMethod>
        get() = recipe.preparationMethods

    /** Scaled ingredients for the active method and servings. */
    val scaledIngredients: List<com.example.recipeapp.model.Ingredient>
        get() {
            val method = activeMethod ?: return emptyList()
            val baseServings = recipe.servings
            val multiplier = effectiveServings.toDouble() / baseServings
            return method.ingredients.map { it.scaledBy(multiplier) }
        }

    /** All steps from the active method. */
    val steps: List<Step>
        get() = activeMethod?.steps ?: emptyList()

    /** Steps with phase "prep" or empty phase. */
    val prepSteps: List<Step>
        get() = steps.filter { it.phase.equals("prep", ignoreCase = true) }

    /** Steps with phase "cook" or non-empty phase that isn't "prep". */
    val cookSteps: List<Step>
        get() = steps.filter { it.phase.equals("cook", ignoreCase = true) }

    /** Total time in minutes. */
    val totalTimeMinutes: Int?
        get() {
            val prep = recipe.prepTimeMinutes ?: 0
            val cook = recipe.cookTimeMinutes ?: 0
            return if (prep > 0 || cook > 0) prep + cook else null
        }
}

/**
 * Data class for a single cooking step with optional warning.
 */
data class CookingStepData(
    val step: Step,
    val phaseHeading: String?,
    val stepLabel: String,
    val warning: String? = null,
)
