package domain


import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import org.example.momandbaby2.AppointmentEntity
import kotlin.math.min

//import org.example.nativacare.AppointmentEntity

//import kotlin.sqldelight.AppointmentEntity

data class Appointment(
    val id: Long?,
    val typeOfAppointment: String,
    val location:String,
    val healthCareProfession: String,
    val date: Long,
    val hours: Long,
    val minutes: Long,
    val notes:String
)



fun AppointmentEntity.toAppointment(): Appointment {
    return Appointment(
        id = id,
        typeOfAppointment = typeOfAppointment,
        location = location,
        healthCareProfession = healthCareProfession,
        date = date,
        hours = hours,
        minutes = minutes,
        notes = notes
    )
}

interface AppointmentDataSource {
    fun getAllAppointments(): Flow<List<Appointment>>
    suspend fun insertAppointment(appointment: Appointment)
    suspend fun removeAppointments()
    suspend fun deleteAppointmentById(id: Long)
}

