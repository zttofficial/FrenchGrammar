package com.zttothers.frenchgrammar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zttothers.frenchgrammar.presentation.viewmodel.LearningViewModel
import com.zttothers.frenchgrammar.presentation.viewmodel.SpellingQuizViewModel
import com.zttothers.frenchgrammar.presentation.viewmodel.VerbViewModel
import com.zttothers.frenchgrammar.ui.screens.CardLearningScreen
import com.zttothers.frenchgrammar.ui.screens.HomeScreen
import com.zttothers.frenchgrammar.ui.screens.ProgressScreen
import com.zttothers.frenchgrammar.ui.screens.QuizScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CardLearning : Screen("card_learning")
    object Quiz : Screen("quiz")
    object Progress : Screen("progress")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    verbViewModel: VerbViewModel,
    learningViewModel: LearningViewModel,
    quizViewModel: SpellingQuizViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = verbViewModel
            )
        }

        composable(Screen.CardLearning.route) {
            CardLearningScreen(
                navController = navController,
                viewModel = learningViewModel
            )
        }

        composable(Screen.Quiz.route) {
            QuizScreen(
                navController = navController,
                viewModel = quizViewModel
            )
        }

        composable(Screen.Progress.route) {
            ProgressScreen(
                navController = navController,
                viewModel = verbViewModel
            )
        }
    }
}

