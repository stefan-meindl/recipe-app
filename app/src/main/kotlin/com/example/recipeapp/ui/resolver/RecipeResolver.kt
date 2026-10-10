package com.example.recipeapp.ui.resolver

import com.example.recipeapp.model.AltCondition
import com.example.recipeapp.model.IngredientVariant
import com.example.recipeapp.model.RecipeV2
import com.example.recipeapp.model.StepV2
import com.example.recipeapp.model.StepPhase
import java.math.BigDecimal
import java.math.MathContext

/**
 * Resolves a [RecipeV2] into a display-ready form.
 *
 * Resolution pipeline:
 * 1. **validate()** — check servings, ingredient IDs, required fields
 * 2. **parseConditions()** — parse condition strings into [Condition] instances
 * 3. **scaleIngredients()** — scale quantities by servings ratio
 * 4. **applyAltOverrides()** — apply per-condition quantity overrides
 * 5. **filterSteps()** — remove steps whose conditions don't match
 * 6. **orderSteps()** — topological sort on `needs[]` edges, partition into prep/cook
 * 7. **buildResult()** — assemble [ResolutionResult] with warnings/errors
 *
 * All validation errors are collected into [ResolutionResult.errors] rather than throwing.
 * Unparseable conditions produce warnings rather than crashes.
 */
object RecipeResolver {

    /**
     * Resolve a recipe for display.
     *
     * @param recipe The recipe to resolve
     * @param servings Desired serving count (must be >= 1, otherwise error is collected)
     * @param context Variation selections (e.g. "diet" → "vegan") used for condition evaluation
     * @return A [ResolutionResult] containing the resolved recipe, warnings, and errors
     */
    fun resolve(
        recipe: RecipeV2,
        servings: Int = recipe.servings,
        context: Map<String, Any> = emptyMap(),
    ): ResolutionResult {
        val warnings = mutableListOf<String>()
        val errors = mutableListOf<String>()

        // Step 1: Validate
        val validationErrors = validate(recipe, servings)
        errors.addAll(validationErrors.map { it.message })

        // If validation fails fatally (e.g. no methods, invalid servings), return early with failure
        if (errors.any { it.contains("Servings must be at least 1") } || errors.any { it.contains("no methods") }) {
            return ResolutionResult.failure(errors, requestedServings = servings)
        }

        val baseServings = recipe.servings.coerceAtLeast(1)
        val effectiveServings = if (servings >= 1) servings else baseServings

        // Step 2: Parse conditions for ingredients and steps
        val parsedIngredientConditions = recipe.ingredients.map { ing ->
            ing.condition?.let { cond ->
                val parsed = ConditionParser.parse(cond)
                if (parsed is Condition.UnparseableCondition) {
                    warnings.add("Unparseable ingredient condition for '${ing.id}': '$cond'")
                }
                parsed
            }
        }

        val parsedStepConditions = recipe.steps.map { step ->
            step.condition?.let { cond ->
                val parsed = ConditionParser.parse(cond)
                if (parsed is Condition.UnparseableCondition) {
                    warnings.add("Unparseable step condition for '${step.id}': '$cond'")
                }
                parsed
            }
        }

        // Step 3-4: Scale ingredients and apply alt overrides
        val resolvedIngredients = buildList {
            for ((index, ing) in recipe.ingredients.withIndex()) {
                val condition = parsedIngredientConditions[index]
                if (condition != null && !condition.evaluate(context)) {
                    continue // Skip ingredient whose condition doesn't match
                }

                var scaledQty = ing.qty?.let { QuantityScaler.scaleNumeric(it, baseServings, effectiveServings) }
                var result = ing.copy(qty = scaledQty)

                // Apply alt overrides
                for (alt in ing.alt) {
                    val altCondition = ConditionParser.parse(alt.condition)
                    if (altCondition.evaluate(context)) {
                        val altValue = alt.value
                        result = result.copy(qty = QuantityScaler.scaleNumeric(altValue, baseServings, effectiveServings))
                    }
                }

                add(result)
            }
        }

        // Step 5: Filter steps by condition
        val filteredSteps = buildList {
            for ((index, step) in recipe.steps.withIndex()) {
                val condition = parsedStepConditions[index]
                if (condition != null && !condition.evaluate(context)) {
                    continue // Skip step whose condition doesn't match
                }
                add(step)
            }
        }

        // Step 6: Order steps (topological sort + partition)
        val orderedSteps = orderSteps(filteredSteps, warnings)

        // Step 7: Build result
        val resolvedRecipe = recipe.copy(
            ingredients = resolvedIngredients,
            steps = orderedSteps,
        )

        return if (errors.isEmpty()) {
            if (warnings.isNotEmpty()) {
                ResolutionResult.partial(resolvedRecipe, warnings, requestedServings = servings)
            } else {
                ResolutionResult.success(resolvedRecipe, requestedServings = servings)
            }
        } else {
            ResolutionResult(resolved = resolvedRecipe, requestedServings = servings, warnings = warnings, errors = errors)
        }
    }

    /**
     * Validate recipe inputs. Returns a list of validation errors.
     */
    private fun validate(recipe: RecipeV2, servings: Int): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()

        if (servings < 1) {
            errors.add(ValidationError("Servings must be at least 1, was $servings"))
        }

        val stepIds = recipe.steps.map { it.id }.toSet()
        val ingredientIds = recipe.ingredients.map { it.id }.toSet()

        // Check that all needs[] IDs exist as either step IDs or ingredient IDs
        for (step in recipe.steps) {
            for (neededId in step.needs) {
                if (neededId !in stepIds && neededId !in ingredientIds) {
                    errors.add(ValidationError("Step '${step.id}' references unknown ingredient ID '$neededId'"))
                }
            }
        }

        // Check required fields
        if (recipe.methods.isEmpty()) {
            errors.add(ValidationError("Recipe '${recipe.id}' has no methods"))
        }

        return errors
    }

    /**
     * Topologically sort steps by their `needs[]` dependencies using Kahn's algorithm.
     *
     * Steps are partitioned into prep and cook phases. Prep steps always come before cook steps.
     * Within each phase, steps are ordered by their dependencies.
     *
     * If a cycle is detected, a structured warning is added and the best-effort
     * sorted steps are returned.
     *
     * @param steps The filtered steps to order
     * @param warnings Mutable list to append cycle detection warnings to
     * @return The topologically sorted steps
     */
    private fun orderSteps(
        steps: List<StepV2>,
        warnings: MutableList<String>,
    ): List<StepV2> {
        if (steps.isEmpty()) return emptyList()

        // Build adjacency list: step → steps that depend on it
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
                // Need references an ingredient or unknown step — ignore for ordering
            }
        }

        // Kahn's algorithm — use a sorted list for deterministic ordering
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

        // Check for cycles
        if (sorted.size < steps.size) {
            val cyclicSteps = steps.filter { it.id !in sorted }.map { it.id }
            warnings.add("Cycle detected in step dependencies: ${cyclicSteps.joinToString(", ")}")
            val cyclicStepsList = steps.filter { it.id !in sorted }
            return steps.filter { it.id in sorted } + cyclicStepsList
        }

        // Partition into prep and cook, preserving topological order within each phase
        val prepSteps = steps.filter { it.phase == StepPhase.prep }
        val cookSteps = steps.filter { it.phase == StepPhase.cook }

        val sortedPrep = prepSteps.sortedBy { sorted.indexOf(it.id) }
        val sortedCook = cookSteps.sortedBy { sorted.indexOf(it.id) }

        return sortedPrep + sortedCook
    }

    /** Represents a validation failure. */
    private data class ValidationError(val message: String)
}
