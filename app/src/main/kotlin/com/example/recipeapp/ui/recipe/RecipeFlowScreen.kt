package com.example.recipeapp.ui.recipe

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.material3.RadioButton
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
import com.example.recipeapp.model.PreparationMethod
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.Step
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
import com.example.recipeapp.ui.theme.SimmerType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeFlowScreen(
    recipeId: String,
    onNavigateToShoppingList: (recipeId: String, servings: Int, variantId: String?, methodId: String?) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel: RecipeFlowViewModel = androidx.lifecycle.viewmodel.compose.hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val c = Simmer.colors

    BackHandler(enabled = state != null) { onBack() }

    Scaffold(
        containerColor = c.surfacePage,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        state?.recipe?.title ?: "Recipe",
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = c.surfacePage,
                    titleContentColor = c.textPrimary,
                ),
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        SimmerIcon(Icons.AutoMirrored.Outlined.ArrowBack, c.textPrimary, size = 20.dp)
                    }
                },
            )
        },
        content = { padding ->
            state?.let { recipeState ->
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(padding),
                ) {
                    when (recipeState.stage) {
                        is RecipeFlowStage.Overview -> RecipeOverviewGrid(
                            recipe = recipeState.recipe,
                            onSelect = { viewModel.navigateToStage(RecipeFlowStage.Selection) },
                        )
                        is RecipeFlowStage.Selection -> RecipeSelectionPanel(
                            state = recipeState,
                            onServingsChanged = viewModel::onServingsChanged,
                            onVariantSelected = viewModel::onVariantSelected,
                            onMethodSelected = viewModel::onMethodSelected,
                            onNavigateToShoppingList = {
                                onNavigateToShoppingList(
                                    recipeId = recipeState.recipe.id,
                                    servings = recipeState.effectiveServings,
                                    variantId = recipeState.selectedVariant?.id,
                                    methodId = recipeState.selectedMethod?.id,
                                )
                            },
                            onContinueToCooking = {
                                viewModel.navigateToStage(RecipeFlowStage.Cooking)
                            },
                        )
                        is RecipeFlowStage.Cooking -> RecipeCookingPanel(
                            state = recipeState,
                            onBack = onBack,
                        )
                    }
                }
            }
        },
    )
}

/** Stage 1: Overview — grid of recipe cards (for single recipe, shows the recipe itself). */
@Composable
private fun RecipeOverviewGrid(
    recipe: Recipe,
    onSelect: () -> Unit,
) {
    val c = Simmer.colors

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(SimmerSpace.lg),
        verticalArrangement = Arrangement.spacedBy(SimmerSpace.lg),
    ) {
        if (recipe.images.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(recipe.images[0]),
                contentDescription = recipe.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().aspectRatio(16f / 9f),
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(SimmerSpace.md),
        ) {
            Text(recipe.title, style = MaterialTheme.typography.headlineLarge, color = c.textPrimary)

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                recipe.tags.forEach { tag -> SimmerBadge(tag) }
            }

            recipe.notes?.let { notes ->
                SimmerCard(Modifier.fillMaxWidth(), variant = SimmerCardVariant.CreamSoft, padding = SimmerSpace.md) {
                    Text("Notes", style = MaterialTheme.typography.titleMedium, color = c.textOnWarm)
                    Spacer(Modifier.height(4.dp))
                    Text(notes, style = MaterialTheme.typography.bodyMedium, color = c.textSecondary)
                }
            }

            SimmerButton(
                "Select Recipe",
                onClick = onSelect,
                variant = SimmerButtonVariant.Primary,
                fullWidth = true,
            )
        }
    }
}

/** Stage 2: Selection — servings, variant/method selection, shopping list jump. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecipeSelectionPanel(
    state: RecipeFlowState,
    onServingsChanged: (Int) -> Unit,
    onVariantSelected: (PreparationMethod) -> Unit,
    onMethodSelected: (PreparationMethod) -> Unit,
    onNavigateToShoppingList: () -> Unit,
    onContinueToCooking: () -> Unit,
) {
    val c = Simmer.colors

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = SimmerSpace.md, vertical = SimmerSpace.md),
        verticalArrangement = Arrangement.spacedBy(SimmerSpace.lg),
    ) {
        Text(state.recipe.title, style = MaterialTheme.typography.headlineLarge, color = c.textPrimary)

        // Time and tags
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            SimmerBadge(
                state.totalTimeMinutes?.let { "$it min total" } ?: "No time info",
                variant = SimmerBadgeVariant.Primary,
                icon = Icons.Outlined.Schedule,
            )
            state.recipe.tags.forEach { tag -> SimmerBadge(tag) }
        }

        // Variant selector (if multiple preparation methods)
        if (state.variants.size > 1) {
            Text("Preparation Method", style = MaterialTheme.typography.titleMedium, color = c.textPrimary)
            SimmerCard(Modifier.fillMaxWidth(), padding = SimmerSpace.md) {
                state.variants.forEach { variant ->
                    val isSelected = state.selectedVariant?.id == variant.id
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onVariantSelected(variant) }
                            .padding(vertical = SimmerSpace.sm),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { onVariantSelected(variant) },
                        )
                        Text(variant.displayName, style = MaterialTheme.typography.bodyLarge, color = c.textPrimary)
                    }
                }
            }
        }

        // Servings adjuster
        Text("Servings", style = MaterialTheme.typography.titleMedium, color = c.textPrimary)
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SimmerSpace.sm, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SimmerButton(
                "Fewer",
                onClick = { onServingsChanged(-1) },
                variant = SimmerButtonVariant.Secondary,
                iconLeft = Icons.Outlined.Remove,
            )
            Text("${state.effectiveServings} servings", style = MaterialTheme.typography.titleMedium, color = c.textPrimary)
            SimmerButton(
                "More",
                onClick = { onServingsChanged(1) },
                variant = SimmerButtonVariant.Secondary,
                iconLeft = Icons.Outlined.Add,
            )
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
                    checked = false,
                    onToggle = {},
                    divider = index > 0,
                )
            }
        }

        // Shopping list and continue buttons
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SimmerSpace.sm),
        ) {
            SimmerButton(
                "Shopping List",
                onClick = onNavigateToShoppingList,
                variant = SimmerButtonVariant.Secondary,
                fullWidth = true,
            )
            SimmerButton(
                "Continue to Cooking",
                onClick = onContinueToCooking,
                variant = SimmerButtonVariant.Primary,
                fullWidth = true,
            )
        }
    }
}

/** Stage 3: Cooking — prep and cook steps in separate LazyColumns. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RecipeCookingPanel(
    state: RecipeFlowState,
    onBack: () -> Unit,
) {
    val c = Simmer.colors

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = SimmerSpace.md, vertical = SimmerSpace.md),
        verticalArrangement = Arrangement.spacedBy(SimmerSpace.lg),
    ) {
        Text(state.recipe.title, style = MaterialTheme.typography.headlineLarge, color = c.textPrimary)

        // Pre-compute phase headings and step labels
        val stepMeta = remember(state.steps) {
            val counters = mutableMapOf<String, Int>()
            var lastPhase = ""
            state.steps.map { step ->
                val phaseKey = step.phase.uppercase().ifBlank { "STEP" }
                val phaseNum = (counters[phaseKey] ?: 0) + 1
                counters[phaseKey] = phaseNum
                val phaseHeading = if (step.phase != lastPhase) {
                    lastPhase = step.phase
                    when (step.phase) {
                        "prep" -> "Preparation"
                        "cook" -> "Cooking"
                        else -> step.phase.replaceFirstChar { it.titlecase() }
                    }
                } else null
                CookingStepData(
                    step = step,
                    phaseHeading = phaseHeading,
                    stepLabel = "$phaseKey $phaseNum",
                )
            }
        }

        // Prep steps
        if (state.prepSteps.isNotEmpty()) {
            Text("Preparation", style = MaterialTheme.typography.headlineMedium, color = c.textPrimary)
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(SimmerSpace.md),
            ) {
                items(stepMeta.filter { it.step.phase.equals("prep", ignoreCase = true) }) { data ->
                    CookingStep(
                        title = data.step.title,
                        stepLabel = data.stepLabel,
                        durationSeconds = (data.step.durationMinutes ?: 0) * 60,
                        stepId = data.step.id,
                        active = false,
                        onTimerComplete = {},
                    ) {
                        val segments = remember(data.step.description) {
                            resolve(data.step.description, state.scaledIngredients)
                        }
                        StepBodyRenderer(segments)
                        data.step.tips?.forEach { tip ->
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                                SimmerIcon(Icons.Outlined.Lightbulb, c.textAccent, size = 18.dp)
                                Text(tip, style = MaterialTheme.typography.bodyMedium, color = c.textSecondary)
                            }
                        }
                    }
                }
            }
        }

        // Cook steps
        if (state.cookSteps.isNotEmpty()) {
            Text("Cooking", style = MaterialTheme.typography.headlineMedium, color = c.textPrimary)
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(SimmerSpace.md),
            ) {
                items(stepMeta.filter { it.step.phase.equals("cook", ignoreCase = true) }) { data ->
                    CookingStep(
                        title = data.step.title,
                        stepLabel = data.stepLabel,
                        durationSeconds = (data.step.durationMinutes ?: 0) * 60,
                        stepId = data.step.id,
                        active = false,
                        onTimerComplete = {},
                    ) {
                        val segments = remember(data.step.description) {
                            resolve(data.step.description, state.scaledIngredients)
                        }
                        StepBodyRenderer(segments)
                        data.step.tips?.forEach { tip ->
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                                SimmerIcon(Icons.Outlined.Lightbulb, c.textAccent, size = 18.dp)
                                Text(tip, style = MaterialTheme.typography.bodyMedium, color = c.textSecondary)
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(SimmerSpace.xxl))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StepBodyRenderer(segments: List<StepSegment>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        segments.forEach { segment ->
            when (segment) {
                is PlainText -> Text(segment.text, style = MaterialTheme.typography.bodyLarge, color = Simmer.colors.textPrimary)
                is IngredientRef -> IngredientChip(segment)
            }
        }
    }
}
