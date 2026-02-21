package com.zttothers.frenchgrammar.ui.utils

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.Locale

/**
 * Creates and manages a [TextToSpeech] engine scoped to the current composition.
 * The engine is set to French (fr-FR) and shut down automatically when the
 * composable leaves the composition.
 *
 * Returns a [State] that is null until the engine finishes initialising.
 */
@Composable
fun rememberTts(context: Context): State<TextToSpeech?> {
    val state = remember { mutableStateOf<TextToSpeech?>(null) }

    DisposableEffect(context) {
        var engine: TextToSpeech? = null
        engine = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = engine?.setLanguage(Locale.FRENCH)
                if (result != TextToSpeech.LANG_MISSING_DATA &&
                    result != TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    state.value = engine
                }
            }
        }
        onDispose {
            engine?.stop()
            engine?.shutdown()
            state.value = null
        }
    }

    return state
}

/** Speak [text] in French, interrupting any current speech. */
fun TextToSpeech.speakFrench(text: String) {
    speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts_${System.currentTimeMillis()}")
}
