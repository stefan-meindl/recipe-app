package com.example.recipeapp.ui.resolver

import com.example.recipeapp.model.RecipeV2

/**
 * Validates a recipe statically (without dynamic filtering).
 * Checks servings, structural integrity of needs[] references, and cycle detection.
 */
object RecipeValidator {

    /**
     * Validate a recipe before resolution.
     *
     * @param recipe The recipe to validate
     * @param servings The requested serving count
     * @return List of validation errors (empty if valid)
     */
    fun validateStatic(recipe: RecipeV2, servings: Int): List<ValidationError> {
        val errors = mutableListOf<ValidationError>()

        // Check servings
        if (servings < 1) {
            errors.add(ValidationError.InvalidServings)
        }

        // Collect all valid IDs from the original recipe
        val stepIds = recipe.steps.map { it.id }.toSet()
        val ingredientIds = recipe.ingredients.map { it.id }.toSet()
        val allIds = stepIds + ingredientIds

        // Check that all needs[] IDs exist in the original recipe
        for (step in recipe.steps) {
            for (neededId in step.needs) {
                if (neededId !in allIds) {
                    errors.add(ValidationError.MissingId(neededId))
                }
            }
        }

        // Check for cycles in the needs[] graph among steps
        val cycleErrors = detectCycles(recipe.steps)
        errors.addAll(cycleErrors)

        return errors
    }

    /**
     * Detect cycles in the step dependency graph using DFS.
     *
     * @param steps The steps to check for cycles
     * @return List of CircularDependency errors (empty if no cycles)
     */
    private fun detectCycles(steps: List<com.example.recipeapp.model.StepV2>): List<ValidationError.CircularDependency> {
        if (steps.isEmpty()) return emptyList()

        val stepMap = steps.associateBy { it.id }
        val stepIds = stepMap.keys
        val visited = mutableSetOf<String>()
        val inStack = mutableSetOf<String>()
        val cycles = mutableListOf<ValidationError.CircularDependency>()

        for (stepId in stepIds) {
            if (stepId !in visited) {
                dfs(stepId, stepMap, visited, inStack, mutableListOf(), cycles)
            }
        }

        return cycles
    }

    private fun dfs(
        nodeId: String,
        stepMap: Map<String, com.example.recipeapp.model.StepV2>,
        visited: MutableSet<String>,
        inStack: MutableSet<String>,
        path: MutableList<String>,
        cycles: MutableList<ValidationError.CircularDependency>,
    ) {
        visited.add(nodeId)
        inStack.add(nodeId)
        path.add(nodeId)

        val step = stepMap[nodeId]
        if (step != null) {
            for (needId in step.needs) {
                if (needId !in stepMap) continue // Skip references to non-step IDs

                if (needId in inStack) {
                    // Found a cycle - extract the cycle path
                    val cycleStart = path.indexOf(needId)
                    if (cycleStart >= 0) {
                        val cyclePath = path.subList(cycleStart, path.size) + needId
                        cycles.add(ValidationError.CircularDependency(cyclePath))
                    }
                } else if (needId !in visited) {
                    dfs(needId, stepMap, visited, inStack, path, cycles)
                }
            }
        }

        path.removeAt(path.size - 1)
        inStack.remove(nodeId)
    }
}
