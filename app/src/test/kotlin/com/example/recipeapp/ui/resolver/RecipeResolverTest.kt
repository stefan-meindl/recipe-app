package com.example.recipeapp.ui.resolver

import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.PreparationMethod
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.Step
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class RecipeResolverTest {

    private fun sampleRecipe(): Recipe = Recipe(
        id = "pancakes",
        title = "Pancakes",
        servings = 4,
        preparationMethods = listOf(
            PreparationMethod(
                id = "stove",
                displayName = "Stovetop",
                ingredients = listOf(
                    Ingredient("Flour", "200 g"),
                    Ingredient("Milk", "300 ml"),
                ),
                steps = listOf(
                    Step("Mix dry ingredients"),
                    Step("Add milk"),
                ),
            ),
            PreparationMethod(
                id = "microwave",
                displayName = "Microwave Mug",
                ingredients = listOf(Ingredient("Flour", "4 tbsp")),
                steps = listOf(Step("Microwave 90s")),
            ),
        ),
    )

    @Test
    fun resolvesToFirstMethodByDefault() {
        val recipe = sampleRecipe()
        val resolution = RecipeResolver.resolve(recipe)

        assertEquals("stove", resolution.method.id)
        assertEquals(4, resolution.servings)
        assertEquals(1.0, resolution.multiplier, 0.0001)
        assertEquals(2, resolution.ingredients.size)
        assertEquals(2, resolution.steps.size)
    }

    @Test
    fun resolvesToSpecificMethod() {
        val recipe = sampleRecipe()
        val resolution = RecipeResolver.resolve(recipe, methodId = "microwave")

        assertEquals("microwave", resolution.method.id)
        assertEquals("4 tbsp", resolution.ingredients[0].quantity)
    }

    @Test
    fun scalesIngredientsUp() {
        val recipe = sampleRecipe()
        val resolution = RecipeResolver.resolve(recipe, servings = 8)

        assertEquals(8, resolution.servings)
        assertEquals(2.0, resolution.multiplier, 0.0001)
        assertEquals("400 g", resolution.ingredients[0].quantity)
        assertEquals("600 ml", resolution.ingredients[1].quantity)
    }

    @Test
    fun scalesIngredientsDown() {
        val recipe = sampleRecipe()
        val resolution = RecipeResolver.resolve(recipe, servings = 2)

        assertEquals(0.5, resolution.multiplier, 0.0001)
        assertEquals("100 g", resolution.ingredients[0].quantity)
        assertEquals("150 ml", resolution.ingredients[1].quantity)
    }

    @Test
    fun stepsAreNotScaled() {
        val recipe = sampleRecipe()
        val resolution = RecipeResolver.resolve(recipe, servings = 8)

        assertEquals("Mix dry ingredients", resolution.steps[0].description)
        assertEquals("Add milk", resolution.steps[1].description)
    }

    @Test
    fun selectionWithMatchingRecipeIdResolves() {
        val recipe = sampleRecipe()
        val resolution = RecipeResolver.resolve(
            recipe,
            RecipeResolver.Selection("pancakes", "microwave", 8),
        )

        assertEquals("microwave", resolution.method.id)
        assertEquals(8, resolution.servings)
    }

    @Test
    fun rejectsMismatchedRecipeId() {
        val recipe = sampleRecipe()
        assertThrows(IllegalArgumentException::class.java) {
            RecipeResolver.resolve(recipe, RecipeResolver.Selection("other-id"))
        }
    }

    @Test
    fun rejectsUnknownMethodId() {
        val recipe = sampleRecipe()
        assertThrows(IllegalArgumentException::class.java) {
            RecipeResolver.resolve(recipe, methodId = "no-such-method")
        }
    }

    @Test
    fun rejectsNonPositiveServings() {
        val recipe = sampleRecipe()
        assertThrows(IllegalArgumentException::class.java) {
            RecipeResolver.resolve(recipe, servings = 0)
        }
    }

    @Test
    fun findsRecipeByIdInCollection() {
        val recipes = listOf(sampleRecipe())
        val found = RecipeResolver.findRecipe(recipes, "pancakes")

        assertEquals("pancakes", found?.id)
    }

    @Test
    fun findsNothingForUnknownId() {
        val recipes = listOf(sampleRecipe())
        val found = RecipeResolver.findRecipe(recipes, "nope")

        assertEquals(null, found)
    }
}
