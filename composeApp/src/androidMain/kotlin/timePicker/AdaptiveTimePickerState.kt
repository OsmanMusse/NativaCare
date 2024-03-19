package datePicker

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue

@Stable
actual class AdaptiveTimePickerState actual constructor(
    initialHour: Int,
    initialMinute: Int,
    is24Hour: Boolean
) {

    internal actual var minuteState by mutableStateOf(initialMinute)
    internal actual var hourState by mutableStateOf(initialHour)
    internal actual var is24hourState by mutableStateOf(is24Hour)

    actual val minute: Int get() = minuteState
    actual val hour: Int get() = hourState
    actual val is24hour: Boolean get() = is24hourState

    actual companion object {
        /**
         * The default [Saver] implementation for [TimePickerState].
         */
        actual fun Saver(): Saver<AdaptiveTimePickerState, *> = Saver(
        save = {
            listOf(
                it.hour,
                it.minute,
                it.is24hour
            )
        },
        restore = { value ->
            AdaptiveTimePickerState(
                initialHour = value[0] as Int,
                initialMinute = value[1] as Int,
                is24Hour = value[2] as Boolean
            )
        }
        )


    }

}