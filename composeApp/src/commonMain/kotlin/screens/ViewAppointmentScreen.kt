package screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.resources.compose.colorResource
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.nativacare.MR

//import di.AppModule

class ViewAppointmentScreen:Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable


    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val sharedViewModel = navigator.rememberNavigatorScreenModel{ SharedViewModel()}
        val viewmodel = getViewModel("view_appointment_viewmodel", viewModelFactory { ViewAppointmentViewModel(sharedViewModel.appModule!!.appointmentDataSource) })
        val state by viewmodel.state.collectAsState()


        Column(
            modifier = Modifier.fillMaxSize().padding(top = 80.dp),
        ){
            if (state.appointments.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 15.dp),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(state.appointments){
                        Row(
                            modifier = Modifier
                            .fillMaxWidth()
                            .height(96.dp)
                            .clickable {
                                sharedViewModel.navigationStack.push("Add Appointments")
                                sharedViewModel.shouldShowBackBtn = false
                                println("VIEW APPOINTMENT == Hour == ${it.hours}, Minute == ${it.minutes}")
                                navigator.push(AddAppointmentScreen(it))
                            }
                        ){
                            Column(
                                modifier = Modifier
                                    .background(colorResource(MR.colors.pink_main_color),shape = RoundedCornerShape(5.dp, 0.dp, 0.dp, 5.dp))
                                    .height(96.dp)
                                    .weight(1f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                val convertedTimestamp = Instant.fromEpochMilliseconds(it.date).toLocalDateTime(
                                    TimeZone.currentSystemDefault())

                                val day = convertedTimestamp.month.name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
                                    .subSequence(0,3)

                                Text("${day} ${convertedTimestamp.dayOfMonth}", color = Color.White)
                                Spacer(modifier = Modifier.height(7.dp))
                                Text("${it.hours}:${it.minutes}", color = Color.White)
                            }

                            Column(
                                modifier = Modifier
                                    .background(colorResource(MR.colors.colorTest))
                                    .height(96.dp)
                                    .weight(2.75f)
                                    .padding(top = 15.dp, start = 15.dp)
                            ){
                                Text("${it.typeOfAppointment}",color = Color.Black)
                            }
                        }
                    }
                }
            } else {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "No Appointments found",
                        color = colorResource(MR.colors.p_color),
                        fontSize = 18.sp
                    )
                }

            }


        }
    }
}