package com.example.casino.screens

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.casino.viewmodel.CasinoViewModel

class MainFragment : Fragment() {

    private lateinit var userViewModel: CasinoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProvider(requireActivity()).get(CasinoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MainFragmentContent(userViewModel)
            }
        }
    }
}

@Composable
fun MainFragmentContent(userViewModel: CasinoViewModel) {
    val message = userViewModel.messageLoginRegisterLiveData.observeAsState(initial = "")

    LaunchedEffect(message.value) {
        when (message.value) {
            "LOGIN SUCCESS", "REGISTER SUCCESS" -> {
                //navigateToClientMenuFragment()
            }
            "LOGIN FALSE", "REGISTER FALSE" -> {
                //Toast.makeText(LocalContext.current, "Login/Register Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Welcome to Casino", modifier = Modifier.padding(bottom = 16.dp))

        Button(onClick = {
            userViewModel.Process_EI_Button_Login("username", "password")
        }) {
            Text("Login")
        }

        Button(onClick = {
            userViewModel.Process_EI_Button_Register("username", "password")
        }) {
            Text("Register")
        }
    }
}

@Composable
fun navigateToClientMenuFragment() {
    val navController = LocalContext.current as? NavController
    navController?.navigate("client_menu_screen") {

    }
}
