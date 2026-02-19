package com.zttothers.frenchgrammar

import android.app.Application
import com.zttothers.frenchgrammar.data.local.InMemoryProgressDataSource
import com.zttothers.frenchgrammar.data.local.InMemoryVerbDataSource
import com.zttothers.frenchgrammar.data.local.VerbDataGenerator
import com.zttothers.frenchgrammar.data.repository.ProgressRepositoryImpl
import com.zttothers.frenchgrammar.data.repository.VerbRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FrenchGrammarApp : Application() {

    companion object {
        lateinit var verbRepository: VerbRepositoryImpl
        lateinit var progressRepository: ProgressRepositoryImpl
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize data sources
        val verbDataSource = InMemoryVerbDataSource()
        val progressDataSource = InMemoryProgressDataSource()

        // Initialize repositories
        verbRepository = VerbRepositoryImpl(verbDataSource)
        progressRepository = ProgressRepositoryImpl(progressDataSource)

        // Populate default verbs
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val defaultVerbs = VerbDataGenerator.getDefaultVerbs()
                verbRepository.insertVerbs(defaultVerbs)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}



