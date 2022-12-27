package com.example.binlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Save
import androidx.compose.ui.graphics.vector.ImageVector

enum class BinListScreen(
    val icon: ImageVector
) {
    Main ( icon = Icons.Filled.Home),
    Memory ( icon = Icons.Filled.Save)
}