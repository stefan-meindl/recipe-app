package com.example.recipeapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.recipeapp.model.Recipe

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesApp(viewModel: RecipesViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val selected = state.selected

    BackHandler(enabled = selected != null) { viewModel.clearSelection() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selected?.title ?: "Recipes") },
                navigationIcon = {
                    if (selected != null) {
                        IconButton(onClick = viewModel::clearSelection) {
                            Icon(Icons.Default.ArrowBack, "Back")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            )
        },
    ) { padding ->
        if (selected == null) {
            RecipeList(state.recipes, viewModel::select, Modifier.padding(padding))
        } else {
            RecipeDetail(state, viewModel, Modifier.padding(padding))
        }
    }
}

@Composable
private fun RecipeList(recipes: List<Recipe>, onSelect: (String) -> Unit, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(recipes, key = { it.id }) { recipe ->
            val totalTime = remember(recipe) {
                val p = recipe.prepTimeMinutes ?: 0
                val c = recipe.cookTimeMinutes ?: 0
                if (p > 0 || c > 0) "${p + c} min" else null
            }
            ListItem(
                headlineContent = { Text(recipe.title) },
                supportingContent = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Restaurant, null, modifier = Modifier.padding(end = 4.dp))
                            Text(totalTime ?: "${recipe.preparationMethods.size} methods")
                        }
                        if (recipe.tags.isNotEmpty()) {
                            Text(
                                recipe.tags.take(3).joinToString(", "),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        }
                    }
                },
                modifier = Modifier.clickable { onSelect(recipe.id) },
            )
            Divider()
        }
    }
}

@Composable
private fun RecipeDetail(
    state: RecipesUiState,
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier,
) {
    val selected = state.selected ?: return
    val scrollState = rememberScrollState()

    LazyColumn(
        modifier = modifier.verticalScroll(scrollState),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Hero image
        if (selected.images.isNotEmpty()) {
            item {
                Box(Modifier.fillMaxWidth()) {
                    Image(
                        painter = rememberAsyncImagePainter(selected.images[0]),
                        contentDescription = selected.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                    )
                }
            }
        }

        // Title and metadata
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(selected.title, style = MaterialTheme.typography.headlineMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Event, null, modifier = Modifier.padding(end = 4.dp))
                    val totalTime = state.totalTimeMinutes
                    Text(totalTime?.let { "${it} min total" } ?: "No time info")
                }
                if (selected.tags.isNotEmpty()) {
                    Row(modifier = Modifier.padding(top = 4.dp)) {
                        selected.tags.forEach { tag ->
                            Chips(tag, Modifier.padding(end = 4.dp))
                        }
                    }
                }
            }
        }

        // Notes
        if (selected.notes != null) {
            item {
                ListItem(
                    headlineContent = { Text("Notes", style = MaterialTheme.typography.titleMedium) },
                    supportingContent = { Text(selected.notes) },
                    leadingContent = { Icon(Icons.Default.Notes, null) },
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
            }
        }

        // Preparation method selector
        if (selected.preparationMethods.size > 1) {
            item {
                val methods = selected.preparationMethods
                val selectedIndex = state.selectedMethodIndex
                TabRow(
                    selectedTabIndex = selectedIndex,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    methods.forEachIndexed { index, method ->
                        Tab(
                            selected = index == selectedIndex,
                            onClick = { viewModel.selectMethod(index) },
                            text = { Text(method.displayName) },
                        )
                    }
                }
            }
        }

        // Servings adjuster
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = viewModel::decrementServings) {
                    Icon(Icons.Default.ArrowDownward, "Decrease servings")
                }
                Text(
                    "Servings: ${state.effectiveServings}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                IconButton(onClick = viewModel::incrementServings) {
                    Icon(Icons.Default.ArrowUpward, "Increase servings")
                }
            }
        }

        // Shopping list (scaled ingredients)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Shopping List", style = MaterialTheme.typography.titleLarge)
                state.scaledIngredients.forEach { ingredient ->
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ShoppingCart, null, modifier = Modifier.padding(end = 8.dp))
                            Text(
                                ingredient.name,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                        Text(
                            ingredient.quantity,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                        if (ingredient.alternatives?.isNotEmpty() == true) {
                            Text(
                                "Alternatives: ${ingredient.alternatives.joinToString(", ")}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.tertiary,
                                textDecoration = TextDecoration.Underline,
                            )
                        }
                        if (ingredient.tips?.isNotEmpty() == true) {
                            ingredient.tips.forEach { tip ->
                                Text(
                                    "💡 $tip",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary,
                                )
                            }
                        }
                    }
                }
            }
        }

        // Cooking steps
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Steps", style = MaterialTheme.typography.titleLarge)
                state.steps.forEachIndexed { index, step ->
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Row(verticalAlignment = Alignment.Top) {
                            Text(
                                "${index + 1}. ",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            )
                            Text(step.description, style = MaterialTheme.typography.bodyLarge)
                        }
                        if (step.durationMinutes != null) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Event, null, modifier = Modifier.padding(end = 4.dp))
                                Text("${step.durationMinutes} min", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        if (step.tips?.isNotEmpty() == true) {
                            step.tips.forEach { tip ->
                                Text(
                                    "💡 $tip",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(start = 24.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Chips(text: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.small,
        modifier = modifier,
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
    }
}
