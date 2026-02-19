package com.zttothers.frenchgrammar.data.repository

import com.zttothers.frenchgrammar.data.local.ProgressDataSource
import com.zttothers.frenchgrammar.data.local.VerbDataSource
import com.zttothers.frenchgrammar.data.models.LearningProgress
import com.zttothers.frenchgrammar.data.models.Verb
import kotlinx.coroutines.flow.Flow

interface VerbRepository {
    fun getAllVerbs(): Flow<List<Verb>>
    fun searchVerbs(query: String): Flow<List<Verb>>
    suspend fun getVerbById(id: Int): Verb?
    suspend fun insertVerb(verb: Verb)
    suspend fun insertVerbs(verbs: List<Verb>)
    suspend fun updateVerb(verb: Verb)
    suspend fun deleteVerb(verb: Verb)
}

interface ProgressRepository {
    suspend fun getProgress(verbId: Int): LearningProgress?
    fun getAllProgress(): Flow<List<LearningProgress>>
    fun getMasteredCount(): Flow<Int>
    fun getLearningCount(): Flow<Int>
    fun getNewCount(): Flow<Int>
    suspend fun updateProgress(progress: LearningProgress)
    suspend fun getVerpsForReview(limit: Int = 10): List<LearningProgress>
}

class VerbRepositoryImpl(private val verbDataSource: VerbDataSource) : VerbRepository {
    override fun getAllVerbs(): Flow<List<Verb>> = verbDataSource.getAllVerbs()

    override fun searchVerbs(query: String): Flow<List<Verb>> = verbDataSource.searchVerbs(query)

    override suspend fun getVerbById(id: Int): Verb? = verbDataSource.getVerbById(id)

    override suspend fun insertVerb(verb: Verb) = verbDataSource.insertVerb(verb)

    override suspend fun insertVerbs(verbs: List<Verb>) = verbDataSource.insertVerbs(verbs)

    override suspend fun updateVerb(verb: Verb) = verbDataSource.updateVerb(verb)

    override suspend fun deleteVerb(verb: Verb) = verbDataSource.deleteVerb(verb)
}

class ProgressRepositoryImpl(private val progressDataSource: ProgressDataSource) : ProgressRepository {
    override suspend fun getProgress(verbId: Int): LearningProgress? = progressDataSource.getProgress(verbId)

    override fun getAllProgress(): Flow<List<LearningProgress>> = progressDataSource.getAllProgress()

    override fun getMasteredCount(): Flow<Int> = progressDataSource.getMasteredCount()

    override fun getLearningCount(): Flow<Int> = progressDataSource.getLearningCount()

    override fun getNewCount(): Flow<Int> = progressDataSource.getNewCount()

    override suspend fun updateProgress(progress: LearningProgress) = progressDataSource.updateProgress(progress)

    override suspend fun getVerpsForReview(limit: Int): List<LearningProgress> {
        return progressDataSource.getVerpsForReview(limit)
    }
}


