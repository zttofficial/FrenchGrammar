package com.zttothers.frenchgrammar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ─── Data ─────────────────────────────────────────────────────────────────────

/** Direction of a single question. */
enum class NumberQuizDirection {
    /** Show the digit, type the French word. */
    DIGIT_TO_FRENCH,
    /** Show the French word, type the digit. */
    FRENCH_TO_DIGIT,
    /** Hear the French word via TTS, type either the digit or the French word. */
    LISTENING
}

data class NumberQuestion(
    val digit: Int,
    val french: String,
    val direction: NumberQuizDirection
) {
    /** The text displayed as the "prompt" (hidden for LISTENING — only TTS plays it). */
    val prompt: String get() = when (direction) {
        NumberQuizDirection.DIGIT_TO_FRENCH -> digit.toString()
        NumberQuizDirection.FRENCH_TO_DIGIT -> french
        NumberQuizDirection.LISTENING       -> french   // spoken, not shown
    }

    /** Primary expected answer displayed in feedback. */
    val answer: String get() = when (direction) {
        NumberQuizDirection.DIGIT_TO_FRENCH -> french
        NumberQuizDirection.FRENCH_TO_DIGIT -> digit.toString()
        NumberQuizDirection.LISTENING       -> "${digit} / $french"
    }

    /** Returns true if [input] counts as correct for this question. */
    fun isCorrect(input: String): Boolean {
        val trimmed = input.trim()
        return when (direction) {
            NumberQuizDirection.DIGIT_TO_FRENCH ->
                trimmed.equals(french, ignoreCase = true)
            NumberQuizDirection.FRENCH_TO_DIGIT ->
                trimmed.equals(digit.toString(), ignoreCase = true)
            NumberQuizDirection.LISTENING ->
                trimmed.equals(digit.toString(), ignoreCase = true) ||
                trimmed.equals(french, ignoreCase = true)
        }
    }
}

data class NumbersQuizState(
    val questions: List<NumberQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val userInput: String = "",
    val submitted: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val isFinished: Boolean = false
)

// ─── French numbers 1-100 ─────────────────────────────────────────────────────

private val FRENCH_NUMBERS: Map<Int, String> = buildMap {
    val ones = listOf("", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf",
        "dix", "onze", "douze", "treize", "quatorze", "quinze", "seize",
        "dix-sept", "dix-huit", "dix-neuf")

    // 1-19
    for (i in 1..19) put(i, ones[i])

    // 20-69 (vingt, trente, quarante, cinquante, soixante)
    val tens = mapOf(20 to "vingt", 30 to "trente", 40 to "quarante", 50 to "cinquante", 60 to "soixante")
    for ((base, baseName) in tens) {
        put(base, baseName)
        put(base + 1, "$baseName et un")
        for (i in 2..9) put(base + i, "$baseName-${ones[i]}")
    }

    // 70-79 (soixante-dix…)
    put(70, "soixante-dix")
    put(71, "soixante et onze")
    for (i in 2..9) put(70 + i, "soixante-${ones[10 + i]}")

    // 80-89 (quatre-vingts, quatre-vingt-un…)
    put(80, "quatre-vingts")
    for (i in 1..9) put(80 + i, "quatre-vingt-${ones[i]}")

    // 90-99 (quatre-vingt-dix…)
    put(90, "quatre-vingt-dix")
    put(91, "quatre-vingt-onze")
    for (i in 2..9) put(90 + i, "quatre-vingt-${ones[10 + i]}")

    // 100
    put(100, "cent")
}

// ─── ViewModel ────────────────────────────────────────────────────────────────

class NumbersQuizViewModel : ViewModel() {

    private val _state = MutableStateFlow(NumbersQuizState())
    val state: StateFlow<NumbersQuizState> = _state.asStateFlow()

    init { loadQuestions() }

    fun loadQuestions() {
        val allNumbers = FRENCH_NUMBERS.entries.toList()   // 100 entries
        val directions = NumberQuizDirection.entries.toList()
        val questions = allNumbers
            .shuffled()
            .take(20)
            .map { (digit, french) ->
                NumberQuestion(digit, french, directions.random())
            }
        _state.value = NumbersQuizState(questions = questions)
    }

    fun onInputChange(input: String) {
        if (!_state.value.submitted) {
            _state.value = _state.value.copy(userInput = input)
        }
    }

    fun submit() {
        val s = _state.value
        if (s.submitted || s.questions.isEmpty()) return
        val q = s.questions[s.currentIndex]
        val isCorrect = q.isCorrect(s.userInput)
        _state.value = s.copy(
            submitted = true,
            isCorrect = isCorrect,
            score = if (isCorrect) s.score + 1 else s.score
        )
    }

    fun next() {
        val s = _state.value
        val nextIndex = s.currentIndex + 1
        if (nextIndex >= s.questions.size) {
            _state.value = s.copy(isFinished = true)
        } else {
            _state.value = s.copy(
                currentIndex = nextIndex,
                userInput = "",
                submitted = false,
                isCorrect = false
            )
        }
    }
}
