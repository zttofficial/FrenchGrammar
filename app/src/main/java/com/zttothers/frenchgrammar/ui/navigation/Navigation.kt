package com.zttothers.frenchgrammar.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.zttothers.frenchgrammar.presentation.viewmodel.LearningViewModel
import com.zttothers.frenchgrammar.presentation.viewmodel.NumbersQuizViewModel
import com.zttothers.frenchgrammar.presentation.viewmodel.SpellingQuizViewModel
import com.zttothers.frenchgrammar.presentation.viewmodel.VerbViewModel
import com.zttothers.frenchgrammar.ui.screens.CardLearningScreen
import com.zttothers.frenchgrammar.ui.screens.HomeScreen
import com.zttothers.frenchgrammar.ui.screens.NumbersQuizScreen
import com.zttothers.frenchgrammar.ui.screens.ProgressScreen
import com.zttothers.frenchgrammar.ui.screens.QuizScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CardLearning : Screen("card_learning?verbId={verbId}") {
        fun withVerb(verbId: Int) = "card_learning?verbId=$verbId"
        val plainRoute = "card_learning?verbId=-1"
    }
    object Quiz : Screen("quiz")
    object Numbers : Screen("numbers")
    object Progress : Screen("progress")
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    verbViewModel: VerbViewModel,
    learningViewModel: LearningViewModel,
    quizViewModel: SpellingQuizViewModel,
    numbersQuizViewModel: NumbersQuizViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController,
                viewModel = verbViewModel
            )
        }

        composable(
            route = Screen.CardLearning.route,
            arguments = listOf(navArgument("verbId") {
                type = NavType.IntType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val verbId = backStackEntry.arguments?.getInt("verbId") ?: -1
            LaunchedEffect(verbId) {
                if (verbId >= 0) learningViewModel.jumpToVerbId(verbId)
            }
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

        composable(Screen.Numbers.route) {
            NumbersQuizScreen(
                navController = navController,
                viewModel = numbersQuizViewModel
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

