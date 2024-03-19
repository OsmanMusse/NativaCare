package screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import components.MultiColorText
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import org.example.nativacare.MR

class FeedbackScreen: Screen {
    @Composable
    override fun Content() {

        Column(modifier = Modifier.padding(top = 90.dp, start = 15.dp, end = 10.dp)) {
            Text(
                text = "Feedback",
                color = colorResource(MR.colors.h2_color),
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Medium,
            )

            Spacer(modifier = Modifier.height(15.dp))


            Text(
                text = "How to provide feedback",
                color = colorResource(MR.colors.h2_color),
                fontSize = 17.5.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(18.dp))

            MultiColorText(
                Pair("There are different options available depending on the type of feedback you want to give. Alternatively, you can email your comments about this app to: ", colorResource(MR.colors.p_dark_color)),
                Pair("info@nativacare.com", colorResource(MR.colors.primaryColor))
            )

            Spacer(modifier = Modifier.height(15.dp))

            Divider(modifier = Modifier.fillMaxWidth(), color = colorResource(MR.colors.primaryColor))

            Row(modifier = Modifier.fillMaxWidth().clickable {  }){
                Text(
                    text = "Feedback about this app",
                    color = colorResource(MR.colors.p_dark_color),
                    fontSize = 14.85.sp,
                    modifier = Modifier.padding(top = 10.dp, start = 5.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(MR.images.feedback_device2),
                    contentDescription = null,
                    modifier = Modifier.width(100.dp).height(150.dp).padding(top = 15.dp,bottom = 15.dp, end = 25.dp)
                )
            }

            Divider(modifier = Modifier.fillMaxWidth(), color = colorResource(MR.colors.primaryColor))

        }
    }


}

