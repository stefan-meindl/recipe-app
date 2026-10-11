package com.example.recipeapp.data

import android.content.Context
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.PreparationMethod
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.RecipeV2
import com.example.recipeapp.model.RecipeV2Codec
import com.example.recipeapp.model.Step
import com.example.recipeapp.model.VariationKind
import com.example.recipeapp.ui.resolver.ConditionEvaluator
import com.example.recipeapp.ui.resolver.ConditionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** Reads recipe JSON files from assets/recipes/ using kotlinx.serialization. */
class JsonRecipeRepository(private val context: Context) : RecipeRepository {

    override suspend fun getRecipes(): List<Recipe> = withContext(Dispatchers.IO) {
        val fileNames = context.assets.list("recipes")?.toList() ?: emptyList()

        fileNames.mapNotNull { fileName ->
            try {
                val text = context.assets.open("recipes/$fileName").bufferedReader().readText()
                mapToRecipe(RecipeV2Codec.decodeFromString(text))
            } catch (e: Exception) {
                android.util.Log.e("JsonRecipeRepository", "Failed to parse $fileName", e)
                null
            }
        }
    }

    private fun mapToRecipe(v2: RecipeV2): Recipe {
        val defaultContext = buildDefaultContext(v2)
        return Recipe(
            id = v2.id,
            title = v2.title,
            prepTimeMinutes = null,
            cookTimeMinutes = v2.timeMinutes,
            servings = v2.servings,
            tags = v2.tags,
            images = emptyList(),
            notes = v2.summary,
            preparationMethods = buildPreparationMethods(v2, defaultContext),
        )
    }

    /**
     * Default context: swap variations use their declared default; method selections use the
     * first option; add-type variations are absent (not active) by default.
     */
    private fun buildDefaultContext(v2: RecipeV2): Map<String, Any?> {
        val ctx = mutableMapOf<String, Any?>()
        for (variation in v2.variations) {
            if (variation.kind == VariationKind.swap && variation.def != null) {
                ctx[variation.id] = variation.def
            }
        }
        for (method in v2.methods) {
            method.options.firstOrNull()?.let { ctx[method.id] = it.value }
        }
        return ctx
    }

    /**
     * For recipes with no methods: one PrepMethod named "Default".
     * For recipes with a primary method: one PrepMethod per option of the first method.
     */
    private fun buildPreparationMethods(v2: RecipeV2, defaultContext: Map<String, Any?>): List<PreparationMethod> {
        if (v2.methods.isEmpty()) {
            return listOf(buildPreparationMethod("default", "Default", v2, defaultContext))
        }
        val primary = v2.methods.first()
        return primary.options.map { option ->
            buildPreparationMethod(option.value, option.label, v2, defaultContext + mapOf(primary.id to option.value))
        }
    }

    private fun buildPreparationMethod(
        id: String,
        displayName: String,
        v2: RecipeV2,
        context: Map<String, Any?>,
    ): PreparationMethod {
        val ingredients = v2.ingredients.mapNotNull { ing ->
            if (ConditionEvaluator.evaluate(ing.condition, context) is ConditionResult.Fail) return@mapNotNull null

            val baseQty = ing.qty
            val finalQty = if (baseQty != null) {
                var qty = baseQty
                for (alt in ing.alt) {
                    if (ConditionEvaluator.evaluate(alt.condition, context) is ConditionResult.Pass) {
                        qty = alt.value
                    }
                }
                qty
            } else null

            Ingredient(
                name = ing.name,
                quantity = if (finalQty != null) formatQuantity(finalQty, ing.unit) else ing.note ?: "",
                tips = ing.tips,
            )
        }

        val steps = v2.steps.mapNotNull { step ->
            if (ConditionEvaluator.evaluate(step.condition, context) is ConditionResult.Fail) return@mapNotNull null
            Step(
                description = step.body,
                id = step.id,
                title = step.title,
                phase = step.phase.name,
                durationMinutes = if (step.durationSeconds > 0) maxOf(1, step.durationSeconds / 60) else null,
            )
        }

        return PreparationMethod(id = id, displayName = displayName, ingredients = ingredients, steps = steps)
    }

    private fun formatQuantity(qty: Double, unit: String?): String {
        val qtyStr = when {
            qty == qty.toLong().toDouble() -> qty.toLong().toString()
            else -> qty.toBigDecimal().stripTrailingZeros().toPlainString()
        }
        return if (unit.isNullOrBlank()) qtyStr else "$qtyStr $unit"
    }
}
