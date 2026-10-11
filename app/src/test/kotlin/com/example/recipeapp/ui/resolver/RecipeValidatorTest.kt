package com.example.recipeapp.ui.resolver

import com.example.recipeapp.model.IngredientVariant
import com.example.recipeapp.model.Method
import com.example.recipeapp.model.RecipeV2
import com.example.recipeapp.model.StepPhase
import com.example.recipeapp.model.StepV2
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse

class RecipeValidatorTest {

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

    // ==================== InvalidServings ====================

    @Test
    fun invalidServingsZero() {
        val recipe = sampleRecipe()
        val errors = RecipeValidator.validateStatic(recipe, servings = 0)
        assertTrue(errors.any { it is ValidationError.InvalidServings })
    }

    @Test
    fun invalidServingsNegative() {
        val recipe = sampleRecipe()
        val errors = RecipeValidator.validateStatic(recipe, servings = -1)
        assertTrue(errors.any { it is ValidationError.InvalidServings })
    }

    @Test
    fun validServings() {
        val recipe = sampleRecipe()
        val errors = RecipeValidator.validateStatic(recipe, servings = 1)
        assertFalse(errors.any { it is ValidationError.InvalidServings })
    }

    @Test
    fun validServingsLarge() {
        val recipe = sampleRecipe()
        val errors = RecipeValidator.validateStatic(recipe, servings = 100)
        assertFalse(errors.any { it is ValidationError.InvalidServings })
    }

    // ==================== MissingId ====================

    @Test
    fun missingIdInStepNeeds() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Mix", body = "Mix", needs = listOf("unknown-id")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        assertTrue(errors.any { it is ValidationError.MissingId && (it as ValidationError.MissingId).id == "unknown-id" })
    }

    @Test
    fun missingIdMultiple() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Mix", body = "Mix", needs = listOf("unknown1", "unknown2")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        val missingIds = errors.filterIsInstance<ValidationError.MissingId>().map { it.id }.toSet()
        assertTrue(missingIds.contains("unknown1"))
        assertTrue(missingIds.contains("unknown2"))
    }

    @Test
    fun validNeedsReferenceToStep() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "First", body = "First"),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Second", body = "Second", needs = listOf("s1")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        assertFalse(errors.any { it is ValidationError.MissingId })
    }

    @Test
    fun validNeedsReferenceToIngredient() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "flour", name = "Flour", qty = 200.0, unit = "g"),
            ),
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Mix", body = "Mix", needs = listOf("flour")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        assertFalse(errors.any { it is ValidationError.MissingId })
    }

    @Test
    fun emptyNeedsNoError() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Mix", body = "Mix", needs = emptyList()),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        assertFalse(errors.any { it is ValidationError.MissingId })
    }

    // ==================== CircularDependency ====================

    @Test
    fun simpleCycleAtoBtoA() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Step 1", body = "Step 1", needs = listOf("s2")),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Step 2", body = "Step 2", needs = listOf("s1")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        assertTrue(errors.any { it is ValidationError.CircularDependency })
    }

    @Test
    fun selfCycle() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Step 1", body = "Step 1", needs = listOf("s1")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        assertTrue(errors.any { it is ValidationError.CircularDependency })
    }

    @Test
    fun longerCycleAtoBtoCtoA() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Step 1", body = "Step 1", needs = listOf("s2")),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Step 2", body = "Step 2", needs = listOf("s3")),
                StepV2(id = "s3", phase = StepPhase.prep, title = "Step 3", body = "Step 3", needs = listOf("s1")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        assertTrue(errors.any { it is ValidationError.CircularDependency })
    }

    @Test
    fun noCycle() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Step 1", body = "Step 1"),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Step 2", body = "Step 2", needs = listOf("s1")),
                StepV2(id = "s3", phase = StepPhase.prep, title = "Step 3", body = "Step 3", needs = listOf("s2")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        assertFalse(errors.any { it is ValidationError.CircularDependency })
    }

    @Test
    fun noStepsNoCycle() {
        val recipe = sampleRecipe(steps = emptyList())
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        assertFalse(errors.any { it is ValidationError.CircularDependency })
    }

    @Test
    fun cyclePathContainsCorrectSteps() {
        val recipe = sampleRecipe(
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Step 1", body = "Step 1", needs = listOf("s2")),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Step 2", body = "Step 2", needs = listOf("s1")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        val cycleErrors = errors.filterIsInstance<ValidationError.CircularDependency>()
        assertTrue(cycleErrors.isNotEmpty())
        // The cycle path should contain both s1 and s2
        val cyclePath = cycleErrors[0].path
        assertTrue(cyclePath.contains("s1"))
        assertTrue(cyclePath.contains("s2"))
    }

    // ==================== Combined errors ====================

    @Test
    fun multipleErrorTypes() {
        val recipe = sampleRecipe(
            servings = 0,
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Step 1", body = "Step 1", needs = listOf("unknown")),
                StepV2(id = "s2", phase = StepPhase.prep, title = "Step 2", body = "Step 2", needs = listOf("s1")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 0)
        assertTrue(errors.any { it is ValidationError.InvalidServings })
        assertTrue(errors.any { it is ValidationError.MissingId })
    }

    @Test
    fun validRecipeNoErrors() {
        val recipe = sampleRecipe(
            ingredients = listOf(
                IngredientVariant(id = "flour", name = "Flour", qty = 200.0, unit = "g"),
            ),
            steps = listOf(
                StepV2(id = "s1", phase = StepPhase.prep, title = "Mix", body = "Mix"),
                StepV2(id = "s2", phase = StepPhase.cook, title = "Cook", body = "Cook", needs = listOf("s1")),
            )
        )
        val errors = RecipeValidator.validateStatic(recipe, servings = 4)
        assertTrue(errors.isEmpty())
    }
}
