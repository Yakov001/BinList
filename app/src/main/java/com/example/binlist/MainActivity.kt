package com.example.binlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.binlist.ui.composables.BinListTabRow
import com.example.binlist.ui.screens.CardInfo
import com.example.binlist.ui.screens.MainScreen
import com.example.binlist.ui.screens.MemoryScreen
import com.example.binlist.ui.theme.BinListTheme
import com.example.binlist.viewModel.MainViewModel
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BinListApp()
        }
    }
}

@Composable
fun BinListApp() {

    val mainViewModel: MainViewModel = viewModel()

    BinListTheme {
        val navController = rememberNavController()
        val backstackEntry = navController.currentBackStackEntryAsState()
        val currentScreen = BinListScreen.fromRoute(backstackEntry.value?.destination?.route)

        Scaffold(
            topBar = {
                BinListTabRow(
                    onTabSelected = { navController.navigate(it.name) },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            BinNavHost(navController, mainViewModel, Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun BinNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = BinListScreen.Main.name,
        modifier = modifier
    ) {
        composable(BinListScreen.Main.name) {
            val cardResponse = viewModel.cardResponse.collectAsState()
            MainScreen(
                card = cardResponse.value.data,
                onButtonRequestClick = { viewModel.requestBin(it) }
            )
        }
        composable(BinListScreen.Memory.name) {
            viewModel.getAllCards()
            MemoryScreen(
                cards = viewModel.memory.value,
                showDetailedCard = {
                    navController.navigate("${BinListScreen.Memory.name}/${it.id}")
                }
            )
        }
        composable(
            route = "${BinListScreen.Memory.name}/{id}",
            arguments = listOf(navArgument("id") {type = NavType.StringType})
        ) { entry ->
            val requestId = entry.arguments?.getString("id")
            val request = viewModel.getCardById(UUID.fromString(requestId))
            CardInfo(card = request, textIsStatic = true)
        }
    }
}