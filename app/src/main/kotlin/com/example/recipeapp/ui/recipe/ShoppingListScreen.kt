package com.example.recipeapp.ui.recipe

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.recipeapp.ui.components.ShoppingListItem
import com.example.recipeapp.ui.components.SimmerButton
import com.example.recipeapp.ui.components.SimmerButtonVariant
import com.example.recipeapp.ui.components.SimmerCard
import com.example.recipeapp.ui.theme.Simmer
import com.example.recipeapp.ui.theme.SimmerSpace
import com.example.recipeapp.ui.theme.SimmerType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    viewModel: ShoppingListFlowViewModel,
    onBack: () -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val c = Simmer.colors

    // Track checked items locally
    val checked = remember { mutableStateMapOf<String, Boolean>() }

    BackHandler { onBack() }

    Scaffold(
        containerColor = c.surfacePage,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Shopping List",
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
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Back", tint = c.textPrimary)
                    }
                },
            )
        },
        content = { padding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = SimmerSpace.md, vertical = SimmerSpace.md),
                verticalArrangement = Arrangement.spacedBy(SimmerSpace.lg),
            ) {
                if (state.loading) {
                    Text("Loading shopping list...", style = MaterialTheme.typography.bodyLarge, color = c.textSecondary)
                } else {
                    // Servings info
                    Text(
                        "For ${state.servings} serving${if (state.servings != 1) "s" else ""}",
                        style = MaterialTheme.typography.titleMedium,
                        color = c.textSecondary,
                    )

                    // Shopping items
                    SimmerCard(Modifier.fillMaxWidth(), padding = 0.dp) {
                        state.items.forEachIndexed { index, item ->
                            val isChecked = checked[item.name] == true
                            ShoppingListItem(
                                name = item.name,
                                amount = item.amount,
                                note = item.note,
                                checked = isChecked,
                                onToggle = { checked[item.name] = it },
                                divider = index > 0,
                            )
                        }
                    }

                    // Save button
                    SimmerButton(
                        "Save & Continue",
                        onClick = onSave,
                        variant = SimmerButtonVariant.Primary,
                        fullWidth = true,
                        iconRight = Icons.Outlined.Check,
                    )
                }
            }
        },
    )
}
