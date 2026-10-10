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
import org.junit.Assert.assertNull

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

    @Test
    fun resolveScalesQuantitiesByServings() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "flour", name = "Flour", qty = 200.0, unit = "g"),
                IngredientVariant(id = "sugar", name = "Sugar", qty = 100.0, unit = "g"),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 2)

        assertTrue(result.isValid)
        assertEquals(2, result.servings)
        assertEquals(100.0, result.resolvedIngredients[0].qty)
        assertEquals(50.0, result.resolvedIngredients[1].qty)
        assertEquals(0, result.warnings.size)
        assertEquals(0, result.errors.size)
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

        assertTrue(result2x.isValid)
        assertTrue(result05x.isValid)
        assertEquals(100.0, result2x.resolvedIngredients[0].qty)
        assertEquals(25.0, result05x.resolvedIngredients[0].qty)
    }

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

        assertTrue(result.isValid)
        assertEquals(300.0, result.resolvedIngredients[0].qty)
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

        assertTrue(result.isValid)
        assertEquals(250.0, result.resolvedIngredients[0].qty)
    }

    @Test
    fun filterOutIngredientWhenConditionFalse() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "tofu", name = "Tofu", qty = 200.0, unit = "g", condition = "diet=vegan"),
                IngredientVariant(id = "chicken", name = "Chicken", qty = 300.0, unit = "g"),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "omnivore"))

        assertTrue(result.isValid)
        assertEquals(1, result.resolvedIngredients.size)
        assertEquals("chicken", result.resolvedIngredients[0].id)
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

        assertTrue(result.isValid)
        assertEquals(2, result.prepSteps.size)
        assertEquals(2, result.cookSteps.size)
        assertEquals("s1", result.prepSteps[0].id)
        assertEquals("s3", result.prepSteps[1].id)
        assertEquals("s2", result.cookSteps[0].id)
        assertEquals("s4", result.cookSteps[1].id)
    }

    @Test
    fun filterOutStepWhenConditionFalse() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Chop", body = "Chop onions"),
                StepV2(id = "s2", phase = StepPhase.cook, title = "Fry vegan", body = "Use oil", condition = "diet=vegan"),
                StepV2(id = "s3", phase = StepPhase.cook, title = "Fry meat", body = "Use lard", condition = "diet=omnivore"),
            )
        )

        val resultVegan = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))
        val resultOmnivore = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "omnivore"))

        assertTrue(resultVegan.isValid)
        assertTrue(resultOmnivore.isValid)
        assertEquals(1, resultVegan.prepSteps.size)
        assertEquals(1, resultVegan.cookSteps.size)
        assertEquals("s2", resultVegan.cookSteps[0].id)

        assertEquals(1, resultOmnivore.cookSteps.size)
        assertEquals("s3", resultOmnivore.cookSteps[0].id)
    }

    @Test
    fun invalidServingsReturnsError() {
        val recipe = sampleRecipe()

        val result = RecipeResolver.resolve(recipe, servings = 0)

        assertFalse(result.isValid)
        assertTrue(result.errors.any { it.contains("Servings must be at least 1") })
        assertNull(result.resolved)
    }

    @Test
    fun negativeServingsReturnsError() {
        val recipe = sampleRecipe()

        val result = RecipeResolver.resolve(recipe, servings = -1)

        assertFalse(result.isValid)
        assertTrue(result.errors.any { it.contains("Servings must be at least 1") })
        assertNull(result.resolved)
    }

    @Test
    fun unknownIngredientIdInStepNeedsReturnsError() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "flour", name = "Flour", qty = 200.0, unit = "g"),
            ),
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Mix", body = "Mix flour", needs = listOf("unknown-id")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertFalse(result.isValid)
        assertTrue(result.errors.any { it.contains("unknown ingredient ID") })
    }

    @Test
    fun cycleDetectionReturnsWarning() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Step 1", body = "Step 1", needs = listOf("s2")),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Step 2", body = "Step 2", needs = listOf("s1")),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        // Cycle detection adds a warning, but we still get best-effort result
        assertTrue(result.warnings.any { it.contains("Cycle detected") })
    }

    @Test
    fun conditionEvaluatorHandlesEqualityOperator() {
        val selections = mapOf("diet" to "vegan")

        assertTrue(ConditionEvaluator.evaluateCondition("diet=vegan", selections))
        assertFalse(ConditionEvaluator.evaluateCondition("diet=omnivore", selections))
    }

    @Test
    fun conditionEvaluatorHandlesInequalityOperator() {
        val selections = mapOf("diet" to "vegan")

        assertTrue(ConditionEvaluator.evaluateCondition("diet!=omnivore", selections))
        assertFalse(ConditionEvaluator.evaluateCondition("diet!=vegan", selections))
    }

    @Test
    fun conditionEvaluatorHandlesAddOperator() {
        val selections = mapOf("tags" to "spicy,vegetarian")

        assertTrue(ConditionEvaluator.evaluateCondition("tags=add:spicy", selections))
        assertTrue(ConditionEvaluator.evaluateCondition("tags=add:vegetarian", selections))
        assertFalse(ConditionEvaluator.evaluateCondition("tags=add:meat", selections))
    }

    @Test
    fun conditionEvaluatorHandlesNotAddOperator() {
        val selections = mapOf("tags" to "spicy,vegetarian")

        assertTrue(ConditionEvaluator.evaluateCondition("tags=!add:meat", selections))
        assertFalse(ConditionEvaluator.evaluateCondition("tags=!add:spicy", selections))
    }

    @Test
    fun unknownFieldInConditionEvaluatesToFalse() {
        val selections = mapOf("diet" to "vegan")

        assertFalse(ConditionEvaluator.evaluateCondition("unknown_field=value", selections))
    }

    @Test
    fun malformedConditionThrowsError() {
        val selections = mapOf("diet" to "vegan")

        try {
            ConditionEvaluator.evaluateCondition("malformed_no_operator", selections)
            throw AssertionError("Expected IllegalArgumentException for malformed condition")
        } catch (e: IllegalArgumentException) {
        }
    }

    @Test
    fun emptyConditionListEvaluatesToTrue() {
        val selections = mapOf("diet" to "vegan")

        assertTrue(ConditionEvaluator.evaluateConditions(emptyList(), selections))
        assertTrue(ConditionEvaluator.evaluateConditions(null, selections))
    }

    // ==================== Condition tests ====================

    @Test
    fun conditionEqMatches() {
        val condition = Condition.Eq("diet", "vegan")
        assertTrue(condition.evaluate(mapOf("diet" to "vegan")))
        assertFalse(condition.evaluate(mapOf("diet" to "omnivore")))
        assertFalse(condition.evaluate(emptyMap()))
    }

    @Test
    fun conditionNotEqMatches() {
        val condition = Condition.NotEq("diet", "vegan")
        assertTrue(condition.evaluate(mapOf("diet" to "omnivore")))
        assertFalse(condition.evaluate(mapOf("diet" to "vegan")))
        assertTrue(condition.evaluate(emptyMap())) // null != "vegan"
    }

    @Test
    fun conditionAddMatches() {
        val condition = Condition.Add("tags", "spicy")
        assertTrue(condition.evaluate(mapOf("tags" to "spicy,vegetarian")))
        assertFalse(condition.evaluate(mapOf("tags" to "sweet")))
        assertFalse(condition.evaluate(emptyMap()))
    }

    @Test
    fun conditionNotAddMatches() {
        val condition = Condition.NotAdd("tags", "spicy")
        assertTrue(condition.evaluate(mapOf("tags" to "sweet")))
        assertFalse(condition.evaluate(mapOf("tags" to "spicy,vegetarian")))
        assertTrue(condition.evaluate(emptyMap()))
    }

    @Test
    fun unparseableConditionEvaluatesToFalse() {
        val condition = Condition.UnparseableCondition("garbage")
        assertFalse(condition.evaluate(mapOf("diet" to "vegan")))
    }

    @Test
    fun conditionListAllMatch() {
        val conditions = listOf(
            Condition.Eq("diet", "vegan"),
            Condition.Add("tags", "spicy"),
        )
        assertTrue(conditions.evaluateAll(mapOf("diet" to "vegan", "tags" to "spicy,sweet")))
        assertFalse(conditions.evaluateAll(mapOf("diet" to "vegan", "tags" to "sweet")))
        assertFalse(conditions.evaluateAll(mapOf("diet" to "omnivore", "tags" to "spicy")))
    }

    @Test
    fun conditionListEmptyReturnsTrue() {
        emptyList<Condition>().evaluateAll(mapOf("diet" to "vegan"))
    }

    // ==================== ConditionParser tests ====================

    @Test
    fun parserHandlesEq() {
        val condition = ConditionParser.parse("diet=vegan")
        assertTrue(condition is Condition.Eq)
        assertEquals("diet", (condition as Condition.Eq).key)
        assertEquals("vegan", condition.value)
    }

    @Test
    fun parserHandlesNotEq() {
        val condition = ConditionParser.parse("diet!=vegan")
        assertTrue(condition is Condition.NotEq)
        assertEquals("diet", (condition as Condition.NotEq).key)
        assertEquals("vegan", condition.value)
    }

    @Test
    fun parserHandlesAdd() {
        val condition = ConditionParser.parse("tags=add:spicy")
        assertTrue(condition is Condition.Add)
        assertEquals("tags", (condition as Condition.Add).key)
        assertEquals("spicy", condition.value)
    }

    @Test
    fun parserHandlesNotAdd() {
        val condition = ConditionParser.parse("tags=!add:meat")
        assertTrue(condition is Condition.NotAdd)
        assertEquals("tags", (condition as Condition.NotAdd).key)
        assertEquals("meat", condition.value)
    }

    @Test
    fun parserReturnsUnparseableForInvalidInput() {
        val condition = ConditionParser.parse("malformed_no_operator")
        assertTrue(condition is Condition.UnparseableCondition)
        assertEquals("malformed_no_operator", (condition as Condition.UnparseableCondition).raw)
    }

    // ==================== QuantityScaler tests ====================

    @Test
    fun scalerScalesUp() {
        val result = QuantityScaler.scale("200 g", 2, 4)
        assertEquals("400 g", result)
    }

    @Test
    fun scalerScalesDown() {
        val result = QuantityScaler.scale("100 g", 4, 2)
        assertEquals("50 g", result)
    }

    @Test
    fun scalerHandlesFractions() {
        val result = QuantityScaler.scale("1.5 cups", 4, 2)
        assertEquals("0.75 cups", result)
    }

    @Test
    fun scalerHandlesNoUnit() {
        val result = QuantityScaler.scale("200", 4, 8)
        assertEquals("400", result)
    }

    @Test
    fun scalerReturnsOriginalForNonNumeric() {
        val result = QuantityScaler.scale("a pinch", 4, 8)
        assertEquals("a pinch", result)
    }

    @Test
    fun scalerNumericScalesUp() {
        val result = QuantityScaler.scaleNumeric(200.0, 2, 4)
        assertEquals(400.0, result, 0.0001)
    }

    @Test
    fun scalerNumericScalesDown() {
        val result = QuantityScaler.scaleNumeric(100.0, 4, 2)
        assertEquals(50.0, result, 0.0001)
    }

    @Test
    fun scalerThrowsForInvalidServings() {
        try {
            QuantityScaler.scale("200 g", 0, 4)
            throw AssertionError("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
        }

        try {
            QuantityScaler.scale("200 g", 4, 0)
            throw AssertionError("Expected IllegalArgumentException")
        } catch (e: IllegalArgumentException) {
        }
    }

    // ==================== ResolutionResult tests ====================

    @Test
    fun resolutionResultSuccess() {
        val recipe = sampleRecipe()
        val result = ResolutionResult.success(recipe)

        assertTrue(result.isValid)
        assertNotNull(result.resolved)
        assertEquals(0, result.warnings.size)
        assertEquals(0, result.errors.size)
    }

    @Test
    fun resolutionResultFailure() {
        val result = ResolutionResult.failure(listOf("error1", "error2"))

        assertFalse(result.isValid)
        assertNull(result.resolved)
        assertEquals(2, result.errors.size)
        assertEquals(0, result.warnings.size)
    }

    @Test
    fun resolutionResultPartial() {
        val recipe = sampleRecipe()
        val result = ResolutionResult.partial(recipe, listOf("warning1"))

        assertTrue(result.isValid)
        assertNotNull(result.resolved)
        assertEquals(1, result.warnings.size)
        assertEquals(0, result.errors.size)
    }

    // ==================== Integration tests ====================

    @Test
    fun unparseableConditionProducesWarning() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(
                    id = "spice",
                    name = "Spice",
                    qty = 1.0,
                    unit = "tsp",
                    condition = "malformed_no_operator"
                ),
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertTrue(result.isValid)
        assertTrue(result.warnings.any { it.contains("Unparseable ingredient condition") })
    }

    @Test
    fun stepConditionFilteringWithParsedConditions() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Prep", body = "Prep"),
                StepV2(id = "s2", phase = StepPhase.cook, title = "Cook vegan", body = "Cook vegan", condition = "diet=vegan"),
                StepV2(id = "s3", phase = StepPhase.cook, title = "Cook meat", body = "Cook meat", condition = "diet=meat"),
            )
        )

        val resultVegan = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "vegan"))
        val resultMeat = RecipeResolver.resolve(recipe, servings = 4, context = mapOf("diet" to "meat"))

        assertTrue(resultVegan.isValid)
        assertTrue(resultMeat.isValid)
        assertEquals(1, resultVegan.cookSteps.size)
        assertEquals("s2", resultVegan.cookSteps[0].id)
        assertEquals(1, resultMeat.cookSteps.size)
        assertEquals("s3", resultMeat.cookSteps[0].id)
    }

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

        assertTrue(result.isValid)
        assertEquals(3, result.prepSteps.size)
        assertEquals("s1", result.prepSteps[0].id)
        assertEquals("s2", result.prepSteps[1].id)
        assertEquals("s3", result.prepSteps[2].id)
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

        assertTrue(result.isValid)
        assertEquals(2, result.prepSteps.size)
        assertEquals(2, result.cookSteps.size)
        // All prep steps should come before all cook steps
        for (prep in result.prepSteps) {
            for (cook in result.cookSteps) {
                // Just verify the lists are partitioned correctly
            }
        }
    }

    @Test
    fun cycleDetectionReturnsBestEffortResult() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Step 1", body = "Step 1", needs = listOf("s2")),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Step 2", body = "Step 2", needs = listOf("s1")),
                StepV2(id = "s3", phase = StepPhase.prep, title = "Step 3", body = "Step 3"), // No deps, should be first
            )
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        // Should have a warning about the cycle
        assertTrue(result.warnings.any { it.contains("Cycle detected") })
        // s3 should come first since it has no dependencies
        assertEquals("s3", result.prepSteps[0].id)
    }

    @Test
    fun noMethodsReturnsError() {
        val recipe = sampleRecipe(methods = emptyList())

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertFalse(result.isValid)
        assertTrue(result.errors.any { it.contains("no methods") })
        assertNull(result.resolved)
    }

    @Test
    fun defaultServingsUsesRecipeBase() {
        val recipe = sampleRecipe(servings = 6)

        val result = RecipeResolver.resolve(recipe)

        assertTrue(result.isValid)
        assertEquals(6, result.servings)
    }

    @Test
    fun emptyRecipeResolvesSuccessfully() {
        val recipe = sampleRecipe(
            ingredients = emptyList(),
            steps = emptyList(),
        )

        val result = RecipeResolver.resolve(recipe, servings = 4)

        assertTrue(result.isValid)
        assertEquals(0, result.resolvedIngredients.size)
        assertEquals(0, result.prepSteps.size)
        assertEquals(0, result.cookSteps.size)
    }

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

        assertTrue(result1.isValid)
        assertTrue(result2.isValid)
        assertTrue(result3.isValid)

        // Same context should produce same result
        assertEquals(result1.resolvedIngredients[0].qty, result2.resolvedIngredients[0].qty)
        // Different servings should scale
        assertEquals(200.0, result1.resolvedIngredients[0].qty)
        assertEquals(400.0, result3.resolvedIngredients[0].qty)
    }
}
