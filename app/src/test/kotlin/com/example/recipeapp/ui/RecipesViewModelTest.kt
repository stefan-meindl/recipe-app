package com.example.recipeapp.ui

import com.example.recipeapp.data.RecipeRepository
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.PreparationMethod
import com.example.recipeapp.model.Recipe
import com.example.recipeapp.model.Step
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RecipesViewModelTest {

    private val toast = Recipe(
        id = "toast",
        title = "Toast",
        preparationMethods = listOf(
            PreparationMethod(
                id = "basic",
                displayName = "Basic",
                ingredients = listOf(
                    Ingredient("Bread", "2 slices"),
                ),
                steps = listOf(
                    Step("Toast the bread."),
                ),
            ),
        ),
    )

    private class FakeRepository(private val recipes: List<Recipe>) : RecipeRepository {
        override suspend fun getRecipes() = recipes
    }

    @Before
    fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun loadsRecipesFromRepository() {
        val viewModel = RecipesViewModel(FakeRepository(listOf(toast)))

        assertEquals(listOf(toast), viewModel.uiState.value.recipes)
        assertFalse(viewModel.uiState.value.loading)
    }

    @Test
    fun selectShowsRecipeAndClearSelectionReturnsToList() {
        val viewModel = RecipesViewModel(FakeRepository(listOf(toast)))

        viewModel.select("toast")
        assertEquals(toast, viewModel.uiState.value.selected)

        viewModel.clearSelection()
        assertNull(viewModel.uiState.value.selected)
    }

    @Test
    fun selectingUnknownIdSelectsNothing() {
        val viewModel = RecipesViewModel(FakeRepository(listOf(toast)))

        viewModel.select("does-not-exist")

        assertNull(viewModel.uiState.value.selected)
    }
}
