package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.recipeapp.data.JsonRecipeRepository
import com.example.recipeapp.ui.recipe.RecipeNavGraph
import com.example.recipeapp.ui.theme.SimmerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimmerTheme(darkTheme = isSystemInDarkTheme()) {
                val context = LocalContext.current
                val repository = remember { JsonRecipeRepository(context) }
                val navController = rememberNavController()
                RecipeNavGraph(
                    navController = navController,
                    repository = repository,
                )
            }
        }
    }
}
