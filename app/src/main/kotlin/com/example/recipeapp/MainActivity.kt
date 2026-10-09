package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.recipeapp.data.JsonRecipeRepository
import com.example.recipeapp.ui.RecipesApp
import com.example.recipeapp.ui.RecipesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val viewModel: RecipesViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer { RecipesViewModel(JsonRecipeRepository(this@MainActivity)) }
                    },
                )
                RecipesApp(viewModel)
            }
        }
    }
}
