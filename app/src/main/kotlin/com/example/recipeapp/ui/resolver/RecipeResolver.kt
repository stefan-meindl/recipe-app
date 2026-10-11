package com.example.recipeapp.ui.resolver

import com.example.recipeapp.model.IngredientVariant
import com.example.recipeapp.model.RecipeV2
import com.example.recipeapp.model.StepV2
import com.example.recipeapp.model.StepPhase
import com.example.recipeapp.ui.resolver.model.ResolvedIngredient
import com.example.recipeapp.ui.resolver.model.ResolvedRecipe
import com.example.recipeapp.ui.resolver.model.ResolvedStep
import com.example.recipeapp.ui.resolver.model.RecipeMetadata

/**
 * Resolves a [RecipeV2] into a display-ready form.
 *
 * Resolution pipeline:
 * 1. **validateStatic()** — check servings, ingredient/step IDs, cycle detection
 * 2. **scaleFactor** — compute servings ratio
 * 3. **Ingredients** — filter by condition, apply alt overrides, scale quantities
 * 4. **Steps** — filter by condition, dynamic prerequisite check, label resolution, topological sort
 * 5. **Return** `Result<ResolvedRecipe>`
 *
 * Strictly stateless; no Android framework coupling.
 */
object RecipeResolver {

    /**
     * Resolve a recipe for display.
     *
     * @param recipe The recipe to resolve
     * @param servings Desired serving count (must be >= 1)
     * @param context Variation selections (e.g. "diet" → "vegan") used for condition evaluation
     * @return Result.success with ResolvedRecipe, or Result.failure with structured error
     */
    fun resolve(
        recipe: RecipeV2,
        servings: Int,
        context: Map<String, Any?> = emptyMap(),
    ): Result<ResolvedRecipe> {
        // Step 1: Static validation
        val validationErrors = RecipeValidator.validateStatic(recipe, servings)
        if (validationErrors.isNotEmpty()) {
            val errorMsg = validationErrors.joinToString("; ") { it.message }
            return Result.failure(IllegalStateException(errorMsg))
        }

        // Step 2: Compute scale factor
        val baseServings = recipe.servings.coerceAtLeast(1)
        val scaleFactor = servings.toDouble() / baseServings

        // Step 3: Resolve ingredients
        val resolvedIngredients = resolveIngredients(recipe.ingredients, scaleFactor, context)

        // Step 4: Resolve steps (may return failure for dynamic prerequisite issues)
        val resolvedStepsResult = resolveSteps(recipe.steps, context)
        if (resolvedStepsResult.isFailure) {
            return Result.failure(resolvedStepsResult.exceptionOrNull()!!)
        }

        // Step 5: Build result
        val metadata = RecipeMetadata(
            id = recipe.id,
            title = recipe.title,
            baseServings = baseServings,
            requestedServings = servings,
        )

        val resolvedRecipe = ResolvedRecipe(
            ingredients = resolvedIngredients,
            steps = resolvedStepsResult.getOrThrow(),
            metadata = metadata,
        )

        return Result.success(resolvedRecipe)
    }

    /**
     * Resolve ingredients: filter by condition, apply alt overrides, scale quantities.
     */
    private fun resolveIngredients(
        ingredients: List<IngredientVariant>,
        scaleFactor: Double,
        context: Map<String, Any?>,
    ): List<ResolvedIngredient> {
        return buildList {
            for (ing in ingredients) {
                // Filter by condition
                val conditionResult = ConditionEvaluator.evaluate(ing.condition, context)
                if (conditionResult is ConditionResult.Fail) {
                    continue // Skip ingredient whose condition doesn't match
                }
                // Invalid conditions are treated as pass (per plan: never throw)

                var quantity = ing.qty ?: 0.0

                // Apply alt overrides — full replacement when condition matches
                for (alt in ing.alt) {
                    val altConditionResult = ConditionEvaluator.evaluate(alt.condition, context)
                    if (altConditionResult is ConditionResult.Pass) {
                        // Full replacement: alt completely swaps the original ingredient
                        // Use alt's value as the new quantity
                        quantity = alt.value
                    }
                }

                // Scale quantity
                quantity = (quantity * scaleFactor).let {
                    // Round to 2 decimal places
                    Math.round(it * 100.0) / 100.0
                }

                // Variation is true if the ingredient has a condition
                val variation = ing.condition != null

                add(ResolvedIngredient(
                    original = ing,
                    quantity = quantity,
                    variation = variation,
                ))
            }
        }
    }

    /**
     * Resolve steps: filter by condition, check prerequisites, resolve labels, topological sort.
     * Returns Result.failure on dynamic prerequisite failure.
     */
    private fun resolveSteps(
        steps: List<StepV2>,
        context: Map<String, Any?>,
    ): Result<List<ResolvedStep>> {
        // Filter steps by condition
        val filteredSteps = buildList {
            for (step in steps) {
                val conditionResult = ConditionEvaluator.evaluate(step.condition, context)
                if (conditionResult is ConditionResult.Fail) {
                    continue // Skip step whose condition doesn't match
                }
                add(step)
            }
        }

        if (filteredSteps.isEmpty()) return Result.success(emptyList())

        // Build a map of step ID → step for quick lookup
        val stepMap = filteredSteps.associateBy { it.id }
        val stepIds = stepMap.keys.toSet()

        // Dynamic prerequisite check: verify every needs[] ID exists in the filtered set
        for (step in filteredSteps) {
            for (needId in step.needs) {
                if (needId !in stepIds) {
                    val errorMsg = "MissingPrerequisite: step '${step.id}' depends on '$needId' which is not present after filtering"
                    return Result.failure(IllegalStateException(errorMsg))
                }
            }
        }

        // Topological sort respecting needs[] IDs
        val sortedSteps = topologicalSort(filteredSteps)

        // Resolve labels: map each needs[] ID to its corresponding step label
        // Build a map of step ID → label
        val idToLabel = stepMap.mapValues { (_, step) -> step.title }

        // Create ResolvedStep with resolved labels
        val resolvedSteps = sortedSteps.map { step ->
            val resolvedNeeds = step.needs.mapNotNull { needId ->
                idToLabel[needId]
            }
            ResolvedStep(
                original = step,
                resolvedNeeds = resolvedNeeds,
            )
        }

        // Partition into prep and cook based on phase
        val prepSteps = resolvedSteps.filter { it.original.phase == StepPhase.prep }
        val cookSteps = resolvedSteps.filter { it.original.phase == StepPhase.cook }

        return Result.success(prepSteps + cookSteps)
    }

    /**
     * Topologically sort steps by their needs[] dependencies using Kahn's algorithm.
     *
     * @param steps The filtered steps to sort
     * @return The topologically sorted steps
     */
    private fun topologicalSort(steps: List<StepV2>): List<StepV2> {
        if (steps.isEmpty()) return emptyList()

        // Build adjacency list and in-degree count
        val stepIds = steps.map { it.id }.toSet()
        val dependents = mutableMapOf<String, MutableSet<String>>()
        val inDegree = mutableMapOf<String, Int>()

        for (step in steps) {
            dependents[step.id] = mutableSetOf()
            inDegree[step.id] = 0
        }

        for (step in steps) {
            for (needId in step.needs) {
                if (needId in stepIds) {
                    dependents.getOrPut(needId) { mutableSetOf() }.add(step.id)
                    inDegree[step.id] = inDegree.getValue(step.id) + 1
                }
            }
        }

        // Kahn's algorithm with deterministic ordering
        val queue = steps.filter { inDegree.getValue(it.id) == 0 }.map { it.id }.toMutableList()
        queue.sort()
        val sorted = mutableListOf<String>()

        while (queue.isNotEmpty()) {
            val node = queue.removeAt(0)
            sorted.add(node)

            for (dependent in dependents[node] ?: emptySet()) {
                inDegree[dependent] = inDegree.getValue(dependent) - 1
                if (inDegree.getValue(dependent) == 0) {
                    queue.add(dependent)
                    queue.sort()
                }
            }
        }

        // If cycle detected (shouldn't happen after validation, but handle gracefully)
        if (sorted.size < steps.size) {
            val cyclicSteps = steps.filter { it.id !in sorted }
            return steps.filter { it.id in sorted } + cyclicSteps
        }

        return steps.sortedBy { sorted.indexOf(it.id) }
    }
}
