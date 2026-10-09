package com.example.recipeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.data.RecipeRepository

/** Builds [RecipesViewModel] with a repository instance. */
class RecipesViewModelFactory(
    private val repository: RecipeRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RecipesViewModel(repository) as T
    }
}
