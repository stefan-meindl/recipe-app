package com.example.recipeapp.data

import com.example.recipeapp.model.Recipe

/** The only way the UI layer reads recipes. A JSON-file-backed version will implement this later. */
interface RecipeRepository {
    suspend fun getRecipes(): List<Recipe>
}
