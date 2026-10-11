package com.example.recipeapp.ui.components

import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.lifecycle.SavedStateHandle
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.Step
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class CookingStepViewTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

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

    @Before
    fun setUp() = Dispatchers.setMain(testDispatcher)

    @Test
    fun headerText_matchesPrepPhase() {
        var viewModel: CookingStepViewModel? = null
        composeTestRule.setContent {
            val savedStateHandle = SavedStateHandle()
            viewModel = CookingStepViewModel(step, listOf(flour, sugar), savedStateHandle)
            Surface {
                CookingStepView(
                    step = step,
                    ingredients = listOf(flour, sugar),
                    viewModel = viewModel!!,
                    modifier = Modifier,
                )
            }
        }

        composeTestRule.onNodeWithText("PREP: Mix dry ingredients").assertExists()
    }

    @Test
    fun refs_populateAnnotatedString() {
        var viewModel: CookingStepViewModel? = null
        composeTestRule.setContent {
            val savedStateHandle = SavedStateHandle()
            viewModel = CookingStepViewModel(step, listOf(flour, sugar), savedStateHandle)
            Surface {
                CookingStepView(
                    step = step,
                    ingredients = listOf(flour, sugar),
                    viewModel = viewModel!!,
                    modifier = Modifier,
                )
            }
        }

        // The refs should contain Flour and Sugar
        val refs = viewModel!!.refsFlow.value
        assert(refs.any { it.id == "Flour" }) { "Expected Flour ref" }
        assert(refs.any { it.id == "Sugar" }) { "Expected Sugar ref" }

        // Verify instruction text is displayed
        composeTestRule.onNodeWithText("Combine {Flour} and {Sugar} in a bowl.").assertExists()
    }

    @Test
    fun startButton_visibleWhenTimerIdle() {
        var viewModel: CookingStepViewModel? = null
        composeTestRule.setContent {
            val savedStateHandle = SavedStateHandle()
            viewModel = CookingStepViewModel(step, listOf(flour, sugar), savedStateHandle)
            Surface {
                CookingStepView(
                    step = step,
                    ingredients = listOf(flour, sugar),
                    viewModel = viewModel!!,
                    modifier = Modifier,
                )
            }
        }

        // Start button should be visible when timer is idle and not done
        composeTestRule.onNodeWithText("Start Timer").assertExists()
    }

    @Test
    fun startButton_callsStartTimer() {
        var viewModel: CookingStepViewModel? = null
        composeTestRule.setContent {
            val savedStateHandle = SavedStateHandle()
            viewModel = CookingStepViewModel(step, listOf(flour, sugar), savedStateHandle)
            Surface {
                CookingStepView(
                    step = step,
                    ingredients = listOf(flour, sugar),
                    viewModel = viewModel!!,
                    modifier = Modifier,
                )
            }
        }

        // Click start button
        composeTestRule.onNodeWithText("Start Timer").performClick()

        // Timer should now be running
        assert(viewModel!!.isTimerRunningFlow.value) { "Timer should be running after start" }
    }

    @Test
    fun doneButton_togglesDoneState() {
        var viewModel: CookingStepViewModel? = null
        composeTestRule.setContent {
            val savedStateHandle = SavedStateHandle()
            viewModel = CookingStepViewModel(step, listOf(flour, sugar), savedStateHandle)
            Surface {
                CookingStepView(
                    step = step,
                    ingredients = listOf(flour, sugar),
                    viewModel = viewModel!!,
                    modifier = Modifier,
                )
            }
        }

        // Initially not done
        assert(!viewModel!!.isDoneFlow.value)

        // Click "Mark Done" button
        composeTestRule.onNodeWithText("Mark Done").performClick()

        // Should now be done
        assert(viewModel.isDoneFlow.value) { "Should be done after toggle" }

        // Button text should change to "Undo Done"
        composeTestRule.onNodeWithText("Undo Done").assertExists()

        // Click "Undo Done" button
        composeTestRule.onNodeWithText("Undo Done").performClick()

        // Should no longer be done
        assert(!viewModel.isDoneFlow.value) { "Should not be done after undo" }
    }

    @Test
    fun persistence_viaSavedStateHandle() {
        var viewModel: CookingStepViewModel? = null

        val savedStateHandle = SavedStateHandle().apply {
            set("isDone", true)
            set("elapsedMs", 30_000L)
        }

        composeTestRule.setContent {
            viewModel = CookingStepViewModel(step, listOf(flour, sugar), savedStateHandle)
            Surface {
                CookingStepView(
                    step = step,
                    ingredients = listOf(flour, sugar),
                    viewModel = viewModel!!,
                    modifier = Modifier,
                )
            }
        }

        // Verify the restored state
        val restoredVm = viewModel!!
        assert(restoredVm.isDoneFlow.value) { "isDone should be restored from SavedStateHandle" }
        assert(restoredVm.elapsedFlow.value == 30_000L) {
            "elapsedMs should be restored from SavedStateHandle"
        }
    }

    @Test
    fun timerNotAutoResumingOnConfigChange() {
        // Simulate config change: create a new ViewModel with SavedStateHandle
        // that has elapsed time but timer was not running
        val savedStateHandle = SavedStateHandle().apply {
            set("elapsedMs", 60_000L)
            set("isDone", false)
        }

        var viewModel: CookingStepViewModel? = null
        composeTestRule.setContent {
            viewModel = CookingStepViewModel(step, listOf(flour, sugar), savedStateHandle)
            Surface {
                CookingStepView(
                    step = step,
                    ingredients = listOf(flour, sugar),
                    viewModel = viewModel!!,
                    modifier = Modifier,
                )
            }
        }

        // Timer should NOT be running after config change (no auto-resume)
        assert(!viewModel!!.isTimerRunningFlow.value) {
            "Timer should not auto-resume on config change"
        }
        // But elapsed time should be preserved
        assert(viewModel.elapsedFlow.value == 60_000L) {
            "Elapsed time should be preserved after config change"
        }
    }
}
