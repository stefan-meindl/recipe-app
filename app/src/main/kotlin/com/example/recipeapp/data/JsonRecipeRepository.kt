package com.example.recipeapp.data

import android.content.Context
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.InputStream

/** Reads recipe JSON files from assets/recipes/ using kotlinx.serialization. */
class JsonRecipeRepository(private val context: Context) : RecipeRepository {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    override suspend fun getRecipes(): List<Recipe> = withContext(Dispatchers.IO) {
        val fileNames = context.assets.list("recipes")?.toList() ?: emptyList()

        fileNames.mapNotNull { fileName ->
            context.assets.open("recipes/$fileName").use { stream ->
                try {
                    json.decodeFromStream<Recipe>(stream)
                } catch (e: Exception) {
                    android.util.Log.e("JsonRecipeRepository", "Failed to parse $fileName", e)
                    null
                }
            }
        }
    }
}
