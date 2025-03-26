package com.example.casino.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import com.example.casino.viewmodel.CasinoViewModel
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController

@Composable
fun EndGameScreen(userViewModel: CasinoViewModel, navController: NavController) {

    val winLoseImage by userViewModel.linkWinLoseLiveData.observeAsState("")
    val moneyBetValue by userViewModel.moneyBetLiveData.observeAsState(0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = getImageResourceId(winLoseImage)),
            contentDescription = "Win Lose Image",
            modifier = Modifier.size(170.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = formatMoneyText(moneyBetValue),
            fontSize = 25.sp,
            color = if (moneyBetValue >= 0) Color.Green else Color.Red
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    userViewModel.Process_EI_Button_Play_Again()
                    navigateToGameBackStack(navController)
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Play Again", fontSize = 16.sp)
            }

            Button(
                onClick = {
                    userViewModel.Process_EI_Button_Out_Table()
                    navigateToClientMenuBackStack(navController)
                },
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .padding(8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Out Table", fontSize = 16.sp)
            }
        }
    }
}

fun formatMoneyText(value: Int): String {
    return if (value >= 0) {
        "+$value $"
    } else {
        "$value $"
    }
}

fun navigateToGameBackStack(navController: NavController) {
    navController.popBackStack()
}

fun navigateToClientMenuBackStack(navController: NavController) {
    navController.popBackStack("client_menu_screen", false)
}


@Preview(showBackground = true)
@Composable
fun EndGameScreenPreview() {
    val userViewModel = CasinoViewModel() // Giả lập ViewModel
    val navController = rememberNavController() // Giả lập NavController
    EndGameScreen(userViewModel = userViewModel, navController = navController)
}
