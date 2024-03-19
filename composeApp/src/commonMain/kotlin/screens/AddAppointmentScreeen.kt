package screens

import RealDatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import data.local.AppointmentFormEvent
import datePicker.rememberAdaptiveDatePickerState
import datePicker.rememberAdaptiveTimePickerState
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import dev.icerock.moko.resources.compose.colorResource
import dialogs.AdaptiveAlertDialog
import domain.Appointment
import domain.AppointmentDataSource
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.nativacare.MR
import switch.CustomSwitch
import timePicker.RealTimePicker
import verticalScrollWithScrollbar

data class AddAppointmentViewState(
    val appointmentType: String = "",
    val date: Long = 0,
    val hours: Int = 0,
    val minutes: Int = 0,
    val location: String? = null,
    val healthCareProfession: String? = null,
    val notes: String? = null,
    val shouldShowError: Boolean = false
){}

sealed class ValidationEvent {
    object Success: ValidationEvent()
    object DeletedAppointment: ValidationEvent()
}

class AddAppointmentViewModel(
    private val appointmentDataSource: AppointmentDataSource
): ViewModel() {


    private val _state = MutableStateFlow(AddAppointmentViewState())
    val state = _state.asStateFlow()

    private val validationEventChannel = Channel<ValidationEvent> {  }
    val validationEvents = validationEventChannel.receiveAsFlow()

     private fun insertAppointment(appointment: Appointment){
        viewModelScope.launch {
            appointmentDataSource.insertAppointment(appointment)
        }
    }

    private fun deleteAppointment(id: Long){
        viewModelScope.launch {
            appointmentDataSource.deleteAppointmentById(id)
            validationEventChannel.send(ValidationEvent.DeletedAppointment)
        }
    }

    fun onEvent(event: AppointmentFormEvent){
        when(event){
            is AppointmentFormEvent.AppointmentTypeChanged -> {
                _state.value = _state.value.copy(appointmentType = event.type)
            }
            is AppointmentFormEvent.DateChanged -> {
                _state.value = _state.value.copy(date = event.date)
            }
            is AppointmentFormEvent.TimeChanged -> {
                _state.value = _state.value.copy(hours = event.hours, minutes = event.minutes)
            }
            is AppointmentFormEvent.LocationChanged -> {
                _state.value = _state.value.copy(location = event.location)
            }
            is AppointmentFormEvent.HealthCareProfessionChanged -> {
                _state.value = _state.value.copy(healthCareProfession = event.profession)
            }
            is AppointmentFormEvent.NotesChanged -> {
                _state.value = _state.value.copy(notes = event.notes)
            }
            is AppointmentFormEvent.DeleteAppointment -> {
                deleteAppointment(event.id)
            }

        }
    }

    fun validateForm(id: Long? = null){
        val state = state.value
        if(state.appointmentType?.isNotEmpty() == true){
            println("SAVE DATA == ${state}")
            val appointment = Appointment(id = id ?: null,typeOfAppointment = state.appointmentType, date = state.date, hours = state.hours.toLong(), minutes = state.minutes.toLong(), location = state.location ?: "", healthCareProfession = state.healthCareProfession ?: "", notes = state.notes ?: "")
            insertAppointment(appointment)
            viewModelScope.launch { validationEventChannel.send(ValidationEvent.Success) }

        } else {
            _state.value = _state.value.copy(shouldShowError = true)
        }
    }
}

@NoLiveLiterals
data class AddAppointmentScreen(
    val appointment: Appointment?
):Screen {

    @OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class,
        ExperimentalLayoutApi::class, ExperimentalVoyagerApi::class
    )
    @Composable
    override fun Content() {

        val calendarType = remember { mutableStateOf("") }
        val typeOfAppointment = remember { mutableStateOf("") }
        val location = remember { mutableStateOf("") }
        val healthCareProfessional = remember { mutableStateOf("") }
        val notes = remember { mutableStateOf("") }
        var shouldShowDateDialog by remember  { mutableStateOf(false) }
        var shouldShowTimeDialog by remember  { mutableStateOf(false) }

        val clock = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

        val datePickerState = rememberAdaptiveDatePickerState(
            initialSelectedDateMillis = appointment?.date ?: Clock.System.now().toEpochMilliseconds(),
            initialDisplayedMonthMillis = appointment?.date ?: Clock.System.now().toEpochMilliseconds(),
        )

        val androidDatePickerState = rememberDatePickerState(
            initialDisplayedMonthMillis = appointment?.date ?: Clock.System.now().toEpochMilliseconds(),
            initialSelectedDateMillis = appointment?.date ?: Clock.System.now().toEpochMilliseconds(),
        )

        val timePickerState = rememberAdaptiveTimePickerState(
            initialHour = appointment?.hours?.toInt() ?: clock.hour,
            initialMinute = appointment?.minutes?.toInt() ?: clock.minute,
            is24Hour = false
        )

        val androidTimePickerState = rememberTimePickerState(
            initialHour =appointment?.hours?.toInt() ?: clock.hour,
            initialMinute =appointment?.minutes?.toInt() ?: clock.minute,
            is24Hour = false
        )


        val navigator = LocalNavigator.currentOrThrow
        val sharedViewModel = navigator.rememberNavigatorScreenModel{ SharedViewModel()}
        val viewModel = getViewModel("Appointment_View_Model", viewModelFactory { AddAppointmentViewModel(sharedViewModel.appModule?.appointmentDataSource!!) })
        val state by viewModel.state.collectAsState()
        var shouldShowAlertDialog  = remember { mutableStateOf(state.shouldShowError) }

        println("SHOULD SHOW ALERT DIALOG == ${shouldShowAlertDialog}")

        LaunchedEffect(androidDatePickerState.selectedDateMillis){
            println("DATE TIMESTAMP == ${androidDatePickerState.selectedDateMillis}")
            viewModel.onEvent(AppointmentFormEvent.DateChanged(androidDatePickerState.selectedDateMillis!!))
        }


            LaunchedEffect(androidTimePickerState.hour,androidTimePickerState.minute){
                println("HOUR == ${androidTimePickerState.hour} MINUTE == ${androidTimePickerState.minute}")
                viewModel.onEvent(AppointmentFormEvent.TimeChanged(androidTimePickerState.hour,androidTimePickerState.minute))
        }


        LaunchedEffect(true){
            viewModel.validationEvents.collectLatest {
                when(it){
                    is ValidationEvent.Success -> {
                        sharedViewModel.navigationStack.pop()
                        sharedViewModel.shouldShowBackBtn = false
                        navigator.pop()
                    }
                    is ValidationEvent.DeletedAppointment -> navigator.pop()
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize()){

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .verticalScrollWithScrollbar(rememberScrollState())
                    .padding(top = 0.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
                    .padding(top = 0.dp, start = 8.dp, end = 8.dp, bottom = 0.dp)
                    .positionAwareImePadding()
                    .weight(1f)
            ){

                Spacer(modifier = Modifier.height(70.dp))

                BasicTextField(
                    value = calendarType.value,
                    onValueChange = {},
                    modifier = Modifier
                        .fillMaxWidth()
                        .indicatorLine(
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor =Color.Transparent,
                                unfocusedIndicatorColor = Color.Black.copy(alpha = 0.15f)
                            ),
                            enabled = true,
                            interactionSource = MutableInteractionSource(),
                            isError = false
                        ),

                    ){
                    TextFieldDefaults.DecorationBox(
                        value = calendarType.value,
                        innerTextField = it,
                        enabled = false,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = MutableInteractionSource(),
                        isError = false,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Black.copy(alpha = 0.15f)
                        ),
                        label = null,
                        placeholder = {
                            Text("Home (Calendar)", fontSize = 15.5.sp, color = colorResource(MR.colors.p_color))
                        },
                        contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                            top = 15.dp,
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 15.dp,
                        )
                    )
                }

                BasicTextField(
                    value = typeOfAppointment.value,
                    onValueChange = {
                        typeOfAppointment.value = it
                        viewModel.onEvent(AppointmentFormEvent.AppointmentTypeChanged(it))
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .indicatorLine(
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor =Color.Transparent,
                                unfocusedIndicatorColor = Color.Black.copy(alpha = 0.15f)
                            ),
                            enabled = true,
                            interactionSource = MutableInteractionSource(),
                            isError = false
                        ),
                ){
                    TextFieldDefaults.DecorationBox(
                        value = typeOfAppointment.value,
                        innerTextField = it,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = MutableInteractionSource(),
                        isError = false,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Black.copy(alpha = 0.15f)
                        ),
                        label = null,
                        placeholder = {
                            Text("Type of appointment", fontSize = 15.5.sp, color = colorResource(MR.colors.p_color))
                        },
                        contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                            top = 18.dp,
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 18.dp,
                        ),
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Date", color = colorResource(MR.colors.p_color),fontSize = 15.5.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    if(shouldShowDateDialog){
                        DatePickerDialog(
                            confirmButton = {
                            Button(
                                onClick = {
                                    shouldShowDateDialog = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent
                                )
                            ){
                                androidx.compose.material3.Text(
                                    text = "Ok".uppercase(),
                                    color = colorResource(resource = MR.colors.primaryColor)
                                )
                            }
                        },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        shouldShowDateDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    )
                                ){
                                    androidx.compose.material3.Text(
                                        text = "Cancel".uppercase(),
                                        color = colorResource(resource = MR.colors.primaryColor)
                                    )
                                }
                            },
                            onDismissRequest = {}
                        ){
                            DatePicker(androidDatePickerState)
                        }
                    }

                    if(shouldShowAlertDialog.value){
                        AdaptiveAlertDialog(
                            title = "Empty Fields",
                            text = "Fields cannot be empty. Please ensure you have selected your preferred calendar and provided an appointment",
                            confirmText = "Ok",
                            dismissText = "",
                            onConfirm = {
                                shouldShowAlertDialog.value = false
                                println("ALERT DIALOG == ${shouldShowAlertDialog}")
                            },
                            onDismiss = {
                                shouldShowAlertDialog.value = false
                                println("ALERT DIALOG == ${shouldShowAlertDialog}")
                            },
                            properties = DialogProperties()
                        )
                    }

                    Column(
                        modifier = Modifier
                            .clickable {
                                shouldShowDateDialog = true
                            }
                            .border(BorderStroke(1.dp,Color.Transparent), shape = RoundedCornerShape(30.dp))
                            .height(50.dp)
                            .clip(shape = RoundedCornerShape(30.dp))
                            .background(colorResource(MR.colors.primaryColor)),
                        verticalArrangement = Arrangement.Center
                    ){
                        val date = Instant.fromEpochMilliseconds(androidDatePickerState.selectedDateMillis ?: Clock.System.now().toEpochMilliseconds() ).toLocalDateTime(
                            TimeZone.UTC)
                        Text(text = "${date.dayOfMonth}/0${date.monthNumber}/${date.year}",color = Color.White,modifier = Modifier.padding(horizontal = 15.dp))
                    }
//                    RealDatePicker(
//                        state = datePickerState,
//                        modifier = Modifier.fillMaxWidth(),
//                        dateFormatter = DatePickerFormatter(),
//                        colors = DatePickerDefaults.colors(),
//                        headline = null,
//                        title = null,
//                        showModeToggle = false,
//                        onConfirmRequest = {},
//                        onDismissRequest = {}
//                    )

                }



                Divider(modifier = Modifier.fillMaxWidth(), color = Color.Black.copy(alpha = 0.30f) )

                Row(
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Time", color = colorResource(MR.colors.p_color),fontSize = 15.5.sp)
                    Spacer(modifier = Modifier.width(10.dp))

                    if(shouldShowTimeDialog){
                        DatePickerDialog(
                            confirmButton = {
                                Button(
                                    onClick = {
                                        shouldShowTimeDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    )
                                ){
                                    androidx.compose.material3.Text(
                                        text = "Ok".uppercase(),
                                        color = colorResource(resource = MR.colors.primaryColor)
                                    )
                                }
                            },
                            dismissButton = {
                                Button(
                                    onClick = {
                                        shouldShowTimeDialog = false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Transparent
                                    )
                                ){
                                    androidx.compose.material3.Text(
                                        text = "Cancel".uppercase(),
                                        color = colorResource(resource = MR.colors.primaryColor)
                                    )
                                }
                            },onDismissRequest = { shouldShowTimeDialog = false }
                        ){
                            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                TimePicker(androidTimePickerState)
                            }

                        }
                    }

                    Column(
                        modifier = Modifier
                            .clickable {
                                shouldShowTimeDialog = true
                            }
                            .border(BorderStroke(1.dp,Color.Transparent), shape = RoundedCornerShape(30.dp))
                            .height(50.dp)
                            .clip(shape = RoundedCornerShape(30.dp))
                            .background(colorResource(MR.colors.primaryColor)),
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(text = "${androidTimePickerState.hour}:${androidTimePickerState.minute} PM",color = Color.White,modifier = Modifier.padding(horizontal = 15.dp))
                    }
//                    Spacer(Modifier.weight(1f))
//                    RealTimePicker(
//                        state = timePickerState,
//                        modifier = Modifier,
//                        colors = DatePickerDefaults.colors(),
//                        headline = null,
//                        title = null
//                    )

                }

                Divider(modifier = Modifier.fillMaxWidth(), color = Color.Black.copy(alpha = 0.30f))


                BasicTextField(
                    value = location.value,
                    onValueChange = {
                        location.value = it
                        viewModel.onEvent(AppointmentFormEvent.LocationChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .indicatorLine(
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor =Color.Transparent,
                                unfocusedIndicatorColor = Color.Black.copy(alpha = 0.15f)
                            ),
                            enabled = true,
                            interactionSource = MutableInteractionSource(),
                            isError = false
                        ),
                ){
                    TextFieldDefaults.DecorationBox(
                        value = location.value,
                        innerTextField = it,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = MutableInteractionSource(),
                        isError = false,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Black.copy(alpha = 0.15f)
                        ),
                        label = null,
                        placeholder = {
                            Text("Location (Optional)", fontSize = 15.5.sp, color = colorResource(MR.colors.p_color))
                        },
                        contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                            top = 18.dp,
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 18.dp,
                        ),
                    )
                }


                BasicTextField(
                    value = healthCareProfessional.value,
                    onValueChange = {
                       healthCareProfessional.value = it
                       viewModel.onEvent(AppointmentFormEvent.HealthCareProfessionChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .indicatorLine(
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor =Color.Transparent,
                                unfocusedIndicatorColor = Color.Black.copy(alpha = 0.15f)
                            ),
                            enabled = true,
                            interactionSource = MutableInteractionSource(),
                            isError = false
                        ),
                ){
                    TextFieldDefaults.DecorationBox(
                        value = healthCareProfessional.value,
                        innerTextField = it,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = MutableInteractionSource(),
                        isError = false,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Black.copy(alpha = 0.15f)
                        ),
                        label = null,
                        placeholder = {
                            Text("Healthcare Professional (Optional)", fontSize = 15.5.sp, color = colorResource(MR.colors.p_color))
                        },
                        contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                            top = 18.dp,
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 18.dp,
                        ),
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().height(40.dp).background(colorResource(MR.colors.colorTest)),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(text = "Alerts (Optional)", fontSize = 15.5.sp, modifier = Modifier.padding(start = 7.dp))
                }

                Row(modifier = Modifier.fillMaxWidth().height(55.dp), verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "1 day before",
                        color = Color.Black,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    CustomSwitch(shouldPreSelect =false, onValueChange = {})
                }

                Divider(modifier = Modifier.fillMaxWidth(), color = Color.Black.copy(alpha = 0.30f) )

                Row(modifier = Modifier.fillMaxWidth().height(55.dp), verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "1 week before",
                        color = Color.Black,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    CustomSwitch(shouldPreSelect =false, onValueChange = {})
                }


                Row(
                    modifier = Modifier.fillMaxWidth().height(40.dp).background(colorResource(MR.colors.colorTest)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Notes (Optional)", fontSize = 15.5.sp, modifier = Modifier.padding(start = 7.dp))
                }


                BasicTextField(
                    value = notes.value,
                    onValueChange = {
                       notes.value = it
                       viewModel.onEvent(AppointmentFormEvent.NotesChanged(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .indicatorLine(
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor =Color.Transparent,
                                unfocusedIndicatorColor = Color.Black.copy(alpha = 0.15f)
                            ),
                            enabled = true,
                            interactionSource = MutableInteractionSource(),
                            isError = false
                        ),
                ){
                    TextFieldDefaults.DecorationBox(
                        value = notes.value,
                        innerTextField = it,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = MutableInteractionSource(),
                        isError = false,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Black.copy(alpha = 0.15f)
                        ),
                        label = null,
                        placeholder = {
                            Text("Thinks to ask at my next appointment", fontSize = 15.5.sp, color = colorResource(MR.colors.p_color))
                        },

                        contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(
                            top = 10.dp,
                            start = 4.dp,
                            end = 4.dp,
                            bottom = 10.dp,
                        ),
                    )
                }


            }


            if(appointment?.date != null){
                // Editable Appointment
                Row(modifier = Modifier.positionAwareImePadding().fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .height(55.dp)
                            .weight(1f)
                            .background(colorResource(MR.colors.btn_dark_green))
                            .clickable(
                                onClick = {
                                    viewModel.onEvent(AppointmentFormEvent.DeleteAppointment(appointment!!.id!!))
                                },
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Delete".uppercase(),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 55.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Divider(Modifier.height(55.dp).width(2.dp).padding(vertical = 10.dp).align(Alignment.CenterVertically), color = Color.White)
                    }
                    Row(
                        modifier = Modifier
                            .height(55.dp)
                            .weight(1f)
                            .background(colorResource(MR.colors.btn_dark_green))
                            .clickable(
                                onClick = {
                                    viewModel.validateForm(appointment!!.id!!)
                                },
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Update".uppercase(),
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    }
                }
            } else {
                Row(modifier = Modifier.positionAwareImePadding().fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .height(55.dp)
                            .weight(1f)
                            .background(colorResource(MR.colors.btn_dark_green))
                            .clickable(
                                onClick = { navigator.pop() },
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Cancel".uppercase(),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(start = 55.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Divider(Modifier.height(55.dp).width(2.dp).padding(vertical = 10.dp).align(Alignment.CenterVertically), color = Color.White)
                    }
                    Row(
                        modifier = Modifier
                            .height(55.dp)
                            .weight(1f)
                            .background(colorResource(MR.colors.btn_dark_green))
                            .clickable(
                                onClick = {
                                    shouldShowAlertDialog.value = true
                                    viewModel.validateForm()
                                },
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Save".uppercase(),
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    }
                }
            }

        }



    }


}


@OptIn(ExperimentalLayoutApi::class)
fun Modifier.positionAwareImePadding() = composed {
    var consumePadding by remember { mutableStateOf(0) }
    onGloballyPositioned { coordinates ->
        val rootCoordinate = coordinates.findRootCoordinates()
        val bottom = coordinates.positionInWindow().y + coordinates.size.height

        consumePadding = (rootCoordinate.size.height - bottom).toInt()
    }
        .consumeWindowInsets(PaddingValues(bottom = consumePadding.pxToDp()))
        .imePadding()
}



@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

