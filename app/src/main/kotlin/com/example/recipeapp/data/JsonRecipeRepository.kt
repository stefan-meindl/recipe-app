package com.example.recipeapp.data

import android.content.Context
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class JsonRecipeRepository(private val context: Context) : RecipeRepository {

    override suspend fun getRecipes(): List<Recipe> = withContext(Dispatchers.IO) {
        val fileNames = context.assets.list("recipes")?.toList() ?: emptyList()
        
        fileNames.mapNotNull { fileName ->
            context.assets.open("recipes/$fileName").use { stream ->
                val json = stream.bufferedReader().use { it.readText() }
                parseRecipe(json)
            }
        }
    }

    private fun parseRecipe(json: String): Recipe? {
        return try {
            val obj = JSONObject(json)
            val id = obj.getString("id")
            val title = obj.getString("title")
            
            val ingredients = mutableListOf<Ingredient>()
            val ingredientsArray = obj.getJSONArray("ingredients")
            for (i in 0 until ingredientsArray.length()) {
                val ing = ingredientsArray.getJSONObject(i)
                ingredients.add(Ingredient(
                    name = ing.getString("name"),
                    quantity = ing.getString("quantity")
                ))
            }
            
            val steps = mutableListOf<String>()
            val stepsArray = obj.getJSONArray("steps")
            for (i in 0 until stepsArray.length()) {
                steps.add(stepsArray.getString(i))
            }
            
            Recipe(
                id = id,
                title = title,
                ingredients = ingredients,
                steps = steps
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
