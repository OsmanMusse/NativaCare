package screens

import Choice
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import org.example.nativacare.MR
import verticalScrollWithScrollbar

class MyMotherhoodJourney: Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val sharedScreenModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }

        val arrayColors = arrayListOf(
            colorResource(MR.colors.gray_main_color),
            colorResource(MR.colors.green_main_color),
            colorResource(MR.colors.pink_main_color),
            colorResource(MR.colors.malta_main_color),
            colorResource(MR.colors.gray_main_color),
            colorResource(MR.colors.green_main_color),
        )

        val choice4 = Choice("My pregnancy", MR.images.your_pregnancy)
        val choice5 =
            Choice("Preparing for my child birth", MR.images.preparing_for_child_birth)
        val choice6 = Choice("My Labour and birth", MR.images.labour_and_birth)
        val choice7 = Choice("My postnatal recovery with my baby", MR.images.after_your_baby_is_born)
        val choice8 = Choice("NativaCare services", MR.images.your_pregnancy)
        val choice9 =
            Choice("Digital monitoring", MR.images.digital_monitoring)


        val items1 = listOf(choice4, choice5)
        val items2 = listOf(choice6, choice7)
        val items3 = listOf(choice8,choice9)

        Column(modifier = Modifier.verticalScrollWithScrollbar(rememberScrollState()).padding(top = 75.dp)) {
            Column(modifier = Modifier.padding(top = 0.dp, start = 10.dp, end = 10.dp)) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                    Image(painterResource(MR.images.loctus_flower2),null,modifier = Modifier.width(150.dp).height(150.dp))
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {

                    for (i in 0..1) {
                        Card(
                            shape = CircleShape,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.50f)
                                .height(170.dp)
                                .clickable {
                                    val mainData =  sharedScreenModel?.mainData?.get(if (i == 0) "My pregnancy" else "Preparing for my child birth")
                                    sharedScreenModel.navTitle = if (i==0) "My pregnancy" else "Preparing for my child birth"
                                    sharedScreenModel.navigationStack.push(sharedScreenModel.navTitle)
                                    navigator.push(TopicsListScreen(isRootScreen = true,mainData = mainData!!))
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (i == 0) arrayColors[0] else arrayColors[1]
                            )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(horizontal =  15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(if(i == 1) 6.dp else 15.dp,Alignment.CenterVertically)
                            ) {
                                Icon(
                                    modifier = Modifier.size(if(i != 1) 40.dp else 51.dp),
                                    painter = painterResource(items1[i].image),
                                    contentDescription = null,
                                    tint = Color.White,
                                )

                                Text(
                                    text = items1[i].title,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    lineHeight = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }

                        // Prevent extra spacing on last item
                        if (i < 1) Spacer(modifier = Modifier.width(10.dp))
                    }


                }

                Spacer(modifier = Modifier.height(20.dp))
                Row() {
                    for (i in 0..1) {
                        Card(
                            shape = CircleShape,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.50f)
                                .height(170.dp)
                                .clickable {
                                    val mainData =  sharedScreenModel?.mainData?.get(if (i == 0) "My Labour and birth" else "My postnatal recovery with my baby")
                                    sharedScreenModel.navTitle = if (i==0) "My Labour and birth" else "My postnatal recovery with my baby"
                                    sharedScreenModel.navigationStack.push(sharedScreenModel.navTitle)
                                    navigator.push(TopicsListScreen(isRootScreen = true,mainData = mainData!!))
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (i == 0) arrayColors[2] else arrayColors[3]
                            )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(horizontal =  15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(if(i == 1) 6.dp else 15.dp,Alignment.CenterVertically)
                            ) {
                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    painter = painterResource(items2[i].image),
                                    contentDescription = null,
                                    tint = Color.White,
                                )

                                Text(
                                    text = items2[i].title,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    lineHeight = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        // Prevent extra spacing on last item
                        if (i < 1) Spacer(modifier = Modifier.width(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {

                    for (i in 0..1) {
                        Card(
                            shape = CircleShape,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.50f)
                                .height(170.dp)
                                .clickable {
                                    val mainData =  sharedScreenModel?.mainData?.get(if (i == 0) "NativaCare services" else "Digital monitoring")
                                    sharedScreenModel.navTitle = if (i==0) "NativaCare services" else "Digital monitoring"
                                    sharedScreenModel.navigationStack.push(sharedScreenModel.navTitle)
//                                    if (i==0) navigator.push(MyMotherhoodJourney()) else navigator.push(MyMotherhoodJourney())
                                    navigator.push(TopicsListScreen(isRootScreen = true,mainData = mainData!!))
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = if (i == 0) arrayColors[4] else arrayColors[5]
                            )
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(horizontal =  15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(if(i == 1) 6.dp else 15.dp,Alignment.CenterVertically)
                            ) {
                                Icon(
                                    modifier = Modifier.size(if(i != 1) 40.dp else 51.dp),
                                    painter = painterResource(items3[i].image),
                                    contentDescription = null,
                                    tint = Color.White,
                                )

                                Text(
                                    text = items3[i].title,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    lineHeight = 15.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }

                        // Prevent extra spacing on last item
                        if (i < 1) Spacer(modifier = Modifier.width(10.dp))
                    }


                }

            }
        }

    }

}