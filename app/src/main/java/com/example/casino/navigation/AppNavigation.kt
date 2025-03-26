package com.example.casino.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.casino.ui.*
import com.example.casino.viewmodel.CasinoViewModel

@Composable
fun AppNavigation(navController: NavHostController, userViewModel: CasinoViewModel) {

    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(userViewModel = userViewModel, navController = navController)
        }
        composable("client_menu_screen") {
            ClientMenuScreen(userViewModel = userViewModel, navController = navController)
        }
        composable("menu_game_screen") {
            MenuGameScreen(userViewModel = userViewModel, navController = navController)
        }
        composable("poker_screen") {
            PokerScreen(userViewModel = userViewModel, navController = navController)
        }
        composable("three_cards_screen") {
            ThreeCardsScreen(userViewModel = userViewModel, navController = navController)
        }
        composable("end_game_screen") {
            EndGameScreen(userViewModel = userViewModel, navController = navController)
        }
    }
}