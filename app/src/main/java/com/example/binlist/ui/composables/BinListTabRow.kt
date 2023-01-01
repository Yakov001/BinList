package com.example.binlist.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.binlist.BinListScreen
import com.example.binlist.ui.theme.BinListTheme

@Composable
fun BinListTabRow(
    onTabSelected: (BinListScreen) -> Unit,
    currentScreen: BinListScreen
) {
    Surface(
        modifier = Modifier
            .height(TabHeight)
            .fillMaxWidth()
    ) {
        Row() {
            BinListScreen.values().forEach { screen ->
                BinListTab(
                    text = screen.name,
                    icon = screen.icon,
                    onSelected = { onTabSelected(screen) },
                    selected = currentScreen == screen
                )
            }
        }
    }
}

@Composable
fun BinListTab(
    text: String,
    icon: ImageVector,
    onSelected: () -> Unit,
    selected : Boolean
) {
    Row (
        modifier = Modifier
            .padding(16.dp)
            .height(TabHeight)
            .clickable { onSelected() }
    ) {
        Icon(imageVector = icon, contentDescription = text)
        if (selected) {
            Spacer(Modifier.width(12.dp))
            Text(text = text.uppercase())
        }
    }
}

private val TabHeight = 56.dp


@Composable
@Preview
fun BinListTabPreview() {
    BinListTheme() {
        BinListTabRow(onTabSelected = {}, currentScreen = BinListScreen.Main)
    }
}

@Composable
@Preview
fun BinListTabPreviewDark() {
    BinListTheme(darkTheme = true) {
        BinListTabRow(onTabSelected = {}, currentScreen = BinListScreen.Main)
    }
}