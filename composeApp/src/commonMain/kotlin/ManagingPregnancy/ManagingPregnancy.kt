package ManagingPregnancy

import Choice
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import dev.icerock.moko.resources.compose.colorResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import dev.icerock.moko.resources.compose.painterResource
import org.example.nativacare.MR
import screens.AppointmentScreen
import screens.MaternityScreen
import screens.PersonalCareScreen
import screens.SharedViewModel

class ManagingPregnancy:Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val sharedScreenModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }

        Column(modifier = Modifier.fillMaxWidth().padding(top = 80.dp)) {

            Column(modifier = Modifier.padding(top = 0.dp, start = 10.dp, end = 10.dp)) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                    Image(painterResource(MR.images.loctus_flower2),null,modifier = Modifier.width(150.dp).height(150.dp))
                }
                Spacer(modifier = Modifier.height(25.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    val choice1 = Choice("My Individualised care", MR.images.your_pregnancy)
                    val choice2 = Choice("My hospital", MR.images.hospital)
                    val choice3 =
                        Choice("My Schedule", MR.images.calendar)
                    val items = listOf(choice1, choice2, choice3)

                    val firstSectionColors = arrayListOf(
                        colorResource(MR.colors.gold_main_color),
                        colorResource(MR.colors.gold_main_color),
                        colorResource(MR.colors.gold_main_color)
                    )

                    for (i in 0..2) {
                        Card(
                            shape = CircleShape,
                            modifier = Modifier
                                .size(150.dp)
                                .clickable(
                                        interactionSource = MutableInteractionSource(),
                                        indication = rememberRipple(color = Color.White)

                                    ) {
                                        when (i) {
                                            0 -> {
                                                sharedScreenModel.navigationStack.push("Care and support plans")
                                                sharedScreenModel.shouldShowBackBtn = true
                                                navigator.push(PersonalCareScreen())
                                            }

                                            1 -> {
                                                navigator.push(MaternityScreen())
                                            }

                                            2 -> {
                                                sharedScreenModel.navigationStack.push("My schedule")
                                                sharedScreenModel.shouldShowBackBtn = true
                                                navigator.push(AppointmentScreen())
                                            }
                                        }
                                    },
                            colors = CardDefaults.cardColors(
                                containerColor = firstSectionColors[i]
                            )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(horizontal =  15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(if(i == 1) 6.dp else 15.dp,Alignment.CenterVertically)
                            ) {
                                Icon(
                                    modifier = Modifier.size(if(i != 1) 40.dp else 51.dp),
                                    painter = painterResource(items[i].image),
                                    contentDescription = null,
                                    tint = Color.White,
                                )

                                Text(
                                    text = items[i].title,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    lineHeight = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }

                        // Prevent extra spacing on last item
                        if (i < 2) Spacer(modifier = Modifier.width(10.dp))
                    }


                }

                // Section 1
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//
//
//                    val choice1 = Choice("My Individualised care", MR.images.your_pregnancy)
//                    val choice2 = Choice("My hospital", MR.images.hospital)
//                    val choice3 =
//                        Choice("My Schedule", MR.images.calendar)
//                    val items = listOf(choice1, choice2, choice3)
//
//                    val firstSectionColors = arrayListOf(
//                        colorResource(MR.colors.gold_main_color),
//                        colorResource(MR.colors.gold_main_color),
//                        colorResource(MR.colors.gold_main_color)
//                    )
//
//                    for (i in 0..2) {
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .weight(1f)
//                                .height(130.dp),
//                            colors = CardDefaults.cardColors(containerColor = firstSectionColors[i])
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clickable(
//                                        interactionSource = MutableInteractionSource(),
//                                        indication = rememberRipple(color = Color.White)
//
//                                    ) {
//                                        when (i) {
//                                            0 -> {
//                                                sharedScreenModel.navigationStack.push("Care and support plans")
//                                                sharedScreenModel.shouldShowBackBtn = true
//                                                navigator.push(PersonalCareScreen())
//                                            }
//
//                                            1 -> {
//                                                navigator.push(MaternityScreen())
//                                            }
//
//                                            2 -> {
//                                                sharedScreenModel.navigationStack.push("My schedule")
//                                                sharedScreenModel.shouldShowBackBtn = true
//                                                navigator.push(AppointmentScreen())
//                                            }
//                                        }
//                                    }
//                                    .padding(
//                                        start = 10.dp,
//                                        top = 15.dp,
//                                        end = 10.dp,
//                                        bottom = 0.dp
//                                    ),
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                            ) {
//                                Text(
//                                    text = items[i].title,
//                                    color = Color.White,
//                                    fontSize = 12.sp,
//                                    lineHeight = 15.sp,
//                                    textAlign = TextAlign.Center
//                                )
//
//                                Spacer(modifier = Modifier.weight(1f))
//                                Icon(
//                                    painter = painterResource(items[i].image),
//                                    contentDescription = null,
//                                    tint = Color.White,
//                                    modifier = Modifier.size(68.dp)
//                                )
//                            }
//
//                        }
//
//
//                        // Prevent extra spacing on last item
//                        if (i < 2) Spacer(modifier = Modifier.width(10.dp))
//
//                    }
//                }
            }

        }

    }
}
