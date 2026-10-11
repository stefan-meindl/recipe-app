package com.example.recipeapp.ui.components

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.domain.parser.IngredientRef
import com.example.recipeapp.domain.parser.PlainText
import com.example.recipeapp.domain.parser.StepSegment
import com.example.recipeapp.domain.parser.resolve
import com.example.recipeapp.model.Ingredient
import com.example.recipeapp.model.Step
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/** A tappable inline reference parsed from step instruction text. */
data class TextRef(
    val id: String,
    val text: String,
    val start: Int,
    val end: Int,
)

/** UI state for a single cooking step. */
data class CookingStepUiState(
    val isDone: Boolean = false,
    val elapsedMs: Long = 0L,
    val durationMs: Long = 0L,
    val headerText: String = "",
    val instructionText: String = "",
    val isTimerRunning: Boolean = false,
    val refs: List<TextRef> = emptyList(),
)

/**
 * ViewModel for a single [Step] with timer, done toggle, and inline ref parsing.
 *
 * State survives config changes via [SavedStateHandle]. Timer does NOT auto-resume on config change.
 */
class CookingStepViewModel(
    private val step: Step,
    private val ingredients: List<Ingredient>,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val durationMs: Long = (step.durationMinutes ?: 0).toLong() * 60 * 1000

    private val headerText: String = buildHeader(step)

    // -- Source of truth flows --

    private val _elapsedFlow = MutableStateFlow(savedStateHandle.get<Long>("elapsedMs") ?: 0L)
    val elapsedFlow: StateFlow<Long> = _elapsedFlow

    private val _isDoneFlow = MutableStateFlow(savedStateHandle.get<Boolean>("isDone") ?: false)
    val isDoneFlow: StateFlow<Boolean> = _isDoneFlow

    private val _isTimerRunningFlow = MutableStateFlow(false)
    val isTimerRunningFlow: StateFlow<Boolean> = _isTimerRunningFlow

    private val _refsFlow = MutableStateFlow<List<TextRef>>(emptyList())
    val refsFlow: StateFlow<List<TextRef>> = _refsFlow

    private var timerJob: Job? = null

    // -- Sync: SavedStateHandle mirrors elapsedFlow and isDone --

    init {
        _elapsedFlow
            .onEach { savedStateHandle.set("elapsedMs", it) }
            .launchIn(viewModelScope)

        _isDoneFlow
            .onEach { savedStateHandle.set("isDone", it) }
            .launchIn(viewModelScope)

        // Parse refs on init using the #03 module
        _refsFlow.value = parseRefs(step.description)
    }

    // -- Public API --

    /** Toggle done state. Cancels any running timer. */
    fun toggleDone(isDone: Boolean) {
        _isDoneFlow.value = isDone
        savedStateHandle.set("isDone", isDone)
        timerJob?.cancel()
        timerJob = null
        _isTimerRunningFlow.value = false
    }

    /** Start the timer. No-op if already running or done. */
    fun startTimer() {
        if (_isTimerRunningFlow.value || _isDoneFlow.value) return
        _isTimerRunningFlow.value = true
        timerJob = viewModelScope.launch {
            try {
                while (_elapsedFlow.value < durationMs) {
                    delay(1000)
                    _elapsedFlow.value += 1000
                }
            } finally {
                _isTimerRunningFlow.value = false
            }
        }
    }

    /** Handle a tap on a tappable ref. */
    fun onRefClick(id: String) {
        // Delegate ref tap handling (e.g., scroll to ingredient, show detail)
    }

    /** Parse instruction text into tappable refs using the #03 module. */
    private fun parseRefs(instructionText: String): List<TextRef> {
        val segments = resolve(instructionText, ingredients)
        val refs = mutableListOf<TextRef>()
        var offset = 0
        for (segment in segments) {
            when (segment) {
                is PlainText -> offset += segment.text.length
                is IngredientRef -> {
                    // Try to find the ingredient name in the original text
                    val idx = instructionText.indexOf(segment.name, offset)
                    if (idx >= 0) {
                        refs.add(
                            TextRef(
                                id = segment.id,
                                text = segment.name,
                                start = idx,
                                end = idx + segment.name.length,
                            ),
                        )
                        offset = idx + segment.name.length
                    } else {
                        // Fall back to finding the {key} pattern
                        val keyStart = instructionText.indexOf('{', offset)
                        if (keyStart >= 0) {
                            val keyEnd = instructionText.indexOf('}', keyStart)
                            if (keyEnd >= 0) {
                                refs.add(
                                    TextRef(
                                        id = segment.id,
                                        text = segment.name,
                                        start = keyStart,
                                        end = keyEnd + 1,
                                    ),
                                )
                                offset = keyEnd + 1
                            }
                        }
                    }
                }
            }
        }
        return refs
    }

    // -- UI State --

    private val initialUiState = CookingStepUiState(
        isDone = _isDoneFlow.value,
        elapsedMs = _elapsedFlow.value,
        durationMs = durationMs,
        headerText = headerText,
        instructionText = step.description,
        isTimerRunning = _isTimerRunningFlow.value,
        refs = _refsFlow.value,
    )

    /** Combined UI state that merges all source flows. */
    val uiState: StateFlow<CookingStepUiState> = combine(
        _elapsedFlow,
        _isDoneFlow,
        _isTimerRunningFlow,
        _refsFlow,
    ) { elapsed, isDone, isTimerRunning, refs ->
        CookingStepUiState(
            isDone = isDone,
            elapsedMs = elapsed,
            durationMs = durationMs,
            headerText = headerText,
            instructionText = step.description,
            isTimerRunning = isTimerRunning,
            refs = refs,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), initialUiState)

    companion object {
        private fun buildHeader(step: Step): String {
            val phase = step.phase.ifBlank { "STEP" }
            return if (step.title.isNotBlank()) "$phase: ${step.title}" else phase
        }
    }
}
