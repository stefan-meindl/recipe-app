package com.example.recipeapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipeapp.model.Recipe

// Placeholder design: plain Material 3 list and detail. No navigation library yet.

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesApp(viewModel: RecipesViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val selected = state.selected

    BackHandler(enabled = selected != null) { viewModel.clearSelection() }

    Scaffold(
        topBar = { TopAppBar(title = { Text(selected?.title ?: "Recipes") }) },
    ) { padding ->
        if (selected == null) {
            RecipeList(state.recipes, viewModel::select, Modifier.padding(padding))
        } else {
            RecipeDetail(selected, Modifier.padding(padding))
        }
    }
}

@Composable
private fun RecipeList(recipes: List<Recipe>, onSelect: (String) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(recipes, key = { it.id }) { recipe ->
            ListItem(
                headlineContent = { Text(recipe.title) },
                supportingContent = { Text("${recipe.ingredients.size} ingredients, ${recipe.steps.size} steps") },
                modifier = Modifier.clickable { onSelect(recipe.id) },
            )
            HorizontalDivider()
        }
    }
}

@Composable
private fun RecipeDetail(recipe: Recipe, modifier: Modifier = Modifier) {
    LazyColumn(modifier, contentPadding = PaddingValues(16.dp)) {
        item { Text("Ingredients", style = MaterialTheme.typography.titleLarge) }
        items(recipe.ingredients) { ingredient ->
            Text("${ingredient.quantity}  ${ingredient.name}", Modifier.padding(top = 4.dp))
        }
        item { Text("Steps", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 16.dp)) }
        itemsIndexed(recipe.steps) { index, step ->
            Text("${index + 1}. $step", Modifier.padding(top = 4.dp))
        }
    }
}
