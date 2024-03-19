package screens

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import domain.Appointment
import domain.AppointmentDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class AppointmentListState(
    val appointments: List<Appointment> = emptyList()
){

}
class ViewAppointmentViewModel(
    private val appointmentDataSource: AppointmentDataSource
): ViewModel() {

    init {
        getAllAppointments()
    }

    private fun getAllAppointments(){
        viewModelScope.launch {
            appointmentDataSource.getAllAppointments().collect {
                println("Items List == ${it} ")
            }
        }
    }

    private val _state = MutableStateFlow(AppointmentListState())

    val state = combine(
        _state,
        appointmentDataSource.getAllAppointments()
    ){ state, allAppointments ->
        println("NEW STATE == ${state} Appointment List == ${allAppointments}")
        state.copy(
            appointments =  allAppointments
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L),AppointmentListState())


}