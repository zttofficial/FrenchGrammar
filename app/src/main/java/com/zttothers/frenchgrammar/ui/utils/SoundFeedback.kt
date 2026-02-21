package com.zttothers.frenchgrammar.ui.utils

import android.media.AudioManager
import android.media.ToneGenerator

/**
 * Simple sound feedback using ToneGenerator — no audio files required.
 * Correct  → two short rising beeps  (TONE_PROP_ACK)
 * Incorrect → one low buzzer tone     (TONE_PROP_NACK)
 */
object SoundFeedback {

    /** Single clean high beep for a correct answer. */
    fun playCorrect() {
        try {
            val tg = ToneGenerator(AudioManager.STREAM_MUSIC, 80)
            tg.startTone(ToneGenerator.TONE_PROP_BEEP, 180)
        } catch (_: Exception) { /* device may not support tone */ }
    }

    /** Short low buzzer for a wrong answer. */
    fun playIncorrect() {
        try {
            val tg = ToneGenerator(AudioManager.STREAM_MUSIC, 80)
            tg.startTone(ToneGenerator.TONE_PROP_NACK, 300)
        } catch (_: Exception) { /* device may not support tone */ }
    }
}
