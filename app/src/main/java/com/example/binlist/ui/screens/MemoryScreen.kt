package com.example.binlist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.binlist.model.CardResponse
import com.example.binlist.ui.composables.*
import com.example.binlist.ui.theme.BinListTheme
import com.example.binlist.ui.theme.courierNewFamily
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MemoryScreen(
    cards: List<CardResponse>,
    showDetailedCard: (CardResponse) -> Unit
) {
    var dateAscending by rememberSaveable { mutableStateOf(false)}
    LazyColumn() {
        item {
            MemorySortingRow(
                dateAscending = dateAscending,
                onDateSortSwitch = { dateAscending = !dateAscending }
            )
        }
        items(
            if (!dateAscending) cards.sortedBy { -it.requestTimeMillis!! } else cards
        ) { card ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable { showDetailedCard(card) }
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = SimpleDateFormat("d MMM yyyy\nHH:mm", Locale.UK)
                            .format(card.requestTimeMillis!!),
                        modifier = Modifier.weight(1F)
                    )
                    Text(
                        text = card.bin?.cardSpacedFormat()?.twoLineFormat()!!,
                        fontFamily = courierNewFamily,
                        modifier = Modifier.weight(2F)
                    )
                }
                Divider(Modifier.fillMaxWidth().padding(top = 8.dp))
            }
        }
    }
}

fun String.twoLineFormat() : String {
    val s = this
    if (s.length > 9) {
        return StringBuilder(s).apply { insert(10, "\n") }.toString()
    }
    return s
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MemoryScreenPreview() {
    BinListTheme() {
        Scaffold() { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                MemoryScreen(mockCards) {}
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MemoryScreenPreviewDark() {
    BinListTheme(darkTheme = true) {
        Scaffold() { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                MemoryScreen(mockCards) {}
            }
        }
    }
}

val mockCards = List(10) {
    CardResponse(
        id = UUID.randomUUID(),
        bin = "${it}37${it}50001003",
        requestTimeMillis = System.currentTimeMillis() + (-20000000000L..0).random()
    )
}
