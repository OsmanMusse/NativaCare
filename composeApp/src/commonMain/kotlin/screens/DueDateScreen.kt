package screens

import RealDatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import components.MultiStyleText
import data.local.PreferencesKeys
import datePicker.AdaptiveDatePickerState
import datePicker.UIKitDisplayMode
import datePicker.rememberAdaptiveDatePickerState
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import helpers.Utils
import kotlinx.datetime.LocalDate
import org.example.nativacare.MR
import switch.CustomSwitch



class DueDateScreen: Screen {

    @Composable
    fun initialSetup(datePickerState: AdaptiveDatePickerState, onSwitchChange: (Boolean) -> Unit){
        val dueDate = settings.getLong(PreferencesKeys.DUE_DATE,0L)
        dueDate.also { miliseconds ->
            if (dueDate == 0L) return
                datePickerState.setSelection(miliseconds)
                onSwitchChange(true)

        }

    }
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        println("STEP === 1 ")
        val sharedScreenModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }

        println("STEP === 2")
        var switchChecked by remember { mutableStateOf(false) }
        var addDueDate by remember { mutableStateOf("Add your due date")  }
        var initialLoad = remember { mutableStateOf(true) }
        var showDateDialog by remember { mutableStateOf(false) }
        var duedate by remember { mutableStateOf("") }
        var androidDate by remember { mutableStateOf(value=10000000000) }
        var state = rememberAdaptiveDatePickerState()
        val mutableInteractionSource = remember{ MutableInteractionSource() }
        println("STEP === 3")

        println("STEP === 4")

        initialSetup(datePickerState = state, onSwitchChange = { switchChecked = it })
        println("STEP === 5")


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = 80.dp)
        ){
            if(showDateDialog){
                println("BUTTON CLICKED 1")
                    RealDatePicker(
                        state = state,
                        modifier = Modifier.fillMaxWidth(),
                        dateFormatter = DatePickerFormatter(),
                        colors = DatePickerDefaults.colors(),
                        headline = null,
                        title = null,
                        showModeToggle = initialLoad.value && settings.getLong(PreferencesKeys.DUE_DATE,0L) != 0L,
                        onConfirmRequest = {
                           println("LOCAL DATE CONFIRMED == ${it}")
                            androidDate = it

                        },
                        onDismissRequest = {
                            showDateDialog = false
                        }
                    )
            }

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
            ){
                Text(
                    text = "Your Pregnancy",
                    color = colorResource(MR.colors.h2_color),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Are you pregnant?",
                        color = colorResource(MR.colors.blackish_color),
                        fontSize = 16.5.sp,
                        modifier = Modifier.weight(1f)
                    )
                    println("STEP === 6")
                        CustomSwitch(
                            shouldPreSelect = switchChecked,
                            onValueChange = { isChecked ->
                                println("SWITCH VALUE == ${switchChecked}")
                                if (!isChecked) {
                                    addDueDate = "Add your due date"
//                                    state.setSelection(null)
                                }
                                switchChecked = isChecked
                            }
                        )
                }


                Spacer(modifier = Modifier.height(10.dp))

                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(MR.colors.thumbColor)
                )

            }


            Spacer(modifier = Modifier.height(30.dp))


            if(switchChecked){
                // Hideable Section
                Column {
                    Text(
                        text = "Set my due date:",
                        color = colorResource(MR.colors.blackish_color),
                        fontSize = 16.5.sp,
                        modifier = Modifier.padding(horizontal = 15.dp)
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                        Button(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp).height(50.dp),
                            onClick = {
                                println("BUTTON CLICKED")
                               showDateDialog = true
                            },
                            contentPadding = PaddingValues(horizontal = 0.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.primaryColor))
                        ) {
                            Text(
                                text = if(duedate == "") "Set date" else duedate,
                                modifier = Modifier.fillMaxWidth().weight(1f),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                                textAlign = TextAlign.Center
                            )
                        }

                    Column(modifier = Modifier.fillMaxWidth().background(Color.Black),horizontalAlignment = Alignment.Start) {
//                        RealDatePicker(
//                            state = state,
//                            modifier = Modifier.fillMaxWidth(),
//                            dateFormatter = DatePickerFormatter(),
//                            colors = DatePickerDefaults.colors(),
//                            headline = null,
//                            title = null,
//                            showModeToggle = initialLoad.value && settings.getLong(PreferencesKeys.DUE_DATE,0L) != 0L
//                        )

                        println("STEP === 8")
                        initialLoad.value = false


                        LaunchedEffect(state.selectedDateMillis){

                            if(state.selectedDateMillis == null) addDueDate = "Add your due date"
                            else {
                                println("LOCAL DATE LONG == ${androidDate}")
                                // Pass the due date to Shared Preferences
                               settings.putLong(PreferencesKeys.DUE_DATE, if(androidDate == 10000000000) state.selectedDateMillis!! else androidDate)

                               val weeks = Utils.calculateDueDate(if(androidDate == 10000000000) state.selectedDateMillis!! else androidDate)
                                sharedScreenModel.dueDate = if(androidDate == 10000000000) state.selectedDateMillis!! else androidDate
                                println("LOCAL DATE CONVERTED TO WEEKS == ${weeks} MILISECONDS == ${if(androidDate == 10000000000) state.selectedDateMillis!! else androidDate}")
                                addDueDate = "$weeks"
                            }
                        }
                    }





                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = mutableInteractionSource,
                                indication = null,
                                onClick = { }
                            )
                            .padding(horizontal = 15.dp)
                            .border(
                                width = 1.25.dp,
                                color = Color.Black.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(corner = CornerSize(3.5.dp))
                            )
                            .indication(interactionSource = mutableInteractionSource, indication = null),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if(state.selectedDateMillis == null){
                            Text(
                                text = addDueDate,
                                color = colorResource(MR.colors.brown_color),
                                fontWeight = FontWeight.Normal,
                                fontSize = 19.sp,
                                modifier = Modifier.padding(13.dp)
                            )
                        } else {
                            MultiStyleText(
                                text1 = "I'm ",
                                color1 = colorResource(MR.colors.brown_color),
                                text2 = "${addDueDate}",
                                color2 = colorResource(MR.colors.primaryColor),
                                text3 = if(addDueDate == "1") " week pregnant" else " weeks pregnant"
                            )
                        }

                    }


                }
            }


            Column(
                modifier = Modifier.weight(1f,)
            ) {}

            Button(
                modifier = Modifier.fillMaxWidth().height(47.dp),
                onClick = {
                   navigator.popUntil { navigator.lastItem is HomeScreen }
                },
                shape = RectangleShape,
                contentPadding = PaddingValues(horizontal = 13.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.green_main_color))
            ) {
                Text(
                    text = "Continue".uppercase(),
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
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
