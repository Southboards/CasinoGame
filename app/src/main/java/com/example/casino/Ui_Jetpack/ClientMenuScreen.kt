package com.example.casino.Ui_Jetpack

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.casino.viewmodel.CasinoViewModel

@Composable
fun ClientMenuScreen(userViewModel: CasinoViewModel, navController: NavController) {

    val linkRank = userViewModel.linkRankLiveData.observeAsState(initial = "")
    val moneyPlayer = userViewModel.moneyPlayerLiveData.observeAsState(initial = 0)

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        val painter: Painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = "android.resource://${context.packageName}/drawable/${linkRank.value}")
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true)
                }).build()
        )

        Image(
            painter = painter,
            contentDescription = "Rank Image",
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 16.dp)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(text = "Budget: ", fontSize = 28.sp, fontWeight = FontWeight.Bold) // Tăng font size cho "Budget" và làm đậm
            Text(text = "$${moneyPlayer.value}", fontSize = 40.sp, fontWeight = FontWeight.Bold) // Tăng font size và làm đậm số tiền
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { navigateToClientGameScreen(navController) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(70.dp)
        ) {
            Text(text = "Play", fontSize = 30.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nút "Log Out"
        Button(
            onClick = {
                userViewModel.sendMessageLoginRegister("NONE")
                navigateToMainScreenBackStack(navController)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(70.dp)
        ) {
            Text(text = "Log Out", fontSize = 30.sp)
        }
    }
}

fun navigateToClientGameScreen(navController: NavController) {
    navController.navigate("menu_game_screen")
}

fun navigateToMainScreenBackStack(navController: NavController) {
    navController.popBackStack("main_screen", false)
}

@Preview(showBackground = true)
@Composable
fun ClientMenuScreenPreview() {
    val navController = rememberNavController()
    val userViewModel = CasinoViewModel()
    ClientMenuScreen(userViewModel = userViewModel, navController = navController)
}
