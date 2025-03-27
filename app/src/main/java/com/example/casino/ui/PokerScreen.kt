package com.example.casino.ui

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.casino.viewmodel.CasinoViewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun PokerScreen(userViewModel: CasinoViewModel, navController: NavController) {
    val botCards by userViewModel.getListLinkCardsBotLiveData().observeAsState(emptyList())
    val playerCards by userViewModel.getListLinkCardsPlayerLiveData().observeAsState(emptyList())
    val tableCards by userViewModel.getListLinkCardsTableLiveData().observeAsState(emptyList())
    val results by userViewModel.getListResultPlayersLiveData().observeAsState(listOf("NONE", "NONE"))
    val isEndGame by userViewModel.getEndGameLiveData().observeAsState(false)

    var hasNavigated by remember { mutableStateOf(false) }

    if (isEndGame && !hasNavigated) {
        showResultGame(results)
        hasNavigated = true
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            navigateToEndGameScreen(navController)
        }, 3000)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Row 1: Bot cards
        CardRowPoker(userViewModel, cards = botCards.take(2), isPlayer = false)

        Spacer(modifier = Modifier.height(16.dp))
        if (isEndGame) {
            Text(text = "Bot: ${results[0]}", fontSize = 20.sp, color = Color.Blue)
        }

        // Row 2: Table cards (5 cards)
        CardRowPoker(userViewModel, cards = tableCards.take(5), isPlayer = false)

        Spacer(modifier = Modifier.height(16.dp))

        // Row 3: Bet buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            BetButton(betAmount = 100, onClick = { handleBetClickPoker(userViewModel, navController, 100) })
            Spacer(modifier = Modifier.width(3.dp))
            BetButton(betAmount = 200, onClick = { handleBetClickPoker(userViewModel, navController, 200) })
            Spacer(modifier = Modifier.width(3.dp))
            BetButton(betAmount = 500, onClick = { handleBetClickPoker(userViewModel, navController, 500) })
            Spacer(modifier = Modifier.width(3.dp))
            FoldButton(onClick = { handleFoldClick(userViewModel, navController) })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Row 4: Player cards
        CardRowPoker(userViewModel, cards = playerCards.take(2), isPlayer = true)
        if (isEndGame) {
            Text(text = "Player: ${results[1]}", fontSize = 20.sp, color = Color.Blue)
        }
    }
}

@Composable
fun CardRowPoker(userViewModel: CasinoViewModel, cards: List<String>, isPlayer: Boolean) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    var imageWidth = 0.dp;
    var imageHeight = 0.dp;
    if (cards.size == 5) {
        imageWidth = screenWidth * 0.15f
        imageHeight = screenHeight * 0.15f
    } else {
        imageWidth = (screenWidth * 0.25f)
        imageHeight = screenHeight * 0.25f
    }

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
                    .padding(4.dp)
                    .width(imageWidth)
                    .height(imageHeight)
                    .clickable {
                        if ((index <= 1) && isPlayer) {
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

// Bet button
@Composable
fun BetButton(betAmount: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(50.dp)
            .width(80.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "$betAmount", fontSize = 12.sp)
    }
}

// Fold button
@Composable
fun FoldButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(50.dp)
            .width(80.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Fold", fontSize = 10.sp)
    }
}

fun handleFoldClick(userViewModel: CasinoViewModel, navController: NavController) {
    userViewModel.Process_EI_Button_Fold()
}

fun handleBetClickPoker(userViewModel: CasinoViewModel, navController: NavController, betAmount: Int) {
    userViewModel.Process_EI_Button_Bet(betAmount)
}

@Preview(showBackground = true)
@Composable
fun PokerGameScreenPreview() {
//    val userViewModel = CasinoViewModel()
//    val navController = rememberNavController()
//    PokerScreen(userViewModel = userViewModel, navController = navController)
}
