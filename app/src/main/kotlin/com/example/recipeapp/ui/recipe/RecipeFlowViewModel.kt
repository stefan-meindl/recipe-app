package com.example.recipeapp.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.PreparationMethod
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.Step
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecipeFlowViewModel(
    private val repository: RecipeRepository,
    private val recipeId: String,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RecipeFlowState?>(null)
    val uiState: StateFlow<RecipeFlowState?> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val recipes = repository.getRecipes()
            val recipe = recipes.find { it.id == recipeId }
            if (recipe != null) {
                _uiState.value = RecipeFlowState.fromRecipe(recipe)
            }
        }
    }

    /** Navigate to a specific stage. */
    fun navigateToStage(stage: RecipeFlowStage) {
        _uiState.update { state ->
            state?.copy(stage = stage) ?: state
        }
    }

    /** Change servings by a delta, clamped to [1, 20]. */
    fun onServingsChanged(delta: Int) {
        _uiState.update { state ->
            state?.let { s ->
                val newServings = (s.servings + delta).coerceIn(1, 20)
                s.copy(servings = newServings)
            } ?: state
        }
    }

    /** Select a preparation method variant. */
    fun onVariantSelected(variant: PreparationMethod) {
        _uiState.update { state ->
            state?.copy(selectedVariant = variant, selectedMethod = variant) ?: state
        }
    }

    /** Select a cooking method. */
    fun onMethodSelected(method: PreparationMethod) {
        _uiState.update { state ->
            state?.copy(selectedMethod = method) ?: state
        }
    }

    /** Complete a step by ID. */
    fun completeStep(stepId: String) {
        // Not persisted in RecipeFlowState — handled in Cooking panel via local state
    }
}
