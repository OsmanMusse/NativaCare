


package screens

import Choice
import ManagingPregnancy.ManagingPregnancy
import ScrollBarConfig
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.stack.mutableStateStackOf
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import com.russhwolf.settings.Settings
import components.MultiStyleText
import data.local.MainData
import data.local.PreferencesKeys
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import di.AppModule
//import di.AppModule
import helpers.Utils
import org.example.nativacare.MR
import verticalScrollWithScrollbar

val settings: Settings = Settings()


class SharedViewModel(): ScreenModel {
    var dueDate: Long = settings.getLong(PreferencesKeys.DUE_DATE,0L)
    var mainData: Map<String,MainData>? = null
    var navTitle: String = "Home"
    var shouldShowBackBtn: Boolean = false
    var hospitalName: String? = settings.getStringOrNull(PreferencesKeys.HOSPITAL)
    var navigationStack = mutableStateStackOf("Home")
    var appModule: AppModule? = null
}

 class HomeScreen:Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val sharedScreenModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }
        val interactionSource = remember { MutableInteractionSource() }
        sharedScreenModel.appModule = sharedScreenModel.appModule
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScrollWithScrollbar(
                    state = rememberScrollState(),
                    scrollbarConfig = ScrollBarConfig(
                        padding = PaddingValues(4.dp, 4.dp, 0.5.dp, 4.dp)
                    )
                )
                .padding(top = 80.dp, start = 10.dp, end = 10.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "NativaCare",
                fontWeight = FontWeight.Bold,
                fontSize = 27.sp,
                color = colorResource(MR.colors.logo_color)
            )

            Spacer(modifier = Modifier.height(20.dp))
//
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(MR.colors.gray_main_color))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = { navigator.push(DueDateScreen()) }
                    )
                    .padding(horizontal = 7.dp)
                    .indication(interactionSource, indication = null),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if(sharedScreenModel.dueDate == 0L){
                    Text(
                        text =  "My expected day of delivery",
                        color = colorResource(MR.colors.brown_color),
                        fontWeight = FontWeight.Medium,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(13.dp),
                    )
                } else {
                    val weeksDue = Utils.calculateDueDate(sharedScreenModel.dueDate)
                    MultiStyleText(
                        text1 = "I'm ",
                        color1 = colorResource(MR.colors.brown_color),
                        text2 = "$weeksDue",
                        color2 = colorResource(MR.colors.logo_color),
                        text3 = if (weeksDue == 1) " week pregnant" else " weeks pregnant"
                    )
                }
            }


            Spacer(modifier = Modifier.height(12.dp))


                if(sharedScreenModel.hospitalName == null){
                    Column {
                        Text(
                            text = "My individual preferences",
                            color = colorResource(MR.colors.h2_color),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(3.dp))

                        Text(
                            text = "Nativacare is here to provide you with personalised assistance whenever you need it",
                            color = colorResource(MR.colors.p_color),
                            fontSize = 15.sp
                        )
                    }
                } else {
                    Column {
                        Text(
                            text = "${sharedScreenModel.hospitalName}",
                            color = colorResource(MR.colors.h2_color),
                            fontSize = 17.5.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                }
            }


            Spacer(modifier = Modifier.height(17.dp))

            // Section 1
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//
//
//                val choice1 = Choice("My Individualised care", MR.images.your_pregnancy)
//                val choice2 = Choice("My hospital", MR.images.hospital)
//                val choice3 =
//                    Choice("My Schedule", MR.images.calendar)
//                val items = listOf(choice1, choice2, choice3)
//
//                val firstSectionColors = arrayListOf(
//                    colorResource(MR.colors.gold_main_color),
//                    colorResource(MR.colors.gold_main_color),
//                    colorResource(MR.colors.gold_main_color)
//                )
//
//                for (i in 0..2) {
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f)
//                            .height(130.dp),
//                        colors = CardDefaults.cardColors(containerColor = firstSectionColors[i])
//                    ) {
//                        Column(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .clickable(
//                                    interactionSource = MutableInteractionSource(),
//                                    indication = rememberRipple(color = Color.White)
//
//                                ) {
//                                    when(i){
//                                        0 -> {
//                                            sharedScreenModel.navigationStack.push("Care and support plans")
//                                            sharedScreenModel.shouldShowBackBtn = true
//                                            navigator.push(PersonalCareScreen())
//                                        }
//                                        1 -> {
//                                            navigator.push(MaternityScreen())
//                                        }
//
//                                        2 -> {
//                                            sharedScreenModel.navigationStack.push("My schedule")
//                                            sharedScreenModel.shouldShowBackBtn = true
//                                            navigator.push(AppointmentScreen())
//                                        }
//                                    }
//                                }
//                                .padding(
//                                    start = 10.dp,
//                                    top = 15.dp,
//                                    end = 10.dp,
//                                    bottom = 0.dp
//                                ),
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                        ) {
//                            Text(
//                                text = items[i].title,
//                                color = Color.White,
//                                fontSize = 12.sp,
//                                lineHeight = 15.sp,
//                                textAlign = TextAlign.Center
//                            )
//
//                            Spacer(modifier = Modifier.weight(1f))
//                            Icon(
//                                painter = painterResource(items[i].image),
//                                contentDescription = null,
//                                tint = Color.White,
//                                modifier = Modifier.size(68.dp)
//                            )
//                        }
//
//                    }
//
//
//                    // Prevent extra spacing on last item
//                    if (i < 2) Spacer(modifier = Modifier.width(10.dp))
//
//                }
//
//
//            }

            val choice100 = Choice("My Motherhood Journey", MR.images.your_pregnancy)
            val choice101 =
                Choice("Managing my pregnancy", MR.images.preparing_for_child_birth)
            val items100 = listOf(choice100, choice101)
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {

                for (i in 0..1) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .height(170.dp)
                            .clickable {
                                val mainData =  sharedScreenModel?.mainData?.get(if (i == 0) "My Motherhood Journey" else "Managing my pregnancy")
                                sharedScreenModel.navTitle = if (i==0) "My Motherhood Journey" else "Managing my pregnancy"
                                sharedScreenModel.navigationStack.push(sharedScreenModel.navTitle)
                                if (i==0) navigator.push(MyMotherhoodJourney()) else navigator.push(ManagingPregnancy())
//                                navigator.push(TopicsListScreen(isRootScreen = true,mainData = mainData!!))
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = if (i == 0) colorResource(MR.colors.gray_main_color) else colorResource(MR.colors.green_main_color)
                        )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(horizontal =  15.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(if(i == 1) 6.dp else 15.dp,Alignment.CenterVertically)
                        ) {
                            Icon(
                                modifier = Modifier.size(if(i != 1) 40.dp else 51.dp),
                                painter = painterResource(items100[i].image),
                                contentDescription = null,
                                tint = Color.White,
                            )

                            Text(
                                text = items100[i].title,
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


            // Section 2

            val choice4 = Choice("My pregnancy", MR.images.your_pregnancy)
            val choice5 =
                Choice("Preparing for my child birth", MR.images.preparing_for_child_birth)
            val choice6 = Choice("My Labour and birth", MR.images.labour_and_birth)
            val choice7 = Choice("My postnatal recovery with my baby", MR.images.after_your_baby_is_born)
            val choice8 = Choice("NativaCare services", MR.images.your_pregnancy)
            val choice9 =
                Choice("Digital monitoring", MR.images.digital_monitoring)

            // Sub Section
            // 1)Monitoring my contractions
            // 2)Monitoring my babies heartrate/heartbeat

            val items1 = listOf(choice4, choice5)
            val items2 = listOf(choice6, choice7)
            val items3 = listOf(choice8,choice9)

            val arrayColors = arrayListOf(
                colorResource(MR.colors.gray_main_color),
                colorResource(MR.colors.green_main_color),
                colorResource(MR.colors.pink_main_color),
                colorResource(MR.colors.malta_main_color),
                colorResource(MR.colors.gray_main_color),
                colorResource(MR.colors.green_main_color),
            )


//            Column(modifier = Modifier.padding(top = 13.dp)) {
//                Text(
//                    text = "My Motherhood Journey",
//                    color = colorResource(MR.colors.h2_color),
//                    fontSize = 17.5.sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Spacer(modifier = Modifier.height(18.dp))
//
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                ) {
//
//                    for (i in 0..1) {
//                        Card(
//                            shape = CircleShape,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .weight(1f)
//                                .height(170.dp)
//                                .clickable {
//                                    val mainData =  sharedScreenModel?.mainData?.get(if (i == 0) "My Motherhood Journey" else "Managing my pregnancy")
//                                    sharedScreenModel.navTitle = if (i==0) "My Motherhood Journey" else "Managing my pregnancy"
//                                    sharedScreenModel.navigationStack.push(sharedScreenModel.navTitle)
//                                    navigator.push(MyMotherhoodJourney())
//                                    navigator.push(TopicsListScreen(isRootScreen = true,mainData = mainData!!))
//                                },
//                            colors = CardDefaults.cardColors(
//                                containerColor = if (i == 0) arrayColors[0] else arrayColors[1]
//                            )
//                        ) {
//                            Column(
//                                modifier = Modifier.fillMaxSize(),
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                verticalArrangement = Arrangement.spacedBy(6.dp,Alignment.CenterVertically)
//                            ) {
//                                Icon(
//                                    modifier = Modifier.size(if(i != 1) 40.dp else 51.dp),
//                                    painter = painterResource(items1[i].image),
//                                    contentDescription = null,
//                                    tint = Color.White,
//                                )
//
//                                Text(
//                                    text = items1[i].title,
//                                    color = Color.White,
//                                    fontSize = 13.sp,
//                                    lineHeight = 15.sp
//                                )
//                            }
//
//                        }
//
//                        // Prevent extra spacing on last item
//                        if (i < 1) Spacer(modifier = Modifier.width(10.dp))
//                    }
//
//
//                }
//            }

            Spacer(modifier = Modifier.height(20.dp))
//            Row() {
//                for (i in 0..1) {
//                    Card(
//                        shape = CircleShape,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f)
//                            .height(170.dp)
//                            .clickable {
//                                val mainData =  sharedScreenModel?.mainData?.get(if (i == 0) "My Labour and birth" else "My postnatal recovery with my baby")
//                                sharedScreenModel.navTitle = if (i==0) "My Labour and birth" else "My postnatal recovery with my baby"
//                                sharedScreenModel.navigationStack.push(sharedScreenModel.navTitle)
//                                navigator.push(TopicsListScreen(isRootScreen = true,mainData = mainData!!))
//                            },
//                        colors = CardDefaults.cardColors(
//                            containerColor = if (i == 0) arrayColors[2] else arrayColors[3]
//                        )
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxSize(),
//                            horizontalArrangement = Arrangement.spacedBy(10.dp,Alignment.CenterHorizontally),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                modifier = Modifier.size(40.dp),
//                                painter = painterResource(items2[i].image),
//                                contentDescription = null,
//                                tint = Color.White,
//                            )
//
//                            Text(
//                                text = items2[i].title,
//                                color = Color.White,
//                                fontSize = 13.sp,
//                                lineHeight = 15.sp
//                            )
//                        }
//                    }
//
//                    // Prevent extra spacing on last item
//                    if (i < 1) Spacer(modifier = Modifier.width(10.dp))
//                }
//            }


            // NEW SECTION
//            Spacer(modifier = Modifier.height(20.dp))
//            Row() {
//                for (i in 0..1) {
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f)
//                            .height(100.dp)
//                            .clickable {
//                                val mainData =  sharedScreenModel?.mainData?.get(if (i == 0) "NativaCare services" else "Digital monitoring")
//                                sharedScreenModel.navTitle = if (i==0) "NativaCare services" else "Digital monitoring"
//                                sharedScreenModel.navigationStack.push(sharedScreenModel.navTitle)
//                                navigator.push(TopicsListScreen(isRootScreen = true,mainData = mainData!!))
//                            },
//                        colors = CardDefaults.cardColors(
//                            containerColor = if (i == 0) arrayColors[4] else arrayColors[5]
//                        )
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxSize(),
//                            horizontalArrangement = Arrangement.spacedBy(10.dp,Alignment.CenterHorizontally),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                modifier = Modifier.size(40.dp),
//                                painter = painterResource(items3[i].image),
//                                contentDescription = null,
//                                tint = Color.White,
//                            )
//
//                            Text(
//                                text = items3[i].title,
//                                color = Color.White,
//                                fontSize = 13.sp,
//                                lineHeight = 15.sp
//                            )
//                        }
//                    }
//
//                    // Prevent extra spacing on last item
//                    if (i < 1) Spacer(modifier = Modifier.width(10.dp))
//                }
//            }

            Spacer(modifier = Modifier.height(15.dp))


            Image(painterResource(MR.images.loctus_flower2),null,modifier = Modifier.fillMaxWidth().height(150.dp).offset(y = -30.dp))



            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Your feedback",
                    color = colorResource(MR.colors.h2_color),
                    fontSize = 17.5.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "Please tell us what we could improve.",
                    color = colorResource(MR.colors.p_color),
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier.fillMaxWidth().height(47.dp),
                    onClick = {
                         navigator.push(FeedbackScreen())
                         sharedScreenModel.navigationStack.push("Feedback")
                         sharedScreenModel.shouldShowBackBtn = false
                    },
                    contentPadding = PaddingValues(horizontal = 23.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.green_main_color))
                ) {
                    Text(
                        text = "Provide feedback",
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start
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
}


//   Row() {
//                for (i in 0..1) {
//
////            val choice8 = Choice("NativaCare services", MR.images.your_pregnancy)
////            val choice9 =  Choice("Digital monitoring", MR.images.digital_monitoring)
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f)
//                            .height(100.dp)
//                            .clickable {
//                                val mainData =  sharedScreenModel?.mainData?.get(if (i == 0) "NativaCare services" else "Digital monitoring")
//                                sharedScreenModel.navTitle = if (i==0) "NativaCare services" else "Digital monitoring"
//                                sharedScreenModel.navigationStack.push(sharedScreenModel.navTitle)
//                                navigator.push(TopicsListScreen(isRootScreen = true,mainData = mainData!!))
//                            },
//                        colors = CardDefaults.cardColors(
//                            containerColor = if (i == 0) arrayColors[4] else arrayColors[5]
//                        )
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxSize(),
//                            horizontalArrangement = Arrangement.spacedBy(10.dp,Alignment.CenterHorizontally),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Icon(
//                                modifier = Modifier.size(40.dp),
//                                painter = painterResource(items3[i].image),
//                                contentDescription = null,
//                                tint = Color.White,
//                            )
//
//                            Text(
//                                text = items3[i].title,
//                                color = Color.White,
//                                fontSize = 13.sp,
//                                lineHeight = 15.sp
//                            )
//                        }
//                    }
//
//                    // Prevent extra spacing on last item
//                    if (i < 1) Spacer(modifier = Modifier.width(10.dp))
//                }