package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipeapp.data.JsonRecipeRepository
import com.example.recipeapp.ui.RecipesApp
import com.example.recipeapp.ui.RecipesViewModel
import com.example.recipeapp.ui.RecipesViewModelFactory
import com.example.recipeapp.ui.theme.SimmerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimmerTheme(darkTheme = isSystemInDarkTheme()) {
                val repository = JsonRecipeRepository(this)
                val viewModel: RecipesViewModel = viewModel(factory = RecipesViewModelFactory(repository))
                RecipesApp(viewModel)
            }
        }
    }
}
