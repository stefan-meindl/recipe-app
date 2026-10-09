package com.example.recipeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Recipe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RecipesUiState(
    val recipes: List<Recipe> = emptyList(),
    val selected: Recipe? = null,
    val loading: Boolean = true,
)

class RecipesViewModel(private val repository: RecipeRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipesUiState())
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val recipes = repository.getRecipes()
            _uiState.update { it.copy(recipes = recipes, loading = false) }
        }
    }

    fun select(id: String) {
        _uiState.update { state -> state.copy(selected = state.recipes.firstOrNull { it.id == id }) }
    }

    fun clearSelection() {
        _uiState.update { it.copy(selected = null) }
    }
}
