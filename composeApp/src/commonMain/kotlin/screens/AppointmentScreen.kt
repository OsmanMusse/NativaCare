package screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.screenModel.rememberNavigatorScreenModel
import dev.icerock.moko.resources.compose.colorResource
import dev.icerock.moko.resources.compose.painterResource
import epicarchitect.calendar.compose.basis.config.rememberBasisEpicCalendarConfig
import epicarchitect.calendar.compose.datepicker.EpicDatePicker
import epicarchitect.calendar.compose.datepicker.config.rememberEpicDatePickerConfig
import epicarchitect.calendar.compose.datepicker.state.rememberEpicDatePickerState
import epicarchitect.calendar.compose.pager.config.rememberEpicCalendarPagerConfig
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.number
import org.example.nativacare.MR

class AppointmentScreen: Screen {

    @OptIn(ExperimentalFoundationApi::class, ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        var selectedDated = remember { mutableStateOf(LocalDate(2024, Month.JANUARY, 2)) }
        var scope = rememberCoroutineScope()
        val sharedViewModel = navigator.rememberNavigatorScreenModel { SharedViewModel() }

        val pickerState = rememberEpicDatePickerState(
            config = rememberEpicDatePickerConfig(
                selectionContentColor = Color.White,
                selectionContainerColor = colorResource(MR.colors.primaryColor),
                pagerConfig  = rememberEpicCalendarPagerConfig(
                    basisConfig = rememberBasisEpicCalendarConfig(
                        displayDaysOfAdjacentMonths = true, displayDaysOfWeek = true
                    )
                ),
           )
        )

        Column(
            modifier = Modifier.padding(top = 100.dp),
        ){
            Box(modifier = Modifier.padding(horizontal = 20.dp).fillMaxWidth()){
                Icon(
                    painter = painterResource(MR.images.arrow_back_ios),
                    contentDescription = null,
                    tint = colorResource(MR.colors.primaryColor),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable {
                            scope.launch {
                                val currentPage = pickerState.pagerState.pagerState.currentPage
                                if (currentPage != 1) pickerState.pagerState.pagerState.animateScrollToPage(currentPage-1)
                            }
                        }
                )
                Text(
                    text = "${selectedDated.value.month.name} ${selectedDated.value.year}",
                    modifier = Modifier.align(Alignment.Center)
                )

                Icon(
                    painter = painterResource(MR.images.arrow_back_ios),
                    contentDescription = null,
                    tint = colorResource(MR.colors.primaryColor),
                    modifier = Modifier
                        .rotate(180f)
                        .align(Alignment.CenterEnd)
                        .clickable {
                          scope.launch {
                              val currentPage = pickerState.pagerState.pagerState.currentPage
                              pickerState.pagerState.pagerState.animateScrollToPage(currentPage+1)
                          }

                    }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            EpicDatePicker(
                state = pickerState
            )


            Spacer(modifier = Modifier.height(40.dp))


            Row(modifier = Modifier.padding(horizontal = 20.dp)){
                Text(
                    text = "${selectedDated.value.month.name} Appointments",
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.width(10.dp))

                Box(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .clip(CircleShape)
                        .background(colorResource(MR.colors.primaryColor))
                        .clickable {
                            sharedViewModel.navigationStack.push("Add Appointments")
                            sharedViewModel.shouldShowBackBtn = false
                            navigator.push(AddAppointmentScreen(null))
                        }
                ){
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.Center),
                        tint = Color.White
                    )
                }
            }


            Column(modifier = Modifier.weight(1f)){}


            Button(
                onClick = {
                    sharedViewModel.navigationStack.push("View Appointments")
                    sharedViewModel.shouldShowBackBtn = false
                    navigator.push(ViewAppointmentScreen())
                },
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(MR.colors.btn_dark_green))
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(MR.images.search_icon_ios),
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "View All".uppercase(),
                        fontSize = 16.sp,
                        modifier = Modifier
                    )
                }


            }

            LaunchedEffect(pickerState.selectedDates){
                if (pickerState.selectedDates.isNotEmpty()){
                    val currentDateSelected = pickerState.selectedDates[0]
                    println("PAGER SELECTED == ${currentDateSelected.year}")
                    selectedDated.value = LocalDate(2024,currentDateSelected.monthNumber,currentDateSelected.dayOfMonth)
                    pickerState.pagerState.pagerState.animateScrollToPage(currentDateSelected.month.number-1)
                }
            }

            LaunchedEffect(pickerState.pagerState.currentMonth.month){
                val currentMonthSelected = pickerState.pagerState.currentMonth.month
                val currentYearSelected = 2024
                println("PAGER MOVED == ${pickerState.pagerState.currentMonth} ${if(pickerState.selectedDates.isNotEmpty()) pickerState.selectedDates[0].year else Unit} ")
                selectedDated.value = LocalDate(currentYearSelected,currentMonthSelected,1)
            }


            println("Pager == ${pickerState.pagerState.currentMonth.month.ordinal} ${pickerState.pagerState.currentMonth.year}")

        }
    }

}