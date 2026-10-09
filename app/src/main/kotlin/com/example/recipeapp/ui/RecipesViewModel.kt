package com.example.recipeapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.PreparationMethod
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.Step
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RecipesUiState(
    val recipes: List<Recipe> = emptyList(),
    val selected: Recipe? = null,
    val selectedMethodIndex: Int = 0,
    val userServings: Int = 0, // 0 = use recipe's default servings
    val loading: Boolean = true,
) {
    val effectiveServings: Int
        get() = if (userServings > 0) userServings else selected?.servings ?: 1

    val selectedMethod: PreparationMethod?
        get() = selected?.preparationMethods?.getOrNull(selectedMethodIndex)

    val scaledIngredients: List<Ingredient>
        get() {
            val method = selectedMethod ?: return emptyList()
            val baseServings = selected?.servings ?: 1
            val multiplier = effectiveServings.toDouble() / baseServings
            return method.ingredients.map { it.scaledBy(multiplier) }
        }

    val steps: List<Step>
        get() = selectedMethod?.steps ?: emptyList()

    val totalTimeMinutes: Int?
        get() {
            val prep = selected?.prepTimeMinutes ?: 0
            val cook = selected?.cookTimeMinutes ?: 0
            return if (prep > 0 || cook > 0) prep + cook else null
        }
}

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
        _uiState.update { state ->
            val recipe = state.recipes.firstOrNull { it.id == id } ?: return@update state
            state.copy(
                selected = recipe,
                selectedMethodIndex = 0,
                userServings = 0,
            )
        }
    }

    fun clearSelection() {
        _uiState.update { it.copy(selected = null) }
    }

    fun selectMethod(index: Int) {
        _uiState.update { it.copy(selectedMethodIndex = index) }
    }

    fun setServings(servings: Int) {
        _uiState.update { it.copy(userServings = servings) }
    }

    fun incrementServings() {
        _uiState.update { state ->
            val current = state.effectiveServings
            val max = (state.selected?.servings ?: 1) * 4 // cap at 4x base
            state.copy(userServings = (current + 1).coerceAtMost(max))
        }
    }

    fun decrementServings() {
        _uiState.update { state ->
            val current = state.effectiveServings
            state.copy(userServings = (current - 1).coerceAtLeast(1))
        }
    }
}
