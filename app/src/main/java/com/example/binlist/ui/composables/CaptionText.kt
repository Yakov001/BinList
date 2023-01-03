package com.example.binlist.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
        CapsText(fontSize = capsFontSize, text = caption)
        Text()
    }
}

@Composable
fun CapsText(
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    text: String
) = Text(
    text = text.uppercase(),
    fontSize = fontSize,
    color = MaterialTheme.colorScheme.tertiary,
    modifier = modifier
)

@Composable
fun NullableText(
    text: String? = null,
    isLink: Boolean? = null,
    hasIntent: Boolean = false,
    sendIntent: () -> Unit = {}
) = Text(
    text = if (isLink == true) text ?: "?" else text?.capitalize(Locale.ROOT) ?: "?",
    color = if (text != null) MaterialTheme.colorScheme.secondary
        else MaterialTheme.colorScheme.secondary.copy(alpha = 0.3F),
    modifier = if (hasIntent) Modifier.clickable { sendIntent() } else Modifier,
    style = if (hasIntent) UnderlineTextStyle else LocalTextStyle.current
)

@Composable
fun VariantsText(
    texts: Pair<String, String>,
    first: Boolean? = null
) {
    Row() {
        val color = MaterialTheme.colorScheme.secondary
        Text(text = texts.first, color = if (first == true) color else color.copy(alpha = 0.3F))
        Text(text = " / ", color = color.copy(alpha = 0.3F))
        Text(text = texts.second, color = if (first == false) color else color.copy(alpha = 0.3F))
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