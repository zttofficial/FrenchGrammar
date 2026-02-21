package com.zttothers.frenchgrammar.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zttothers.frenchgrammar.data.models.Verb
import com.zttothers.frenchgrammar.data.models.LearningProgress
import com.zttothers.frenchgrammar.data.repository.ProgressRepository
import com.zttothers.frenchgrammar.data.repository.VerbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class VerbUiState(
    val verbs: List<Verb> = emptyList(),
    val isLoading: Boolean = true,
    val searchQuery: String = ""
)

data class ProgressUiState(
    val masteredCount: Int = 0,
    val learningCount: Int = 0,
    val newCount: Int = 0,
    val totalCount: Int = 0
)

class VerbViewModel(
    private val verbRepository: VerbRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerbUiState())
    val uiState: StateFlow<VerbUiState> = _uiState.asStateFlow()

    private val _progressState = MutableStateFlow(ProgressUiState())
    val progressState: StateFlow<ProgressUiState> = _progressState.asStateFlow()

    init {
        loadVerbs()
        loadProgress()
    }

    private fun loadVerbs() {
        viewModelScope.launch {
            verbRepository.getAllVerbs().collect { verbs ->
                _uiState.value = _uiState.value.copy(
                    verbs = verbs,
                    isLoading = false
                )
            }
        }
    }

    private fun loadProgress() {
        viewModelScope.launch {
            try {
                progressRepository.getMasteredCount().collect { count ->
                    _progressState.value = _progressState.value.copy(masteredCount = count)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModelScope.launch {
            try {
                progressRepository.getLearningCount().collect { count ->
                    _progressState.value = _progressState.value.copy(learningCount = count)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModelScope.launch {
            try {
                progressRepository.getNewCount().collect { count ->
                    _progressState.value = _progressState.value.copy(newCount = count)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun search(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
        if (query.isEmpty()) {
            loadVerbs()
        } else {
            viewModelScope.launch {
                verbRepository.searchVerbs(query).collect { verbs ->
                    _uiState.value = _uiState.value.copy(verbs = verbs)
                }
            }
        }
    }
}

data class LearningState(
    val currentVerbIndex: Int = 0,
    val verbs: List<Verb> = emptyList(),
    val isFlipped: Boolean = false,
    val totalCount: Int = 0
)

class LearningViewModel(
    private val verbRepository: VerbRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _learningState = MutableStateFlow(LearningState())
    val learningState: StateFlow<LearningState> = _learningState.asStateFlow()
    private var _pendingJumpVerbId: Int = -1

    init {
        loadVerbsForLearning()
    }

    private fun loadVerbsForLearning() {
        viewModelScope.launch {
            verbRepository.getAllVerbs().collect { verbs ->
                val sorted = verbs.sortedBy { it.id }
                val current = _learningState.value
                val pending = _pendingJumpVerbId
                val startIndex = when {
                    pending >= 0 -> sorted.indexOfFirst { it.id == pending }.takeIf { it >= 0 } ?: 0
                    current.verbs.isNotEmpty() -> current.currentVerbIndex
                    else -> 0
                }
                _pendingJumpVerbId = -1
                _learningState.value = current.copy(
                    verbs = sorted,
                    totalCount = sorted.size,
                    currentVerbIndex = startIndex
                )
            }
        }
    }

    fun flipCard() {
        _learningState.value = _learningState.value.copy(
            isFlipped = !_learningState.value.isFlipped
        )
    }

    fun nextCard() {
        val s = _learningState.value
        if (s.verbs.isEmpty()) return
        val next = (s.currentVerbIndex + 1) % s.verbs.size
        _learningState.value = s.copy(currentVerbIndex = next, isFlipped = false)
    }

    fun jumpToVerbId(verbId: Int) {
        val verbs = _learningState.value.verbs
        if (verbs.isNotEmpty()) {
            val index = verbs.indexOfFirst { it.id == verbId }
            if (index >= 0) {
                _learningState.value = _learningState.value.copy(
                    currentVerbIndex = index,
                    isFlipped = false
                )
            }
        } else {
            _pendingJumpVerbId = verbId
        }
    }

    fun previousCard() {
        val s = _learningState.value
        if (s.verbs.isEmpty()) return
        val prev = (s.currentVerbIndex - 1 + s.verbs.size) % s.verbs.size
        _learningState.value = s.copy(currentVerbIndex = prev, isFlipped = false)
    }
}

// ─── Spelling Quiz ────────────────────────────────────────────────────────────

data class QuizQuestion(
    val verb: com.zttothers.frenchgrammar.data.models.Verb,
    val pronounLabel: String,
    val correctAnswer: String
)

data class SpellingQuizState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val userInput: String = "",
    val submitted: Boolean = false,
    val isCorrect: Boolean = false,
    val score: Int = 0,
    val isLoading: Boolean = true,
    val isFinished: Boolean = false
)

class SpellingQuizViewModel(
    private val verbRepository: VerbRepository,
    private val progressRepository: ProgressRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SpellingQuizState())
    val state: StateFlow<SpellingQuizState> = _state.asStateFlow()

    init { loadQuestions() }

    fun loadQuestions() {
        _state.value = SpellingQuizState(isLoading = true)
        viewModelScope.launch {
            verbRepository.getAllVerbs().collect { verbs ->
                val pronounEntries: List<Pair<String, (com.zttothers.frenchgrammar.data.models.Verb) -> String>> = listOf(
                    "Je" to { v -> v.je },
                    "Tu" to { v -> v.tu },
                    "Il / Elle" to { v -> v.ilElle },
                    "Nous" to { v -> v.nous },
                    "Vous" to { v -> v.vous },
                    "Ils / Elles" to { v -> v.ilsElles },
                    "P.P." to { v -> v.pastParticiple }
                )
                val questions = verbs.flatMap { verb ->
                    pronounEntries
                        .filter { (_, getter) -> getter(verb).isNotEmpty() }
                        .map { (pronoun, getter) -> QuizQuestion(verb, pronoun, getter(verb)) }
                }.shuffled().take(20)
                _state.value = SpellingQuizState(questions = questions, isLoading = false)
            }
        }
    }

    fun onInputChange(input: String) {
        if (!_state.value.submitted) {
            _state.value = _state.value.copy(userInput = input)
        }
    }

    fun submit() {
        val s = _state.value
        if (s.submitted || s.questions.isEmpty()) return
        val question = s.questions[s.currentIndex]
        val isCorrect = s.userInput.trim().equals(question.correctAnswer.trim(), ignoreCase = true)
        _state.value = s.copy(
            submitted = true,
            isCorrect = isCorrect,
            score = if (isCorrect) s.score + 1 else s.score
        )
        viewModelScope.launch {
            val progress = progressRepository.getProgress(question.verb.id)
                ?: LearningProgress(verbId = question.verb.id)
            val newCorrect = if (isCorrect) progress.correctCount + 1 else progress.correctCount
            val newReview = progress.reviewCount + 1
            val updatedProgress = progress.copy(
                status = when {
                    newCorrect >= 5 -> "MASTERED"
                    newReview > 0  -> "LEARNING"
                    else            -> "NEW"
                },
                reviewCount = newReview,
                correctCount = newCorrect,
                lastReviewTime = System.currentTimeMillis(),
                mastery = (newCorrect.toFloat() / newReview).coerceIn(0f, 1f)
            )
            progressRepository.updateProgress(updatedProgress)
        }
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


