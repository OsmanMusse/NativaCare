package helpers

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlin.math.ceil

object Utils {
    fun calculateDueDate(timestamp:Long): Int {
        val currentMoment: Instant = Clock.System.now()
        val date1 = currentMoment.toLocalDateTime(TimeZone.UTC).date
        val dateSelected = Instant.fromEpochMilliseconds(timestamp).toLocalDateTime(TimeZone.UTC).date
        val diffInDays = date1.daysUntil(dateSelected)
        val weeks = 40 - ceil(diffInDays / 7.0).toInt()
        return weeks
    }
}