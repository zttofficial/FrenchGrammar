package com.zttothers.frenchgrammar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.zttothers.frenchgrammar.presentation.viewmodel.LearningViewModel
import com.zttothers.frenchgrammar.presentation.viewmodel.SpellingQuizViewModel
import com.zttothers.frenchgrammar.presentation.viewmodel.VerbViewModel
import com.zttothers.frenchgrammar.ui.navigation.AppNavigation
import com.zttothers.frenchgrammar.ui.theme.FrenchGrammarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrenchGrammarTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppContent()
                }
            }
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()

    // Create ViewModels with custom factory
    val verbViewModel = viewModel<VerbViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return VerbViewModel(
                    FrenchGrammarApp.verbRepository,
                    FrenchGrammarApp.progressRepository
                ) as T
            }
        }
    )

    val learningViewModel = viewModel<LearningViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LearningViewModel(
                    FrenchGrammarApp.verbRepository,
                    FrenchGrammarApp.progressRepository
                ) as T
            }
        }
    )

    val quizViewModel = viewModel<SpellingQuizViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SpellingQuizViewModel(
                    FrenchGrammarApp.verbRepository,
                    FrenchGrammarApp.progressRepository
                ) as T
            }
        }
    )

    AppNavigation(
        navController = navController,
        verbViewModel = verbViewModel,
        learningViewModel = learningViewModel,
        quizViewModel = quizViewModel
    )
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    FrenchGrammarTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            // Preview placeholder
        }
    }
}