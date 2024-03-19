package data.local

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import domain.Appointment
import domain.AppointmentDataSource
import domain.toAppointment
//import domain.toAppointment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.example.nativacare.Database
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext




class SqlDelightAppointmentDataSource(
    db: Database
): AppointmentDataSource {
    private val queries = db.appointmentQueries

    override fun getAllAppointments(): Flow<List<Appointment>> {
        return queries
            .getAllAppointments()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map {
                it.map {
                    it.toAppointment()
                }
            }


    }

    override suspend fun insertAppointment(appointment: Appointment) {
        withContext(Dispatchers.IO){
            queries.insertAppointment(
                id = appointment.id,
                typeOfAppointment = appointment.typeOfAppointment,
                location = appointment.location,
                healthCareProfession = appointment.healthCareProfession,
                date = appointment.date,
                hours = appointment.hours,
                minutes = appointment.minutes,
                notes = appointment.notes
            )
        }
    }

    override suspend fun removeAppointments() {
        withContext(Dispatchers.IO){
            queries.removeAllAppointments()
        }
    }

    override suspend fun deleteAppointmentById(id: Long) {
        withContext(Dispatchers.IO){
            queries.deleteAppointmentById(id)
        }
    }
}