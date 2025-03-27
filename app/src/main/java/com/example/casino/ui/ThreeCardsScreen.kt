package com.example.casino.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.casino.viewmodel.CasinoViewModel
import android.os.Handler
import android.os.Looper
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun ThreeCardsScreen(userViewModel: CasinoViewModel, navController: NavController) {

    val botCards by userViewModel.listLinkCardsBotLiveData.observeAsState(emptyList())
    val playerCards by userViewModel.listLinkCardsPlayerLiveData.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CardRowThreeCard(userViewModel, cards = botCards, isPlayer = false)

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BetButtonThreeCards(betAmount = 100, onClick = { handleBetClickThreeCards(userViewModel, navController, 100) })
            BetButtonThreeCards(betAmount = 200, onClick = { handleBetClickThreeCards(userViewModel, navController, 200) })
            BetButtonThreeCards(betAmount = 500, onClick = { handleBetClickThreeCards(userViewModel, navController, 500) })
        }

        Spacer(modifier = Modifier.height(32.dp))

        CardRowThreeCard(userViewModel, cards = playerCards, isPlayer = true)
    }
}

@Composable
fun CardRowThreeCard(userViewModel: CasinoViewModel, cards: List<String>, isPlayer: Boolean) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageWidth = screenWidth * 0.25f
    val imageHeight = screenHeight * 0.25f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        cards.forEachIndexed { index, cardLink ->
            val resourceId = getImageResourceId(cardLink)
            if (resourceId != 0) {
                val painter = painterResource(id = resourceId)
                val modifier = Modifier
                    .padding(8.dp)
                    .width(imageWidth)
                    .height(imageHeight)
                    .clickable {
                        if ((index <= 2) && isPlayer) {
                            userViewModel.Process_EI_Button_Card(index)
                        }
                    }

                Image(
                    painter = painter,
                    contentDescription = if (isPlayer) "Player card $index" else "Bot card $index",
                    modifier = modifier
                )
            } else {
                Text("Invalid card image", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun BetButtonThreeCards(betAmount: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(50.dp)
            .width(100.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "$betAmount", fontSize = 16.sp)
    }
}

fun handleBetClickThreeCards(userViewModel: CasinoViewModel, navController: NavController, betAmount: Int) {
    userViewModel.Process_EI_Button_Bet(betAmount)

    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        navigateToEndGameScreen(navController)
    }, 5000)
}

@Preview(showBackground = true)
@Composable
fun ThreeCardsScreenPreview() {
    val userViewModel = CasinoViewModel() // Giả lập ViewModel
    val navController = rememberNavController() // Giả lập NavController
    ThreeCardsScreen(userViewModel = userViewModel, navController = navController)
}
