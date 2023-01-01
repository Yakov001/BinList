package com.example.binlist.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.example.binlist.ui.screens.UnderlineTextStyle
import com.example.binlist.ui.theme.BinListTheme
import java.util.*

@Composable
fun CaptionText(
    caption: String,
    capsFontSize: TextUnit = TextUnit.Unspecified,
    Text: @Composable () -> Unit
) {
    Column() {
        CapsText(caption, capsFontSize)
        Text()
    }
}

@Composable
fun CapsText(text: String, fontSize: TextUnit = TextUnit.Unspecified) = Text(text = text.uppercase(),fontSize = fontSize, color = Color.Gray)

@Composable
fun NullableText(
    text: String? = null,
    isLink: Boolean? = null,
    hasIntent: Boolean = false,
    sendIntent : () -> Unit = {}
) = Text(
    text = if (isLink == true) text ?: "?" else text?.capitalize(Locale.ROOT) ?: "?",
    color = if (text != null) {
        if (isLink == true) Color.Blue else Color.Black
    } else Color.Gray,
    modifier = if (hasIntent) Modifier.clickable { sendIntent() } else Modifier,
    style = if (hasIntent) UnderlineTextStyle else LocalTextStyle.current
)

@Composable
fun VariantsText(
    texts: Pair<String, String>,
    first: Boolean? = null
) {
    Row() {
        Text(text = texts.first, color = if (first == true) Color.Black else Color.Gray)
        Text(text = " / ", color = Color.Gray)
        Text(text = texts.second, color = if (first == false) Color.Black else Color.Gray)
    }
}

@Composable
@Preview
fun CaptionTextPreview() {
    BinListTheme() {
        CaptionText(
            caption = "scheme / network",
            Text = { NullableText(text = "Visa") }
        )
    }
}

@Composable
@Preview
fun CaptionTextPreviewDark() {
    BinListTheme(darkTheme = true) {
        CaptionText(
            caption = "scheme / network",
            Text = { NullableText() }
        )
    }
}