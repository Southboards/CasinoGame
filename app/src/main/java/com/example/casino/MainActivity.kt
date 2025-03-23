package com.example.casino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.casino.navigation.AppNavigation
import com.example.casino.viewmodel.CasinoViewModel

class MainActivity : ComponentActivity() {

    private lateinit var userViewModel: CasinoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userViewModel = ViewModelProvider(this).get(CasinoViewModel::class.java)

        setContent {
            val navController = rememberNavController()

            AppNavigation(navController = navController, userViewModel = userViewModel)
        }
    }

    @Preview
    @Composable
    fun MainScreenPreview() {
        val navController = rememberNavController()
        val userViewModel = CasinoViewModel()
        AppNavigation(navController = navController, userViewModel = userViewModel)
    }
}
