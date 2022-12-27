package com.example.binlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.binlist.ui.composables.BinListTabRow
import com.example.binlist.ui.screens.MainScreen
import com.example.binlist.ui.screens.MemoryScreen
import com.example.binlist.ui.theme.BinListTheme
import com.example.binlist.viewModel.MainViewModel

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
    val currentScreen = remember { mutableStateOf(BinListScreen.Main) }

    val mainViewModel: MainViewModel = viewModel()

    BinListTheme {
        Scaffold(
            topBar = {
                BinListTabRow(
                    onTabSelected = { currentScreen.value = it },
                    currentScreen = currentScreen.value
                )
            }
        ) { innerPadding ->
            Box(
                Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                when (currentScreen.value) {
                    BinListScreen.Main -> {
                        val cardResponse = mainViewModel.cardResponse.collectAsState()
                        MainScreen(
                            card = cardResponse.value.data,
                            onButtonRequestClick = { mainViewModel.requestBin(it) }
                        )
                    }
                    BinListScreen.Memory -> MemoryScreen()
                }
            }
        }
    }
}