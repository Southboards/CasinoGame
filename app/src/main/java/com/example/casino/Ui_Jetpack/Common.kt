package com.example.casino.Ui_Jetpack

import android.content.res.Resources
import android.widget.Toast
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Common() {

}

@Composable
fun showResultGame(results: List<String>) {
    val context = LocalContext.current
    Toast.makeText(context, "Game Over: ${results[0]} vs ${results[1]}", Toast.LENGTH_SHORT).show()
}

fun navigateToEndGameScreen(navController: NavController) {
    navController.navigate("end_game_screen")
}

@Composable
fun getImageResourceId(imageName: String): Int {
    val context = LocalContext.current
    return try {
        val resourceId = "drawable/$imageName"
        val id = context.resources.getIdentifier(resourceId, null, context.packageName)
        if (id == 0) throw Resources.NotFoundException("Resource not found for $imageName")
        id
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}
