package com.example.recipeapp.model

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RecipeSerializationTest {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Test
    fun serializeAndDeserializeFullRecipe() {
        val recipe = Recipe(
            id = "test-recipe",
            title = "Test Recipe",
            prepTimeMinutes = 10,
            cookTimeMinutes = 20,
            servings = 4,
            tags = listOf("test", "quick"),
            images = listOf("https://example.com/test.jpg"),
            notes = "A test recipe",
            preparationMethods = listOf(
                PreparationMethod(
                    id = "method1",
                    displayName = "Method 1",
                    ingredients = listOf(
                        Ingredient(
                            "Flour",
                            "200 g",
                            alternatives = listOf("GF flour"),
                            tips = listOf("Sift it"),
                        ),
                    ),
                    steps = listOf(
                        Step(
                            "Mix dry ingredients",
                            durationMinutes = 5,
                            tips = listOf("Be gentle"),
                        ),
                    ),
                ),
            ),
        )

        val jsonStr = json.encodeToString(recipe)
        val deserialized = json.decodeFromString<Recipe>(jsonStr)

        assertEquals(recipe.id, deserialized.id)
        assertEquals(recipe.title, deserialized.title)
        assertEquals(recipe.prepTimeMinutes, deserialized.prepTimeMinutes)
        assertEquals(recipe.cookTimeMinutes, deserialized.cookTimeMinutes)
        assertEquals(recipe.servings, deserialized.servings)
        assertEquals(recipe.tags, deserialized.tags)
        assertEquals(recipe.images, deserialized.images)
        assertEquals(recipe.notes, deserialized.notes)
        assertEquals(recipe.preparationMethods.size, deserialized.preparationMethods.size)
        assertEquals(recipe.preparationMethods[0].id, deserialized.preparationMethods[0].id)
        assertEquals(recipe.preparationMethods[0].ingredients[0].name, deserialized.preparationMethods[0].ingredients[0].name)
        assertEquals(recipe.preparationMethods[0].steps[0].description, deserialized.preparationMethods[0].steps[0].description)
    }

    @Test
    fun serializeRecipeWithMinimalFields() {
        val recipe = Recipe(
            id = "minimal",
            title = "Minimal",
            preparationMethods = listOf(
                PreparationMethod(
                    id = "m1",
                    displayName = "M1",
                    ingredients = emptyList(),
                    steps = emptyList(),
                ),
            ),
        )

        val jsonStr = json.encodeToString(recipe)
        val deserialized = json.decodeFromString<Recipe>(jsonStr)

        assertEquals("minimal", deserialized.id)
        assertNull(deserialized.prepTimeMinutes)
        assertNull(deserialized.cookTimeMinutes)
        assertEquals(4, deserialized.servings)
        assertEquals(emptyList<String>(), deserialized.tags)
        assertEquals(emptyList<String>(), deserialized.images)
        assertNull(deserialized.notes)
    }

    @Test
    fun scaleIngredientByMultiplier() {
        val ingredient = Ingredient("Flour", "200 g")
        val scaled = ingredient.scaledBy(2.0)
        assertEquals("400 g", scaled.quantity)
        assertEquals("Flour", scaled.name)
    }

    @Test
    fun scaleIngredientWithNonIntegerMultiplier() {
        val ingredient = Ingredient("Milk", "300 ml")
        val scaled = ingredient.scaledBy(0.5)
        assertEquals("150 ml", scaled.quantity)
    }

    @Test
    fun scaleIngredientToNonIntegerFractionalValue() {
        // Regression: scaled quantity must not render in scientific notation
        // (e.g. 150 g * 1.25 = 187.5 g, not "1.9e+02 g").
        val ingredient = Ingredient("Cheddar cheese", "150 g")
        val scaled = ingredient.scaledBy(1.25)
        assertEquals("187.5 g", scaled.quantity)
    }

    @Test
    fun scaleIngredientToIntegerValue() {
        val ingredient = Ingredient("Cheddar cheese", "150 g")
        val scaled = ingredient.scaledBy(2.0)
        assertEquals("300 g", scaled.quantity)
    }

    @Test
    fun deserializeIgnoresUnknownKeys() {
        val jsonStr = """
        {
            "id": "pancakes",
            "title": "Pancakes",
            "unknownField": "should be ignored",
            "preparationMethods": [
                {
                    "id": "stove",
                    "displayName": "Stovetop",
                    "ingredients": [],
                    "steps": []
                }
            ]
        }
        """.trimIndent()

        val recipe = json.decodeFromString<Recipe>(jsonStr)
        assertEquals("pancakes", recipe.id)
        assertEquals("Pancakes", recipe.title)
    }

    @Test
    fun deserializeWithMissingOptionalFields() {
        val jsonStr = """
        {
            "id": "simple",
            "title": "Simple",
            "preparationMethods": [
                {
                    "id": "m1",
                    "displayName": "Method",
                    "ingredients": [
                        { "name": "Salt", "quantity": "pinch" }
                    ],
                    "steps": [
                        { "description": "Add salt" }
                    ]
                }
            ]
        }
        """.trimIndent()

        val recipe = json.decodeFromString<Recipe>(jsonStr)
        assertEquals("simple", recipe.id)
        assertEquals("Method", recipe.preparationMethods[0].displayName)
        assertEquals("Salt", recipe.preparationMethods[0].ingredients[0].name)
        assertNull(recipe.preparationMethods[0].ingredients[0].alternatives)
        assertNull(recipe.preparationMethods[0].ingredients[0].tips)
        assertNull(recipe.preparationMethods[0].steps[0].durationMinutes)
        assertNull(recipe.preparationMethods[0].steps[0].tips)
    }
}
