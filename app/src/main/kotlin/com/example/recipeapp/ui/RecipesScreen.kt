package com.example.recipeapp.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.recipeapp.domain.parser.IngredientRef
import com.example.recipeapp.domain.parser.PlainText
import com.example.recipeapp.domain.parser.StepSegment
import com.example.recipeapp.domain.parser.resolve
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.ui.components.CookingStep
import com.example.recipeapp.ui.components.IngredientChip
import com.example.recipeapp.ui.components.SegmentedTabs
import com.example.recipeapp.ui.components.ShoppingListItem
import com.example.recipeapp.ui.components.SimmerBadge
import com.example.recipeapp.ui.components.SimmerBadgeVariant
import com.example.recipeapp.ui.components.SimmerButton
import com.example.recipeapp.ui.components.SimmerButtonVariant
import com.example.recipeapp.ui.components.SimmerCard
import com.example.recipeapp.ui.components.SimmerCardVariant
import com.example.recipeapp.ui.components.SimmerIcon
import com.example.recipeapp.ui.components.SimmerTabItem
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesApp(viewModel: RecipesViewModel) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val selected = state.selected
    val c = Simmer.colors

    BackHandler(enabled = selected != null) { viewModel.clearSelection() }

    Scaffold(
        containerColor = c.surfacePage,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        selected?.title ?: "Recipes",
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    if (selected != null) {
                        IconButton(onClick = viewModel::clearSelection) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                contentDescription = "Back",
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = c.surfacePage,
                    titleContentColor = c.textPrimary,
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecipeList(recipes: List<Recipe>, onSelect: (String) -> Unit, modifier: Modifier = Modifier) {
    val c = Simmer.colors
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(SimmerSpace.md),
        verticalArrangement = Arrangement.spacedBy(SimmerSpace.sm),
    ) {
        items(recipes, key = { it.id }) { recipe ->
            val totalTime = remember(recipe) {
                val p = recipe.prepTimeMinutes ?: 0
                val k = recipe.cookTimeMinutes ?: 0
                if (p > 0 || k > 0) "${p + k} min" else null
            }
            SimmerCard(
                modifier = Modifier.fillMaxWidth().clickable { onSelect(recipe.id) },
                padding = SimmerSpace.md,
            ) {
                Text(recipe.title, style = MaterialTheme.typography.headlineSmall, color = c.textPrimary)
                Spacer(Modifier.height(SimmerSpace.xs))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    SimmerBadge(
                        totalTime ?: "${recipe.preparationMethods.size} methods",
                        variant = SimmerBadgeVariant.Primary,
                        icon = Icons.Outlined.Schedule,
                    )
                    recipe.tags.take(3).forEach { tag -> SimmerBadge(tag) }
                }
            }
        }
    }
}

@Composable
private fun StepBodyRenderer(segments: List<StepSegment>) {
    segments.forEach { segment ->
        when (segment) {
            is PlainText -> Text(segment.text, style = MaterialTheme.typography.bodyLarge, color = Simmer.colors.textPrimary)
            is IngredientRef -> IngredientChip(segment)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecipeDetail(
    state: RecipesUiState,
    viewModel: RecipesViewModel,
    modifier: Modifier = Modifier,
) {
    val selected = state.selected ?: return
    val c = Simmer.colors
    val methodIndex = state.selectedMethodIndex
    // Ticked-off ingredients; reset when the recipe or preparation method changes.
    val checked = remember(selected.id, methodIndex) { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
    ) {
        if (selected.images.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(selected.images[0]),
                contentDescription = selected.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f),
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth().padding(horizontal = SimmerSpace.md, vertical = SimmerSpace.md),
            verticalArrangement = Arrangement.spacedBy(SimmerSpace.lg),
        ) {
            Text(selected.title, style = MaterialTheme.typography.headlineLarge, color = c.textPrimary)

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                SimmerBadge(
                    state.totalTimeMinutes?.let { "$it min total" } ?: "No time info",
                    variant = SimmerBadgeVariant.Primary,
                    icon = Icons.Outlined.Schedule,
                )
                selected.tags.forEach { tag -> SimmerBadge(tag) }
            }

            selected.notes?.let { notes ->
                SimmerCard(Modifier.fillMaxWidth(), variant = SimmerCardVariant.CreamSoft, padding = SimmerSpace.md) {
                    Text("Notes", style = MaterialTheme.typography.titleMedium, color = c.textOnWarm)
                    Spacer(Modifier.height(4.dp))
                    Text(notes, style = MaterialTheme.typography.bodyMedium, color = c.textSecondary)
                }
            }

            if (selected.preparationMethods.size > 1) {
                SegmentedTabs(
                    items = selected.preparationMethods.map { SimmerTabItem(it.id, it.displayName) },
                    value = methodIndex.toString(),
                    onChange = { viewModel.selectMethod(it.toInt()) },
                    modifier = Modifier.fillMaxWidth(),
                    fullWidth = true,
                )
            }

            // Servings adjuster
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(SimmerSpace.sm, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SimmerButton("Fewer", onClick = viewModel::decrementServings, variant = SimmerButtonVariant.Secondary, iconLeft = Icons.Outlined.Remove)
                Text("${state.effectiveServings} servings", style = MaterialTheme.typography.titleMedium, color = c.textPrimary)
                SimmerButton("More", onClick = viewModel::incrementServings, variant = SimmerButtonVariant.Secondary, iconLeft = Icons.Outlined.Add)
            }

            // Ingredients (scaled)
            Text("Ingredients", style = MaterialTheme.typography.headlineSmall, color = c.textPrimary)
            SimmerCard(Modifier.fillMaxWidth(), padding = 0.dp) {
                state.scaledIngredients.forEachIndexed { index, ingredient ->
                    val note = listOfNotNull(
                        ingredient.alternatives?.takeIf { it.isNotEmpty() }?.joinToString(", ", prefix = "Or: "),
                        ingredient.tips?.takeIf { it.isNotEmpty() }?.joinToString(" "),
                    ).joinToString(" · ").ifBlank { null }
                    ShoppingListItem(
                        name = ingredient.name,
                        amount = ingredient.quantity,
                        note = note,
                        checked = checked[ingredient.name] == true,
                        onToggle = { checked[ingredient.name] = it },
                        divider = index > 0,
                    )
                }
            }

            // Cooking steps
            Text("Steps", style = MaterialTheme.typography.headlineSmall, color = c.textPrimary)
            state.steps.forEachIndexed { index, step ->
                key(selected.id, methodIndex, index) {
                    CookingStep(
                        title = "",
                        number = index + 1,
                        durationSeconds = (step.durationMinutes ?: 0) * 60,
                    ) {
                        val segments = remember(step.description, state.scaledIngredients) {
                            resolve(step.description, state.scaledIngredients)
                        }
                        StepBodyRenderer(segments)
                        step.tips?.forEach { tip ->
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                                SimmerIcon(Icons.Outlined.Lightbulb, c.textAccent, size = 18.dp)
                                Text(tip, style = MaterialTheme.typography.bodyMedium, color = c.textSecondary)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(SimmerSpace.xxl))
        }
    }
}
