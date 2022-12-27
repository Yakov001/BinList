package com.example.binlist.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.binlist.ui.theme.BinListTheme

@Composable
fun CardTextField(
    onTextChange: (String) -> Unit
) {
    var textFieldValueState by remember {
        mutableStateOf(
            TextFieldValue(
                text = ""
            )
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = textFieldValueState.copy(
                text = textFieldValueState.text.cardSpacedFormat(),
                selection = TextRange(textFieldValueState.text.cardSpacedFormat().length + 1)
            ),
            onValueChange = {
                onTextChange(it.text.filterNot { c -> c.isWhitespace() })
                textFieldValueState = TextFieldValue(text = it.text.filterNot { c -> c.isWhitespace() })
            },
            textStyle = CardNumberTextStyle,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            )
        )
        Text(
            text = "Enter the first digits of a card number (BIN/IIN)",
            color = Color.Gray,
            fontSize = 10.sp
        )
    }
}

fun String?.cardSpacedFormat(): String {
    val s = this
    if (s.isNullOrEmpty()) return ""
    val ans = CharArray(s.length + (s.length - 1) / 4)
    var offset = 0
    for (i in s.indices) {
        if (i > 3 && i % 4 == 0) {
            ans[i + offset] = ' '
            ans[i + 1 + offset] = s[i]
            offset++
        } else ans[i + offset] = s[i]

    }
    return String(ans)
}

val CardNumberTextStyle = TextStyle(
    color = Color.Gray,
    fontSize = 20.sp,
    textAlign = TextAlign.Center
)

@Composable
@Preview
fun CardTextFieldPreview() {
    BinListTheme() {
        Surface() {
            CardTextField {}
        }
    }
}

@Composable
@Preview
fun CardTextFieldPreviewDark() {
    BinListTheme(darkTheme = true) {
        Surface() {
            CardTextField {}
        }
    }
}