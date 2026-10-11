package com.example.recipeapp

import com.example.recipeapp.data.RecipeRepository

/**
 * Application-level dependency holder.
 * Set in [MainActivity] so screens can resolve repositories without DI.
 */
object AppDeps {
    @Volatile
    var repository: RecipeRepository? = null
}
