package screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import org.example.nativacare.MR
import verticalScrollWithScrollbar

class PersonalCareScreen: Screen {

    @Composable
    override fun Content() {
        Column(modifier = Modifier.fillMaxSize().verticalScrollWithScrollbar(rememberScrollState()).padding(top = 100.dp,start = 17.5.dp, end = 17.5.dp)){
            Text(
                text = "Tailored Care and Support Packages",
                color = colorResource(MR.colors.h2_color),
                fontSize = 22.5.sp,
                fontWeight = FontWeight.Medium,
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Explore our comprehensive range of personalised plans designed to cater to your unique preferences and address both your physical and emotional requirements throughout pregnancy, labour, and childbirth. Additionally, these plans extend support for you and your newborn during the initial weeks postpartum.",
                color = colorResource(MR.colors.p_color),
                fontSize = 14.85.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Individualised care and Support Packages",
                color = colorResource(MR.colors.h2_color),
                fontSize = 19.sp,
                fontWeight = FontWeight.Medium,
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(modifier = Modifier.fillMaxWidth()){

                Button(
                    modifier = Modifier.fillMaxWidth().height(47.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.green_main_color))
                ){
                    Text(
                        text = "Individualised birth",
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        color = Color.Black
                    )

                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(MR.images.arrow_right),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    modifier = Modifier.fillMaxWidth().height(47.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.gray_main_color))
                ){
                    Text(
                        text = "Health and wellbeing in my pregnancy",
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        color = Color.Black
                    )

                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(MR.images.arrow_right),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    modifier = Modifier.fillMaxWidth().height(47.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.pink_main_color))
                ){

                    Text(
                        text = "My postnatal recovery with my baby",
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        color = Color.Black
                    )

                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(MR.images.arrow_right),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }


                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    modifier = Modifier.fillMaxWidth().height(47.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.malta_main_color))
                ){
                    Text(
                        text = "Birth reflections",
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        color = Color.Black
                    )

                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(MR.images.arrow_right),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Column(modifier = Modifier.fillMaxWidth()){
                Text(
                    text = "Follow these steps for effective use of personalized care and support plans",
                    color = colorResource(MR.colors.h2_color),
                    fontSize = 17.5.sp,
                    fontWeight = FontWeight.Bold,
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Your unique choices for pregnancy, childbirth, and early parenthood can be explored, understood, and documented with the aid of individualised care and support plans. Consult the app's relevant parts as you progress through the customised care and support programmes. It is always possible to modify your preferences.It is suggested that you communicate your plans and preferences to your health professionals throughout your pregnancy, as they can assist you with finishing or modifying your individualised care and support plans. A plan is just that—a plan—and in order to guarantee that the care you receive is always of the highest calibre and is safe, it may occasionally need to be reviewed and adjusted to better suit your requirements and those of your baby.",
                    fontSize = 14.85.sp,
                    color = colorResource(MR.colors.p_color)
                )


            }

        }
    }
}