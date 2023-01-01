package com.example.binlist.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.binlist.model.CardResponse
import com.example.binlist.ui.composables.*
import com.example.binlist.ui.theme.BinListTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    card: CardResponse? = null,
    onButtonRequestClick: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        val cardNumber = remember { mutableStateOf("") }

        CardTextField { cardNumber.value = it }

        CardInfo(card = card)

        Button(onClick = { onButtonRequestClick(cardNumber.value) }) { Text("Request") }
    }
}

@Composable
fun CardInfo(
    modifier: Modifier = Modifier,
    card: CardResponse? = null,
    textIsStatic: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        if (textIsStatic) {
            CardStaticTextField(text = card!!.bin!!)
        }
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
                        if (card?.country?.latitude != null && card.country.longitude != null) {
                            val intentLocation = Intent(Intent.ACTION_VIEW, Uri.parse("geo:${card.country.latitude},${card.country.longitude}"))
                            val context = LocalContext.current
                            Text(
                                text = "(lat: ${card.country.latitude}, lon: ${card.country.longitude})",
                                color = Color.Black,
                                modifier = Modifier.clickable { context.startActivity(Intent.createChooser(intentLocation, null)) },
                                style = UnderlineTextStyle
                            )
                        } else {
                            Text(text = "(lat: ? lon: ?)", color = Color.Gray)
                        }
                    }
                }
            }
        }

        CaptionText(caption = "BANK") {
            Column {
                NullableText(card?.bank?.city)

                if (card?.bank?.url != null) {
                    val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse("http://${card.bank.url}"))
                    val context = LocalContext.current
                    NullableText(
                        card.bank.url,
                        isLink = true,
                        hasIntent = true,
                        sendIntent = { context.startActivity(Intent.createChooser(intentWeb, null)) }
                    )
                } else {
                    NullableText()
                }

                if (card?.bank?.phone != null) {
                    val intentCall = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${card.bank.phone}"))
                    val context = LocalContext.current
                    NullableText(
                        card.bank.phone,
                        hasIntent = true,
                        sendIntent = { context.startActivity(Intent.createChooser(intentCall, null)) }
                    )
                } else {
                    NullableText()
                }
            }
        }
    }
}

val UnderlineTextStyle = TextStyle(
    textDecoration = TextDecoration.Underline
)

@OptIn(ExperimentalMaterial3Api::class)
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


@OptIn(ExperimentalMaterial3Api::class)
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