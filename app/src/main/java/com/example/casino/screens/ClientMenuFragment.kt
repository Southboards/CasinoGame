//package com.example.casino.fragment
//
//import android.net.Uri
//import android.os.Bundle
//import android.widget.Toast
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.Button
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.Observer
//import androidx.navigation.NavController
//import androidx.navigation.NavOptions
//import androidx.navigation.fragment.NavHostFragment
//import coil.compose.rememberImagePainter
//import com.example.casino.MainActivity
//import com.example.casino.R
//import com.example.casino.viewmodel.CasinoViewModel
//
//class ClientMenuFragment : Fragment() {
//
//    private val userViewModel: CasinoViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Here we will use Compose for UI
//        return ComposeView(requireContext()).apply {
//            setContent {
//                ClientMenuScreen(userViewModel = userViewModel)
//            }
//        }
//    }
//}
//
//@Composable
//fun ClientMenuScreen(userViewModel: CasinoViewModel) {
//    val linkRank = userViewModel.linkRankLiveData.collectAsState(initial = "")
//    val moneyPlayer = userViewModel.moneyPlayerLiveData.collectAsState(initial = 0)
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Rank Image
//        val painter: Painter = rememberImagePainter(
//            data = "android.resource://${userViewModel.context.packageName}/drawable/${linkRank.value}",
//            builder = {
//                crossfade(true)
//            }
//        )
//
//        Image(
//            painter = painter,
//            contentDescription = "Rank Image",
//            modifier = Modifier
//                .size(250.dp)
//                .padding(bottom = 16.dp)
//        )
//
//        // Money and Budget Section
//        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//            Text(text = "Budget: ", fontSize = 20.sp)
//            Text(text = "$${moneyPlayer.value}", fontSize = 20.sp)
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        // Play Button
//        Button(
//            onClick = { navigateToClientGameFragment() },
//            modifier = Modifier.fillMaxWidth().padding(8.dp)
//        ) {
//            Text(text = "Play", fontSize = 30.sp)
//        }
//
//        // Log Out Button
//        Button(
//            onClick = {
//                userViewModel.sendMessageLoginRegister("NONE")
//                navigateToMainFragmentBackStack()
//            },
//            modifier = Modifier.fillMaxWidth().padding(8.dp)
//        ) {
//            Text(text = "Log Out", fontSize = 30.sp)
//        }
//    }
//}
//
//fun navigateToClientGameFragment() {
//    val navController: NavController = NavHostFragment.findNavController(this)
//    navController.navigate(
//        R.id.action_clientMenuFragment_to_gameMenuFragment,
//        null,
//        NavOptions.Builder()
//            .setEnterAnim(android.R.animator.fade_in)
//            .setExitAnim(android.R.animator.fade_out)
//            .build()
//    )
//}
//
//fun navigateToMainFragmentBackStack() {
//    NavHostFragment.findNavController(this).popBackStack(R.id.mainFragment, false)
//}
