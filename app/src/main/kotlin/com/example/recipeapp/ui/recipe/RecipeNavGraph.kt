package com.example.recipeapp.ui.recipe

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.ui.RecipesApp

/**
 * Sets up the navigation graph for the recipe flow.
 * Uses primitive path segments for shopping list navigation.
 */
@androidx.compose.runtime.Composable
fun RecipeNavGraph(
    navController: NavHostController,
    repository: RecipeRepository,
) {
    NavHost(
        navController = navController,
        startDestination = "recipes",
    ) {
        // Main recipes list screen
        composable("recipes") {
            val recipesViewModel: com.example.recipeapp.ui.RecipesViewModel =
                viewModel(factory = com.example.recipeapp.ui.RecipesViewModelFactory(repository))

            RecipesApp(
                viewModel = recipesViewModel,
                onRecipeSelected = { recipeId ->
                    navController.navigate("recipe/$recipeId")
                },
            )
        }

        // Recipe flow: Overview → Selection → Cooking
        composable(
            route = "recipe/{recipeId}",
            arguments = listOf(
                navArgument("recipeId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
            val recipeFlowViewModel: RecipeFlowViewModel =
                viewModel(
                    factory = RecipeFlowViewModel.Factory(repository, recipeId),
                )

            RecipeFlowScreen(
                recipeId = recipeId,
                repository = repository,
                onNavigateToShoppingList = { _, servings, variantId, methodId ->
                    navController.navigate(
                        "shoppingList/$recipeId/$servings/$variantId/$methodId"
                    )
                },
                onBack = { navController.popBackStack() },
            )
        }

        // Shopping list detour with state bridging
        composable(
            route = "shoppingList/{recipeId}/{servings}/{variantId}/{methodId}",
            arguments = listOf(
                navArgument("recipeId") { type = NavType.StringType },
                navArgument("servings") { type = NavType.StringType; defaultValue = "1" },
                navArgument("variantId") { type = NavType.StringType; nullable = true },
                navArgument("methodId") { type = NavType.StringType; nullable = true },
            ),
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
            // Get the parent RecipeFlowViewModel's mutable state flow for bridging
            val recipeFlowViewModel: RecipeFlowViewModel =
                viewModel(
                    factory = RecipeFlowViewModel.Factory(repository, recipeId),
                )

            ShoppingListScreen(
                viewModel = ShoppingListFlowViewModel(
                    repository = repository,
                    navBackStackEntry = backStackEntry,
                    recipeFlowState = recipeFlowViewModel.mutableUiState,
                ),
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() },
            )
        }
    }
}
