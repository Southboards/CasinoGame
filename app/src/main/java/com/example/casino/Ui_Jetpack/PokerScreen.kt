package com.example.casino.Ui_Jetpack

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

@Composable
fun PokerScreen(userViewModel: CasinoViewModel, navController: NavController) {
    val botCards by userViewModel.listLinkCardsBotLiveData.observeAsState(emptyList())
    val playerCards by userViewModel.listLinkCardsPlayerLiveData.observeAsState(emptyList())
    val tableCards by userViewModel.listLinkCardsTableLiveData.observeAsState(emptyList())
    val results by userViewModel.listResultPlayersLiveData.observeAsState(listOf("NONE", "NONE"))
    val isEndGame by userViewModel.endGameLiveData.observeAsState(false)

    if (isEndGame) {
        showResultGame(results)
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

        Spacer(modifier = Modifier.height(16.dp))

        // Row 3: Bet buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            BetButton(betAmount = 100, onClick = { handleBetClickPoker(userViewModel, navController, 100) })
            Spacer(modifier = Modifier.width(7.dp))
            BetButton(betAmount = 200, onClick = { handleBetClickPoker(userViewModel, navController, 200) })
            Spacer(modifier = Modifier.width(7.dp))
            BetButton(betAmount = 500, onClick = { handleBetClickPoker(userViewModel, navController, 500) })
            Spacer(modifier = Modifier.width(7.dp))
            FoldButton(onClick = { handleFoldClick(userViewModel, navController) })
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Row 4: Player cards
        CardRowPoker(userViewModel, cards = playerCards.take(2), isPlayer = true)
        if (isEndGame) {
            Text(text = "Player: ${results[1]}", fontSize = 20.sp, color = Color.Blue)
        }
    }
}

@Composable
fun CardRowPoker(userViewModel: CasinoViewModel, cards: List<String>, isPlayer: Boolean) {
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
                    .weight(1f)
                    .aspectRatio(0.75f)
                    .clickable {
                        userViewModel.Process_EI_Button_Card(index);
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
            .width(85.dp)
            .padding(8.dp),
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
            .width(90.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = "Fold", fontSize = 12.sp)
    }
}

fun handleFoldClick(userViewModel: CasinoViewModel, navController: NavController) {
    userViewModel.Process_EI_Button_Fold()

    val handler = Handler(Looper.getMainLooper())
    handler.postDelayed({
        navigateToEndGameScreen(navController)
    }, 3000)
}

fun handleBetClickPoker(userViewModel: CasinoViewModel, navController: NavController, betAmount: Int) {
    userViewModel.Process_EI_Button_Bet(betAmount)
}

@Preview(showBackground = true)
@Composable
fun PokerGameScreenPreview() {
    val userViewModel = CasinoViewModel()
    val navController = rememberNavController()
    PokerScreen(userViewModel = userViewModel, navController = navController)
}
