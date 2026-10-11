package com.example.recipeapp.ui.resolver.model

import com.example.recipeapp.model.IngredientVariant
import com.example.recipeapp.model.StepV2

/**
 * Fully resolved recipe ready for display.
 */
data class ResolvedRecipe(
    val ingredients: List<ResolvedIngredient>,
    val steps: List<ResolvedStep>,
    val metadata: RecipeMetadata,
)

/**
 * An ingredient after resolution (filtered, scaled, alt-applied).
 */
data class ResolvedIngredient(
    val original: IngredientVariant,
    val quantity: Double,
    val variation: Boolean,
)

/**
 * A step after resolution (filtered, needs resolved to labels, topologically sorted).
 */
data class ResolvedStep(
    val original: StepV2,
    val resolvedNeeds: List<String>,
)

/**
 * Metadata about the resolved recipe.
 */
data class RecipeMetadata(
    val id: String,
    val title: String,
    val baseServings: Int,
    val requestedServings: Int,
)
