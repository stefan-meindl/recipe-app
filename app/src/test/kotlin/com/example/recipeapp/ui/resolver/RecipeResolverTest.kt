package com.example.recipeapp.ui.resolver

import com.example.recipeapp.model.AltCondition
import com.example.recipeapp.model.IngredientVariant
import com.example.recipeapp.model.Method
import com.example.recipeapp.model.RecipeV2
import com.example.recipeapp.model.StepPhase
import com.example.recipeapp.model.StepV2
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull

class RecipeResolverTest {

    private fun sampleRecipe(
        id: String = "test-recipe",
        title: String = "Test Recipe",
        servings: Int = 4,
        ingredients: List<IngredientVariant> = emptyList(),
        methods: List<Method> = listOf(
            Method(id = "method-1", label = "Default", question = "How?", options = emptyList())
        ),
        steps: List<StepV2> = emptyList(),
    ) = RecipeV2(
        id = id,
        title = title,
        servings = servings,
        methods = methods,
        ingredients = ingredients,
        steps = steps,
    )

    // ==================== Scaling ====================

    @Test
    fun resolveScalesQuantitiesByServings() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "flour", name = "Flour", qty = 200.0, unit = "g"),
                IngredientVariant(id = "sugar", name = "Sugar", qty = 100.0, unit = "g"),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 2)

        assertTrue(result.isSuccess)
        val resolved = result.getOrNull()!!
        assertEquals(2, resolved.metadata.requestedServings)
        assertEquals(100.0, resolved.ingredients[0].quantity, 0.001)
        assertEquals(50.0, resolved.ingredients[1].quantity, 0.001)
    }

    @Test
    fun resolveScalesQuantitiesBothDirections() {
        val recipe = sampleRecipe(
            servings = 4,
            ingredients = listOf(
                IngredientVariant(id = "butter", name = "Butter", qty = 50.0, unit = "g"),
            )
        )

        val result2x = RecipeResolver.resolve(recipe, servings = 8)
        val result05x = RecipeResolver.resolve(recipe, servings = 2)

        assertTrue(result2x.isSuccess)
        assertTrue(result05x.isSuccess)
        assertEquals(100.0, result2x.getOrNull()!!.ingredients[0].quantity, 0.001)
        assertEquals(25.0, result05x.getOrNull()!!.ingredients[0].quantity, 0.001)
    }

    @Test
    fun resolveScalesWithRounding() {
        // 200 * (3/7) = 85.714... → 85.71
        val recipe = sampleRecipe(
            servings = 7,
            ingredients = listOf(
                IngredientVariant(id = "flour", name = "Flour", qty = 200.0, unit = "g"),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 3)
        assertTrue(result.isSuccess)
        assertEquals(85.71, result.getOrNull()!!.ingredients[0].quantity, 0.001)
    }

    // ==================== Alt overrides ====================

    @Test
    fun applyAltOverrideWhenConditionTrue() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(
                    id = "milk",
                    name = "Milk",
                    qty = 250.0,
                    unit = "ml",
                    alt = listOf(AltCondition(condition = "diet=vegan", value = 300.0))
                ),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))

        assertTrue(result.isSuccess)
        // Full replacement: alt completely swaps the ingredient, quantity becomes 300.0
        assertEquals(300.0, result.getOrNull()!!.ingredients[0].quantity, 0.001)
    }

    @Test
    fun ignoreAltOverrideWhenConditionFalse() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(
                    id = "milk",
                    name = "Milk",
                    qty = 250.0,
                    unit = "ml",
                    alt = listOf(AltCondition(condition = "diet=vegan", value = 300.0))
                ),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "regular"))

        assertTrue(result.isSuccess)
        assertEquals(250.0, result.getOrNull()!!.ingredients[0].quantity, 0.001)
    }

    @Test
    fun altFullReplacementNotPartial() {
        // Verify alt completely replaces the ingredient, not just the quantity
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(
                    id = "milk",
                    name = "Cow Milk",
                    qty = 250.0,
                    unit = "ml",
                    note = "Regular milk",
                    alt = listOf(AltCondition(condition = "diet=vegan", value = 300.0))
                ),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))

        assertTrue(result.isSuccess)
        val resolved = result.getOrNull()!!.ingredients[0]
        // The original is preserved in the ResolvedIngredient.original field
        assertEquals("Cow Milk", resolved.original.name)
        assertEquals(250.0, resolved.original.qty!!, 0.001)
        // But the quantity is replaced by alt's value
        assertEquals(300.0, resolved.quantity, 0.001)
    }

    @Test
    fun multipleAltOverrides() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(
                    id = "milk",
                    name = "Milk",
                    qty = 250.0,
                    unit = "ml",
                    alt = listOf(
                        AltCondition(condition = "diet=vegan", value = 300.0),
                        AltCondition(condition = "diet=lowfat", value = 200.0),
                    )
                ),
            )
        )

        // Last matching alt wins (since we iterate all and apply each)
        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))
        assertTrue(result.isSuccess)
        assertEquals(300.0, result.getOrNull()!!.ingredients[0].quantity, 0.001)
    }

    @Test
    fun missingAltField() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(
                    id = "milk",
                    name = "Milk",
                    qty = 250.0,
                    unit = "ml",
                    alt = listOf(AltCondition(condition = "diet=vegan", value = 300.0))
                ),
            )
        )

        // Context doesn't match any alt condition
        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("flavor" to "sweet"))
        assertTrue(result.isSuccess)
        assertEquals(250.0, result.getOrNull()!!.ingredients[0].quantity, 0.001)
    }

    // ==================== Variation marking ====================

    @Test
    fun variationTrueWhenConditionPresent() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(
                    id = "tofu",
                    name = "Tofu",
                    qty = 200.0,
                    unit = "g",
                    condition = "diet=vegan",
                ),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()!!.ingredients[0].variation)
    }

    @Test
    fun variationFalseWhenConditionAbsent() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(
                    id = "flour",
                    name = "Flour",
                    qty = 200.0,
                    unit = "g",
                ),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)
        assertTrue(result.isSuccess)
        assertFalse(result.getOrNull()!!.ingredients[0].variation)
    }

    // ==================== Ingredient filtering ====================

    @Test
    fun filterOutIngredientWhenConditionFalse() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "tofu", name = "Tofu", qty = 200.0, unit = "g", condition = "diet=vegan"),
                IngredientVariant(id = "chicken", name = "Chicken", qty = 300.0, unit = "g"),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "omnivore"))

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()!!.ingredients.size)
        assertEquals("chicken", result.getOrNull()!!.ingredients[0].original.id)
    }

    @Test
    fun filterInIngredientWhenConditionTrue() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "tofu", name = "Tofu", qty = 200.0, unit = "g", condition = "diet=vegan"),
                IngredientVariant(id = "chicken", name = "Chicken", qty = 300.0, unit = "g"),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()!!.ingredients.size)
    }

    // ==================== Step filtering & partitioning ====================

    @Test
    fun filterOutStepWhenConditionFalse() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Chop", body = "Chop onions"),
                StepV2(id = "s2", phase = StepPhase.cook, title = "Fry vegan", body = "Use oil", condition = "diet=vegan"),
                StepV2(id = "s3", phase = StepPhase.cook, title = "Fry meat", body = "Use lard", condition = "diet=meat"),
            )
        )

        val resultVegan = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))
        val resultMeat = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "meat"))

        assertTrue(resultVegan.isSuccess)
        assertTrue(resultMeat.isSuccess)
        // s1 always passes (no condition), s2 passes for vegan, s3 passes for meat
        assertEquals(2, resultVegan.getOrNull()!!.steps.size)
        assertEquals(2, resultMeat.getOrNull()!!.steps.size)
        assertEquals("s2", resultVegan.getOrNull()!!.steps[1].original.id) // cook step
        assertEquals("s3", resultMeat.getOrNull()!!.steps[1].original.id) // cook step
    }

    @Test
    fun partitionStepsByPhase() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Chop", body = "Chop the onions"),
                StepV2(id = "s2", phase = StepPhase.cook, title = "Fry", body = "Fry for 5 min"),
                StepV2(id = "s3", phase = StepPhase.prep, title = "Measure", body = "Measure flour"),
                StepV2(id = "s4", phase = StepPhase.cook, title = "Bake", body = "Bake for 20 min"),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertTrue(result.isSuccess)
        val resolved = result.getOrNull()!!
        assertEquals(4, resolved.steps.size)
        // Prep steps come first, then cook steps
        assertEquals("s1", resolved.steps[0].original.id)
        assertEquals("s3", resolved.steps[1].original.id)
        assertEquals("s2", resolved.steps[2].original.id)
        assertEquals("s4", resolved.steps[3].original.id)
    }

    @Test
    fun prepStepsComeBeforeCookSteps() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "c1", phase = StepPhase.cook, title = "Cook 1", body = "Cook 1"),
                StepV2(id = "p1", phase = StepPhase.prep, title = "Prep 1", body = "Prep 1"),
                StepV2(id = "c2", phase = StepPhase.cook, title = "Cook 2", body = "Cook 2"),
                StepV2(id = "p2", phase = StepPhase.prep, title = "Prep 2", body = "Prep 2"),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertTrue(result.isSuccess)
        val resolved = result.getOrNull()!!
        assertEquals(4, resolved.steps.size)
        // All prep steps should come before all cook steps
        var foundCook = false
        for (step in resolved.steps) {
            if (step.original.phase == StepPhase.cook) {
                foundCook = true
            } else if (foundCook) {
                // Prep step after cook step — fail
                assertTrue("Prep step should not come after cook step", false)
            }
        }
    }

    // ==================== Label resolution ====================

    @Test
    fun resolvedNeedsContainsLabels() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Chop Onions", body = "Chop"),
                StepV2(id = "s2", phase = StepPhase.cook, title = "Fry", body = "Fry onions", needs = listOf("s1")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)
        assertTrue(result.isSuccess)

        val resolved = result.getOrNull()!!
        val stepWithNeeds = resolved.steps.find { it.original.id == "s2" }
        assertNotNull(stepWithNeeds)
        assertEquals(1, stepWithNeeds!!.resolvedNeeds.size)
        assertEquals("Chop Onions", stepWithNeeds.resolvedNeeds[0])
    }

    @Test
    fun resolvedNeedsEmptyWhenNoNeeds() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Chop", body = "Chop"),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)
        assertTrue(result.isSuccess)

        val resolved = result.getOrNull()!!
        assertEquals(0, resolved.steps[0].resolvedNeeds.size)
    }

    @Test
    fun resolvedNeedsMultipleLabels() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Chop", body = "Chop"),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Measure", body = "Measure"),
                StepV2(id = "s3", phase = StepPhase.cook, title = "Cook", body = "Cook", needs = listOf("s1", "s2")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)
        assertTrue(result.isSuccess)

        val resolved = result.getOrNull()!!
        val stepWithNeeds = resolved.steps.find { it.original.id == "s3" }
        assertNotNull(stepWithNeeds)
        assertEquals(2, stepWithNeeds!!.resolvedNeeds.size)
    }

    // ==================== Dynamic prerequisite check ====================

    @Test
    fun missingPrerequisiteAfterFiltering() {
        // Step B depends on A, but A is filtered out by condition
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Prep A", body = "Prep A", condition = "diet=vegan"),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Prep B", body = "Prep B", needs = listOf("s1")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "omnivore"))

        // s1 is filtered out (condition fails), but s2 needs s1 → MissingPrerequisite
        assertTrue(result.isFailure)
        val error = result.exceptionOrNull()!!
        assertTrue(error.message!!.contains("MissingPrerequisite"))
    }

    @Test
    fun prerequisitePresentAfterFiltering() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Prep A", body = "Prep A", condition = "diet=vegan"),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Prep B", body = "Prep B", needs = listOf("s1")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))

        assertTrue(result.isSuccess)
    }

    // ==================== Topological ordering ====================

    @Test
    fun topologicalOrderingRespectsNeeds() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "First", body = "First"),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Second", body = "Second", needs = listOf("s1")),
                StepV2(id = "s3", phase = StepPhase.prep, title = "Third", body = "Third", needs = listOf("s2")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertTrue(result.isSuccess)
        val resolved = result.getOrNull()!!
        assertEquals(3, resolved.steps.size)
        assertEquals("s1", resolved.steps[0].original.id)
        assertEquals("s2", resolved.steps[1].original.id)
        assertEquals("s3", resolved.steps[2].original.id)
    }

    // ==================== Validation errors ====================

    @Test
    fun invalidServingsReturnsFailure() {
        val recipe = sampleRecipe()

        val result = RecipeResolver.resolve(recipe, servings = 0)

        assertTrue(result.isFailure)
        val error = result.exceptionOrNull()!!
        assertTrue(error.message!!.contains("Servings must be at least 1"))
    }

    @Test
    fun negativeServingsReturnsFailure() {
        val recipe = sampleRecipe()

        val result = RecipeResolver.resolve(recipe, servings = -1)

        assertTrue(result.isFailure)
        val error = result.exceptionOrNull()!!
        assertTrue(error.message!!.contains("Servings must be at least 1"))
    }

    @Test
    fun missingNeedsIdReturnsFailure() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Mix", body = "Mix", needs = listOf("unknown-id")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertTrue(result.isFailure)
        val error = result.exceptionOrNull()!!
        assertTrue(error.message!!.contains("Missing ID"))
    }

    @Test
    fun cycleDetectionReturnsFailure() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Step 1", body = "Step 1", needs = listOf("s2")),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Step 2", body = "Step 2", needs = listOf("s1")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertTrue(result.isFailure)
        val error = result.exceptionOrNull()!!
        assertTrue(error.message!!.contains("Circular dependency"))
    }

    // ==================== Full pipeline ====================

    @Test
    fun validRecipeReturnsSuccess() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "flour", name = "Flour", qty = 200.0, unit = "g"),
                IngredientVariant(id = "sugar", name = "Sugar", qty = 100.0, unit = "g"),
            ),
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Mix", body = "Mix"),
                StepV2(id = "s2", phase = StepPhase.cook, title = "Cook", body = "Cook", needs = listOf("s1")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertTrue(result.isSuccess)
        val resolved = result.getOrNull()!!
        assertEquals(2, resolved.ingredients.size)
        assertEquals(2, resolved.steps.size)
        assertEquals("test-recipe", resolved.metadata.id)
        assertEquals("Test Recipe", resolved.metadata.title)
        assertEquals(4, resolved.metadata.baseServings)
        assertEquals(4, resolved.metadata.requestedServings)
    }

    @Test
    fun invalidInputsReturnFailure() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Mix", body = "Mix", needs = listOf("unknown")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 0)

        assertTrue(result.isFailure)
    }

    // ==================== Context passthrough ====================

    @Test
    fun contextPassthroughToConditionEvaluator() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "tofu", name = "Tofu", qty = 200.0, unit = "g", condition = "diet=vegan"),
                IngredientVariant(id = "chicken", name = "Chicken", qty = 300.0, unit = "g"),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))

        assertTrue(result.isSuccess)
        assertEquals(2, result.getOrNull()!!.ingredients.size)
    }

    // ==================== Empty recipe ====================

    @Test
    fun emptyRecipeResolvesSuccessfully() {
        val recipe = sampleRecipe(
            ingredients = emptyList(),
            steps = emptyList(),
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertTrue(result.isSuccess)
        val resolved = result.getOrNull()!!
        assertEquals(0, resolved.ingredients.size)
        assertEquals(0, resolved.steps.size)
    }

    // ==================== Stateless resolution ====================

    @Test
    fun statelessResolution() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "flour", name = "Flour", qty = 200.0, unit = "g"),
            ),
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Mix", body = "Mix"),
            ),
        )

        // Resolve the same recipe multiple times with different contexts
        val result1 = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))
        val result2 = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))
        val result3 = RecipeResolver.resolve(recipe, servings = 8, context = mapOf("diet" to "vegan"))

        assertTrue(result1.isSuccess)
        assertTrue(result2.isSuccess)
        assertTrue(result3.isSuccess)

        // Same context should produce same result
        assertEquals(result1.getOrNull()!!.ingredients[0].quantity, result2.getOrNull()!!.ingredients[0].quantity, 0.001)
        // Different servings should scale
        assertEquals(200.0, result1.getOrNull()!!.ingredients[0].quantity, 0.001)
        assertEquals(400.0, result3.getOrNull()!!.ingredients[0].quantity, 0.001)
    }
}
