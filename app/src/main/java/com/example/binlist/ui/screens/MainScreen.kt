package com.example.binlist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.binlist.BinListScreen
import com.example.binlist.model.CardResponse
import com.example.binlist.ui.composables.CaptionText
import com.example.binlist.ui.composables.CardTextField
import com.example.binlist.ui.composables.NullableText
import com.example.binlist.ui.composables.VariantsText
import com.example.binlist.ui.theme.BinListTheme

@Composable
fun MainScreen(
    card: CardResponse? = null,
    onButtonRequestClick: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        val cardNumber = remember { mutableStateOf("") }

        CardTextField { cardNumber.value = it }

        Row(horizontalArrangement = Arrangement.spacedBy(40.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                CaptionText(caption = "SCHEME / NETWORK") { NullableText(card?.scheme) }
                CaptionText(caption = "BRAND") { NullableText(card?.brand) }
                CaptionText(caption = "CARD NUMBER") {
                    Row() {
                        CaptionText(
                            caption = "LENGTH",
                            capsFontSize = 10.sp
                        ) { NullableText(card?.number?.length?.toString()) }
                        Spacer(modifier = Modifier.width(8.dp))
                        CaptionText(
                            caption = "LUHN",
                            capsFontSize = 10.sp
                        ) { VariantsText(texts = "Yes" to "No", first = card?.number?.luhn) }
                    }
                }
            }
            Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
                CaptionText(caption = "TYPE") {
                    VariantsText(texts = "Debit" to "Credit", first = card?.type?.equals("Debit"))
                }
                CaptionText(caption = "PREPAID") {
                    VariantsText(texts = "Yes" to "No", first = card?.prepaid)
                }
                CaptionText(caption = "COUNTRY") {
                    Column {
                        NullableText(card?.country?.name)
                        Text(
                            text = "(lat: ${card?.country?.latitude ?: "?"}, " +
                                    "lon: ${card?.country?.longitude ?: "?"})",
                            color = Color.Gray
                        )
                    }
                }
            }
        }

        CaptionText(caption = "BANK") {
            Column {
                NullableText(card?.bank?.city)
                NullableText(card?.bank?.url, isLink = true)
                NullableText(card?.bank?.phone)
            }
        }

        Button(onClick = { onButtonRequestClick(cardNumber.value) }) { Text("Request") }
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    BinListTheme() {
        Scaffold() { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                MainScreen {}
            }
        }
    }
}


@Composable
@Preview
fun MainScreenPreviewDark() {
    BinListTheme(darkTheme = true) {
        Scaffold() { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                MainScreen {}
            }
        }
    }
}