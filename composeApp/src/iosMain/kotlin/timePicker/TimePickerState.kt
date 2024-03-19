package datePicker

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver


@Stable
@OptIn(ExperimentalMaterial3Api::class)
actual class AdaptiveTimePickerState actual constructor(
    initialHour: Int,
    initialMinute: Int,
    is24Hour: Boolean,
) {
    init {
        require(initialHour in 0..23) { "initialHour should in [0..23] range" }
        require(initialHour in 0..59) { "initialMinute should be in [0..59] range" }
    }

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