package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.data.JsonRecipeRepository
import com.example.recipeapp.ui.RecipesApp
import com.example.recipeapp.ui.RecipesViewModel
import com.example.recipeapp.ui.RecipesViewModelFactory
import com.example.recipeapp.ui.recipe.RecipeFlowScreen
import com.example.recipeapp.ui.recipe.RecipeFlowState
import com.example.recipeapp.ui.recipe.RecipeFlowViewModel
import com.example.recipeapp.ui.recipe.ShoppingListFlowViewModel
import com.example.recipeapp.ui.recipe.ShoppingListScreen
import com.example.recipeapp.ui.theme.SimmerTheme

class MainActivity : ComponentActivity() {

    // Simple navigation state: null = recipe list, non-null = recipe flow with given recipeId
    private var currentRecipeId: String? by mutableStateOf(null)
    private var currentShoppingListArgs: ShoppingListArgs? by mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimmerTheme(darkTheme = isSystemInDarkTheme()) {
                val repository = JsonRecipeRepository(this)
                val viewModel: RecipesViewModel = viewModel(factory = RecipesViewModelFactory(repository))

                when {
                    currentShoppingListArgs != null -> {
                        val args = currentShoppingListArgs!!
                        val recipeFlowState = remember { mutableStateOf<RecipeFlowState?>(null) }
                        val shoppingListVm: ShoppingListFlowViewModel = viewModel(
                            factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                                @Suppress("UNCHECKED_CAST")
                                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                                    return ShoppingListFlowViewModel(
                                        repository = repository,
                                        recipeFlowState = recipeFlowState,
                                        recipeId = args.recipeId,
                                        servings = args.servings,
                                        variantId = args.variantId,
                                        methodId = args.methodId,
                                    ) as T
                                }
                            },
                        )
                        ShoppingListScreen(
                            viewModel = shoppingListVm,
                            onBack = {
                                currentShoppingListArgs = null
                                // Update recipe flow state with shopping list selections
                                shoppingListVm.onSave(
                                    newServings = args.servings,
                                    newVariantId = args.variantId,
                                    newMethodId = args.methodId,
                                )
                            },
                            onSave = {
                                shoppingListVm.onSave(
                                    newServings = args.servings,
                                    newVariantId = args.variantId,
                                    newMethodId = args.methodId,
                                )
                                currentShoppingListArgs = null
                            },
                        )
                    }
                    currentRecipeId != null -> {
                        val recipeId = currentRecipeId!!
                        val recipeFlowState = remember { mutableStateOf<RecipeFlowState?>(null) }
                        val recipeFlowVm: RecipeFlowViewModel = viewModel(
                            factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                                @Suppress("UNCHECKED_CAST")
                                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                                    return RecipeFlowViewModel(
                                        repository = repository,
                                        recipeId = recipeId,
                                    ) as T
                                }
                            },
                        )
                        RecipeFlowScreen(
                            recipeId = recipeId,
                            onNavigateToShoppingList = { rid, servings, variantId, methodId ->
                                currentShoppingListArgs = ShoppingListArgs(rid, servings, variantId, methodId)
                            },
                            onBack = { currentRecipeId = null },
                        )
                    }
                    else -> {
                        RecipesApp(
                            viewModel = viewModel,
                            onRecipeSelected = { recipeId ->
                                currentRecipeId = recipeId
                            },
                        )
                    }
                }
            }
        }
    }
}

/** Arguments passed to the shopping list screen. */
data class ShoppingListArgs(
    val recipeId: String,
    val servings: Int,
    val variantId: String?,
    val methodId: String?,
)
