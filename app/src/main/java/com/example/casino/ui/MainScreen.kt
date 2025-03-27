package com.example.casino.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.casino.viewmodel.CasinoViewModel

@Composable
fun MainScreen(userViewModel: CasinoViewModel, navController: NavController) {

    val message = userViewModel.messageLoginRegisterLiveData.observeAsState(initial = "")

    val context = LocalContext.current

    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    LaunchedEffect(message.value) {
        when (message.value) {
            "LOGIN SUCCESS", "REGISTER SUCCESS" -> {
                navController.navigate("client_menu_screen")
            }
            "LOGIN FALSE", "REGISTER FALSE" -> {
                Toast.makeText(context, "Login/Register Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CASINO WORLD",
            modifier = Modifier.padding(bottom = 32.dp),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E88E5),
            letterSpacing = 1.5.sp,
        )
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            singleLine = true,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // action when enter
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                userViewModel.Process_EI_Button_Login(username.value, password.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                userViewModel.Process_EI_Button_Register(username.value, password.value)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text("Register")
        }
    }
}

@Preview(showBackground = false)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    val userViewModel = CasinoViewModel()

    MainScreen(userViewModel = userViewModel, navController = navController)
}