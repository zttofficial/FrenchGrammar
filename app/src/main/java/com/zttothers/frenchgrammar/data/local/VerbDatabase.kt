package com.zttothers.frenchgrammar.data.local

import com.zttothers.frenchgrammar.data.models.Verb
import com.zttothers.frenchgrammar.data.models.LearningProgress
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

// Verb DAO Interface
interface VerbDataSource {
    fun getAllVerbs(): Flow<List<Verb>>
    suspend fun getVerbById(id: Int): Verb?
    fun searchVerbs(query: String): Flow<List<Verb>>
    suspend fun insertVerb(verb: Verb)
    suspend fun insertVerbs(verbs: List<Verb>)
    suspend fun updateVerb(verb: Verb)
    suspend fun deleteVerb(verb: Verb)
}

// Progress DAO Interface
interface ProgressDataSource {
    suspend fun getProgress(verbId: Int): LearningProgress?
    fun getAllProgress(): Flow<List<LearningProgress>>
    fun getMasteredCount(): Flow<Int>
    fun getLearningCount(): Flow<Int>
    fun getNewCount(): Flow<Int>
    suspend fun updateProgress(progress: LearningProgress)
    suspend fun getVerpsForReview(limit: Int): List<LearningProgress>
}

// In-Memory Implementation
class InMemoryVerbDataSource : VerbDataSource {
    private val verbsFlow = MutableStateFlow<List<Verb>>(emptyList())

    override fun getAllVerbs(): Flow<List<Verb>> = verbsFlow

    override suspend fun getVerbById(id: Int): Verb? {
        return verbsFlow.value.find { it.id == id }
    }

    override fun searchVerbs(query: String): Flow<List<Verb>> {
        return verbsFlow.map { verbs ->
            verbs.filter { v ->
                listOf(
                    v.infinitive, v.meaning, v.je, v.tu, v.ilElle,
                    v.nous, v.vous, v.ilsElles, v.pastParticiple, v.relatedVerbs
                ).any { it.contains(query, ignoreCase = true) }
            }
        }
    }

    override suspend fun insertVerb(verb: Verb) {
        verbsFlow.value = verbsFlow.value + verb
    }

    override suspend fun insertVerbs(verbs: List<Verb>) {
        verbsFlow.value = verbsFlow.value + verbs
    }

    override suspend fun updateVerb(verb: Verb) {
        verbsFlow.value = verbsFlow.value.map { if (it.id == verb.id) verb else it }
    }

    override suspend fun deleteVerb(verb: Verb) {
        verbsFlow.value = verbsFlow.value.filter { it.id != verb.id }
    }
}

class InMemoryProgressDataSource : ProgressDataSource {
    private val progressMap = mutableMapOf<Int, LearningProgress>()
    private val progressFlow = MutableStateFlow(progressMap.toList())

    override suspend fun getProgress(verbId: Int): LearningProgress? {
        return progressMap[verbId]
    }

    override fun getAllProgress(): Flow<List<LearningProgress>> {
        return progressFlow.map { list -> list.map { pair -> pair.second } }
    }

    override fun getMasteredCount(): Flow<Int> {
        return progressFlow.map { list -> list.count { pair -> pair.second.status == "MASTERED" } }
    }

    override fun getLearningCount(): Flow<Int> {
        return progressFlow.map { list -> list.count { pair -> pair.second.status == "LEARNING" } }
    }

    override fun getNewCount(): Flow<Int> {
        return progressFlow.map { list -> list.count { pair -> pair.second.status == "NEW" } }
    }

    override suspend fun updateProgress(progress: LearningProgress) {
        progressMap[progress.verbId] = progress
        progressFlow.value = progressMap.toList()
    }

    override suspend fun getVerpsForReview(limit: Int): List<LearningProgress> {
        val currentTime = System.currentTimeMillis()
        return progressMap.values
            .filter { it.nextReviewTime <= currentTime && it.status != "MASTERED" }
            .sortedBy { it.nextReviewTime }
            .take(limit)
    }
}

// Database wrapper
class VerbDatabase {
    val verbDataSource: VerbDataSource = InMemoryVerbDataSource()
    val progressDataSource: ProgressDataSource = InMemoryProgressDataSource()
}


