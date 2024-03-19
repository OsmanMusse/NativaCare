package components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import org.example.nativacare.MR

@Composable

fun DefaultBtn(title: String, containerColor: Color, shape: Shape, textAlign: TextAlign, onClick: () -> Unit){
    Button(
        modifier = Modifier.fillMaxWidth().height(47.dp),
        onClick = onClick,
        shape = shape,
        contentPadding = PaddingValues(horizontal = 0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor)
    ) {
        Text(
            text = title,
            modifier = Modifier.fillMaxWidth().weight(1f),
            fontWeight = FontWeight.Normal,
            textAlign = textAlign
        )

        Icon(
            modifier = Modifier.size(25.dp),
            painter = painterResource(MR.images.arrow_right),
            contentDescription = null,
            tint = Color.White
        )
    }
}