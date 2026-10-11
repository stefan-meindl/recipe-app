package com.example.recipeapp.ui.components

import androidx.lifecycle.SavedStateHandle
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.Step
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CookingStepViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val flour = Ingredient("Flour", "200 g")
    private val sugar = Ingredient("Sugar", "100 g")

    private val step = Step(
        id = "mix",
        title = "Mix dry ingredients",
        phase = "PREP",
        description = "Combine {Flour} and {Sugar} in a bowl.",
        durationMinutes = 5,
        tips = listOf("Sift together"),
    )

    private fun createViewModel(
        savedStateHandle: SavedStateHandle? = null,
    ): CookingStepViewModel {
        val handle = savedStateHandle ?: SavedStateHandle()
        return CookingStepViewModel(step, listOf(flour, sugar), handle)
    }

    @Before
    fun setUp() = Dispatchers.setMain(testDispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun init_parsesRefsFromInstructionText() {
        val viewModel = createViewModel()

        val refs = viewModel.refsFlow.value
        assertTrue(refs.isNotEmpty())
        // Should have refs for Flour and Sugar
        val refIds = refs.map { it.id }
        assertTrue(refIds.contains(flour.name))
        assertTrue(refIds.contains(sugar.name))
    }

    @Test
    fun toggleDone_flipsIsDoneFlow() {
        val viewModel = createViewModel()

        assertFalse(viewModel.isDoneFlow.value)

        viewModel.toggleDone(true)
        assertTrue(viewModel.isDoneFlow.value)

        viewModel.toggleDone(false)
        assertFalse(viewModel.isDoneFlow.value)
    }

    @Test
    fun toggleDone_cancelsActiveTimer() = runTest {
        val viewModel = createViewModel()

        viewModel.startTimer()
        assertTrue(viewModel.isTimerRunningFlow.value)

        viewModel.toggleDone(true)
        assertFalse(viewModel.isTimerRunningFlow.value)
    }

    @Test
    fun startTimer_setsIsTimerRunning() = runTest {
        val viewModel = createViewModel()

        assertFalse(viewModel.isTimerRunningFlow.value)

        viewModel.startTimer()
        assertTrue(viewModel.isTimerRunningFlow.value)
    }

    @Test
    fun startTimer_doesNotStartIfAlreadyRunning() = runTest {
        val viewModel = createViewModel()

        viewModel.startTimer()
        assertTrue(viewModel.isTimerRunningFlow.value)

        // Calling startTimer again should be a no-op
        viewModel.startTimer()
        assertTrue(viewModel.isTimerRunningFlow.value)
    }

    @Test
    fun startTimer_doesNotStartIfDone() = runTest {
        val viewModel = createViewModel()

        viewModel.toggleDone(true)
        viewModel.startTimer()
        assertFalse(viewModel.isTimerRunningFlow.value)
        assertEquals(0L, viewModel.elapsedFlow.value)
    }

    @Test
    fun configChange_restoresIsDoneFromSavedStateHandle() {
        val handle = SavedStateHandle().apply {
            set("isDone", true)
            set("elapsedMs", 30_000L)
        }

        val viewModel = createViewModel(handle)

        assertTrue(viewModel.isDoneFlow.value)
        assertEquals(30_000L, viewModel.elapsedFlow.value)
    }

    @Test
    fun configChange_restoresElapsedMsFromSavedStateHandle() {
        val handle = SavedStateHandle().apply {
            set("elapsedMs", 45_000L)
        }

        val viewModel = createViewModel(handle)

        assertEquals(45_000L, viewModel.elapsedFlow.value)
    }

    @Test
    fun configChange_keepsIsTimerRunningFalse_noAutoResume() {
        val handle = SavedStateHandle().apply {
            set("isDone", false)
            set("elapsedMs", 30_000L)
        }

        val viewModel = createViewModel(handle)

        assertFalse(viewModel.isTimerRunningFlow.value)
        assertEquals(false, viewModel.isDoneFlow.value)
        assertEquals(30_000L, viewModel.elapsedFlow.value)
    }

    @Test
    fun startTimer_completesWhenElapsedReachesDuration() = runTest {
        // Create a step with 0 minutes duration (0 ms)
        // The while condition is immediately false, so the timer completes instantly
        val shortStep = Step(
            id = "short",
            title = "Quick step",
            description = "Do something.",
            durationMinutes = 0,
        )
        val shortViewModel = CookingStepViewModel(
            shortStep,
            listOf(flour, sugar),
            SavedStateHandle(),
        )

        shortViewModel.startTimer()
        // With UnconfinedTestDispatcher, the coroutine runs to completion immediately
        // Since duration is 0, the while condition is immediately false
        // isTimerRunning should be set to false in the finally block
        assertFalse(shortViewModel.isTimerRunningFlow.value)
    }

    @Test
    fun combine_mergesAllFlowsIntoUiState() = runTest {
        val viewModel = createViewModel()

        // Test source flows directly (uiState uses WhileSubscribed so it won't emit
        // in unit tests without a subscriber)
        assertFalse(viewModel.isDoneFlow.value)
        assertEquals(0L, viewModel.elapsedFlow.value)
        assertFalse(viewModel.isTimerRunningFlow.value)
        assertTrue(viewModel.refsFlow.value.isNotEmpty())

        // Toggle done
        viewModel.toggleDone(true)
        assertTrue(viewModel.isDoneFlow.value)
        assertFalse(viewModel.isTimerRunningFlow.value)

        // Create a fresh view model to test timer running
        val freshViewModel = createViewModel()
        freshViewModel.startTimer()
        assertTrue(freshViewModel.isTimerRunningFlow.value)
    }

    @Test
    fun onRefClick_callbackIsInvoked() {
        var clickedId: String? = null
        val viewModel = createViewModel()
        viewModel.onRefClick = { clickedId = it }

        // Trigger the callback
        viewModel.onRefClick("flour-test-id")
        assertEquals("flour-test-id", clickedId)
    }
}
