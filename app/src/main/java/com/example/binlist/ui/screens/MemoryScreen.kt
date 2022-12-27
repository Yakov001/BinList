package com.example.binlist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.binlist.model.CardResponse
import com.example.binlist.ui.composables.CaptionText
import com.example.binlist.ui.composables.NullableText
import com.example.binlist.ui.composables.cardSpacedFormat
import com.example.binlist.ui.theme.BinListTheme
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MemoryScreen(
    cards: List<CardResponse>,
    showDetailedCard: (CardResponse) -> Unit
) {
    LazyColumn() {
        items(cards) { card ->
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable { showDetailedCard(card) }
                .padding(horizontal = 16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                    CaptionText(caption = "BIN") { NullableText(card.bin?.cardSpacedFormat()?.twoLineFormat()) }
                    CaptionText(caption = "Date") {
                        NullableText(card.requestTimeMillis?.let { Date(it) }
                            ?.let {
                                SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.ROOT).format(
                                    it
                                )
                            })
                    }
                }
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp))
            }
        }
    }
}

fun String.twoLineFormat() : String {
    var s = this
    if (s.length > 9) {
        return StringBuilder(s).apply { insert(10, "\n") }.toString()
    } else repeat(10 - s.length) {s = s.plus(" ")}
    return s
}

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
        requestTimeMillis = System.currentTimeMillis()
    )
}
