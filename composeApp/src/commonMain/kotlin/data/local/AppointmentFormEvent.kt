package data.local

sealed class AppointmentFormEvent{
    data class AppointmentTypeChanged(val type: String):AppointmentFormEvent()
    data class DateChanged(val date: Long):AppointmentFormEvent()
    data class TimeChanged(val hours: Int,val minutes: Int):AppointmentFormEvent()
    data class LocationChanged(val location: String):AppointmentFormEvent()
    data class HealthCareProfessionChanged(val profession: String):AppointmentFormEvent()
    data class NotesChanged(val notes: String):AppointmentFormEvent()
    data class DeleteAppointment(val id: Long): AppointmentFormEvent()
}
