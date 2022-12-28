package com.example.binlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Save
import androidx.compose.ui.graphics.vector.ImageVector

enum class BinListScreen(
    val icon: ImageVector
) {
    Main ( icon = Icons.Filled.Home),
    Memory ( icon = Icons.Filled.Save);

    companion object {
        fun fromRoute(route: String?): BinListScreen =
            when (route?.substringBefore("/")) {
                Main.name -> Main
                Memory.name -> Memory
                null -> Main
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}