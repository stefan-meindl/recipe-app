package com.example.recipeapp.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavBackStackEntry
import com.example.recipeapp.data.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ShoppingListUiState(
    val items: List<ShoppingListItemData> = emptyList(),
    val recipeId: String = "",
    val servings: Int = 1,
    val variantId: String? = null,
    val methodId: String? = null,
    val loading: Boolean = true,
)

data class ShoppingListItemData(
    val name: String,
    val amount: String,
    val note: String? = null,
)

class ShoppingListFlowViewModel(
    private val repository: RecipeRepository,
    private val navBackStackEntry: NavBackStackEntry,
    private val recipeFlowState: MutableStateFlow<RecipeFlowState?>,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ShoppingListUiState())
    val uiState: StateFlow<ShoppingListUiState> = _uiState.asStateFlow()

    init {
        // Parse args from NavBackStackEntry
        val args = navBackStackEntry.arguments
        val recipeId = args?.getString("recipeId") ?: ""
        val servings = args?.getString("servings")?.toIntOrNull() ?: 1
        val variantId = args?.getString("variantId")
        val methodId = args?.getString("methodId")

        viewModelScope.launch {
            val recipes = repository.getRecipes()
            val recipe = recipes.find { it.id == recipeId }
            if (recipe != null) {
                // Find the selected variant/method
                val method = recipe.preparationMethods.find { it.id == variantId }
                    ?: recipe.preparationMethods.find { it.id == methodId }
                    ?: recipe.preparationMethods.firstOrNull()

                if (method != null) {
                    val baseServings = recipe.servings
                    val multiplier = servings.toDouble() / baseServings
                    val scaledIngredients = method.ingredients.map { it.scaledBy(multiplier) }

                    _uiState.value = ShoppingListUiState(
                        items = scaledIngredients.map { ingredient ->
                            val note = listOfNotNull(
                                ingredient.alternatives?.takeIf { it.isNotEmpty() }?.joinToString(", ", prefix = "Or: "),
                                ingredient.tips?.takeIf { it.isNotEmpty() }?.joinToString(" "),
                            ).joinToString(" · ").ifBlank { null }
                            ShoppingListItemData(
                                name = ingredient.name,
                                amount = ingredient.quantity,
                                note = note,
                            )
                        },
                        recipeId = recipe.id,
                        servings = servings,
                        variantId = variantId,
                        methodId = methodId,
                        loading = false,
                    )
                }
            }
        }
    }

    /**
     * Bridges updated state back to the parent RecipeFlowViewModel.
     * Called on back press or save to preserve user selections.
     */
    fun onSave(newServings: Int, newVariantId: String?, newMethodId: String?) {
        _uiState.update { it.copy(servings = newServings) }

        // Bridge state back to parent via the shared MutableStateFlow
        val parentState = recipeFlowState.value
        if (parentState != null) {
            recipeFlowState.value = parentState.copy(
                servings = newServings,
                selectedVariant = parentState.variants.find { it.id == newVariantId },
                selectedMethod = parentState.variants.find { it.id == newMethodId },
            )
        }
    }
}
