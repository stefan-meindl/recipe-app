package com.example.recipeapp.data

import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.Recipe

/** Hard-coded sample data so the placeholder UI has something to show. */
class InMemoryRecipeRepository : RecipeRepository {
    override suspend fun getRecipes(): List<Recipe> = sampleRecipes
}

private val sampleRecipes = listOf(
    Recipe(
        id = "pancakes",
        title = "Pancakes",
        ingredients = listOf(
            Ingredient("Flour", "200 g"),
            Ingredient("Milk", "300 ml"),
            Ingredient("Eggs", "2"),
            Ingredient("Butter", "20 g"),
        ),
        steps = listOf(
            "Whisk the flour, milk and eggs into a smooth batter.",
            "Melt the butter in a pan over medium heat.",
            "Pour in a ladle of batter and cook until golden, about 2 minutes per side.",
        ),
    ),
    Recipe(
        id = "tomato-pasta",
        title = "Tomato Pasta",
        ingredients = listOf(
            Ingredient("Spaghetti", "250 g"),
            Ingredient("Canned tomatoes", "400 g"),
            Ingredient("Garlic", "2 cloves"),
            Ingredient("Olive oil", "2 tbsp"),
        ),
        steps = listOf(
            "Boil the spaghetti in salted water until al dente.",
            "Fry the garlic in olive oil, add the tomatoes and simmer for 10 minutes.",
            "Toss the drained pasta in the sauce and serve.",
        ),
    ),
)
