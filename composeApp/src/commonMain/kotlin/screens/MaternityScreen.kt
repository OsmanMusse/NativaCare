package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import components.MultiColorText
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import org.example.nativacare.MR
import verticalScrollWithScrollbar

data class RelatedLinksModel(
    val title: String,
    val icon: ImageResource? = null,
)

class MaternityScreen : Screen {
    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val sharedViewModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScrollWithScrollbar(rememberScrollState())
                    .background(Color.White)
                    .weight(1f,false)
                    .padding(top = 100.dp)
            ) {

                Column(modifier = Modifier.padding(horizontal = 16.dp).background(Color.White)) {

                    Text(
                        text = "Explore hospital units in your area",
                        color = colorResource(MR.colors.h2_color),
                        fontSize = 22.5.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(end = 14.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    MultiColorText(
                        Pair(
                            first = "If your hospital location is already listed in this application, choose it from",
                            second = colorResource(MR.colors.p_color)
                        ),
                        Pair(
                            first = " Find my hospital area",
                            second = colorResource(MR.colors.blackish_color)
                        ),
                        Pair(
                            first = " to access your preferred maternity unit and to obtain local contacts and information. If your area is not displayed, you can explore ",
                            second = colorResource(MR.colors.p_color)
                        ),
                        Pair(
                            first = "Find your hospital services",
                            second = colorResource(MR.colors.primaryColor)
                        ),
                        Pair(
                            first = " to locate your nearest units; you will still have access to all the advice and guidance provided within the application.",
                            second = colorResource(MR.colors.p_color),
                        )
                    )

                    Spacer(modifier = Modifier.height(18.dp))


                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "What to Consider?",
                        color = colorResource(MR.colors.h2_color),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Numerous women choose to schedule their care with the maternity unit closest to them, and there are several advantages to selecting this option:",
                        color = colorResource(MR.colors.p_color),
                        fontSize = 15.sp,
                    )

                    val item1 =
                        "You are more likely to receive care from a small team of doctor and midwife, conveniently located near your home."
                    val item2 =
                        "You are more likely to become acquainted with one midwife and a cohesive team, which can enhance your maternity experience"
                    val item3 = "where you have previously received maternity or other healthcare services"

                    val list = arrayOf(item1, item2, item3)

                    Spacer(modifier = Modifier.height(15.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                        list.forEach {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.Top)
                                        .offset(y = 7.dp)
                                        .padding(start = 16.dp, end = 8.dp)
                                        .size(8.dp)
                                        .background(
                                            colorResource(MR.colors.primaryColor),
                                            shape = CircleShape
                                        ),
                                )

                                Spacer(modifier = Modifier.width(18.dp))

                                Text(
                                    text = "$it",
                                    color = colorResource(MR.colors.p_color),
                                    fontSize = 15.sp,
                                    modifier = Modifier.align(Alignment.Top),
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "If your area is not displayed in the app, you can visit the website of your local hospital unit to discover the essential services it provides",
                        color = colorResource(MR.colors.p_color),
                        fontSize = 15.sp,
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                }

                Spacer(modifier = Modifier.height(30.dp))

            }

            Button(
                onClick = {
                    sharedViewModel.navigationStack.push("Find my hospital area")
                    sharedViewModel.shouldShowBackBtn = true
                    navigator.push(FindMyAreaScreen())
                },
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.green_main_color))
            ) {
                Text(
                    text = "Change Area".uppercase(),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                )
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(MR.images.arrow_right),
                    contentDescription = null,
                    tint = Color.White
                )
            }

        }
    }


}