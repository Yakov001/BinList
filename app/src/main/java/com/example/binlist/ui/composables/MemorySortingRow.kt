package com.example.binlist.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.binlist.ui.theme.BinListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemorySortingRow(
    dateAscending: Boolean = true,
    onDateSortSwitch: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(modifier = Modifier.weight(1F)) {
            Card(onClick = onDateSortSwitch) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    CapsText(text = "Date")
                    Spacer(Modifier.width(4.dp))
                    Icon(imageVector = if (dateAscending) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward, "Date")
                }
            }
        }
        CapsText(modifier = Modifier.weight(2F), text = "Bin")
    }
}

@Composable
@Preview
fun MemoryFilterRowPreview() {
    BinListTheme() {
        MemorySortingRow {}
    }
}

@Composable
@Preview
fun MemoryFilterRowPreviewDark() {
    BinListTheme(darkTheme = true) {
        MemorySortingRow {}
    }
}