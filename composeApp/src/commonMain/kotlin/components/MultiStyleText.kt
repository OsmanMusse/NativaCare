package components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MultiStyleText(text1: String, color1: Color, text2: String, color2: Color,text3: String) {
    Text(buildAnnotatedString {
        withStyle(
            style = SpanStyle(color = color1, fontWeight = FontWeight.Normal, fontSize = 19.sp)
        ) {
            append(text1)
        }
        withStyle(
            style = SpanStyle(color = color2, fontWeight = FontWeight.Bold, fontSize = 19.sp)
        ) {
            append(text2)
        }

        withStyle(
            style = SpanStyle(color = color1, fontWeight = FontWeight.Normal, fontSize = 19.sp)
        ) {
            append(text3)
        }
    },modifier = Modifier.padding(13.dp))
}


@Composable
fun MultiColorText(vararg textWithColors: Pair<String, Color>) {
    Text(text = buildAnnotatedString {
        textWithColors.forEach { (text, color) ->
            withStyle(style = SpanStyle(color = color)) {
                append(text)
            }
        }
    },fontSize = 15.sp)
}