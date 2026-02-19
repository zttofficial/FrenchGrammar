package com.zttothers.frenchgrammar.data.models

data class Verb(
    val id: Int = 0,
    val infinitive: String,           // 不定式 (e.g., "aller")
    val meaning: String,               // 中文意思 (e.g., "去，到")
    val je: String,                    // Je form
    val tu: String,                    // Tu form
    val ilElle: String,                // Il/Elle form
    val nous: String,                  // Nous form
    val vous: String,                  // Vous form
    val ilsElles: String,              // Ils/Elles form
    val pastParticiple: String,        // Past Participle (P.P.)
    val relatedVerbs: String = "",     // Related verbs separated by commas
    val createdAt: Long = System.currentTimeMillis()
)

data class LearningProgress(
    val verbId: Int,
    val status: String = "NEW",         // NEW, LEARNING, MASTERED
    val reviewCount: Int = 0,           // 複習次數
    val correctCount: Int = 0,          // 正確次數
    val lastReviewTime: Long = 0,       // 上次複習時間
    val nextReviewTime: Long = 0,       // 下次複習時間
    val mastery: Float = 0f             // 掌握度 0-1
)

data class VerbWithProgress(
    val verb: Verb,
    val progress: LearningProgress?
)

enum class PronounType {
    JE, TU, IL_ELLE, NOUS, VOUS, ILS_ELLES
}

data class ConjugationForm(
    val pronoun: String,
    val pronounType: PronounType,
    val form: String
)


