package com.example.binlist.ui.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    val color = MaterialTheme.colorScheme.primary
    val durationMillis = if (selected) TabFadeInAnimationDuration else TabFadeOutAnimationDuration
    val animSpec = remember {
        tween<Color>(
            durationMillis = durationMillis,
            easing = LinearEasing,
            delayMillis = TabFadeInAnimationDelay
        )
    }
    val tabTintColor by animateColorAsState(
        targetValue = if (selected) color else color.copy(alpha = InactiveTabOpacity),
        animationSpec = animSpec
    )
    Row (
        modifier = Modifier
            .clickable { onSelected() }
            .padding(16.dp)
            .animateContentSize()
            .height(TabHeight),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = tabTintColor)
        if (selected) {
            Spacer(Modifier.width(12.dp))
            Text(text = text.uppercase(), color = tabTintColor)
        }
    }
}

private val TabHeight = 56.dp
private const val InactiveTabOpacity = 0.60f

private const val TabFadeInAnimationDuration = 150
private const val TabFadeInAnimationDelay = 100
private const val TabFadeOutAnimationDuration = 100


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