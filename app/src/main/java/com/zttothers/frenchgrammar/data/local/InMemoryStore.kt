package com.zttothers.frenchgrammar.data.local

import com.zttothers.frenchgrammar.data.models.LearningProgress
import com.zttothers.frenchgrammar.data.models.Verb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// In-memory implementation for now (no Room database)
object InMemoryVerbStore {
    private val verbsStore = MutableStateFlow<List<Verb>>(emptyList())
    private val progressStore = mutableMapOf<Int, LearningProgress>()

    val verbs: Flow<List<Verb>> = verbsStore.asStateFlow()

    fun addVerbs(verbs: List<Verb>) {
        verbsStore.value = verbsStore.value + verbs
    }

    fun updateProgress(progress: LearningProgress) {
        progressStore[progress.verbId] = progress
    }

    fun getProgress(verbId: Int): LearningProgress? = progressStore[verbId]

    fun getMasteredCount(): Int = progressStore.values.count { it.status == "MASTERED" }
    fun getLearningCount(): Int = progressStore.values.count { it.status == "LEARNING" }
    fun getNewCount(): Int = progressStore.values.count { it.status == "NEW" }
}

// Simplified interface compatible with Flow
class SimpleVerbDatabase {
    fun initializeWithDefaultVerbs() {
        val defaultVerbs = VerbDataGenerator.getDefaultVerbs()
        InMemoryVerbStore.addVerbs(defaultVerbs)
    }
}

