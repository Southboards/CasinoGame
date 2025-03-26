package com.example.casino.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.casino.viewmodel.CasinoViewModel

@Composable
fun MenuGameScreen(userViewModel: CasinoViewModel, navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = {
                userViewModel.Process_EI_Button_Three_Cards_Game()
                navController.navigate("three_cards_screen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Three Cards", fontSize = 22.sp)
        }


        Button(
            onClick = {
                userViewModel.Process_EI_Button_Poker_Game()
                navController.navigate("poker_screen")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Poker", fontSize = 22.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuGameScreenPreview() {
    val navController = rememberNavController()
    val userViewModel = CasinoViewModel()
    MenuGameScreen(userViewModel = userViewModel, navController = navController)
}
