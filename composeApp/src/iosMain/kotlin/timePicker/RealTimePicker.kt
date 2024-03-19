package timePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import datePicker.AdaptiveDatePickerState
import datePicker.AdaptiveTimePickerState
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIDatePicker


@OptIn(ExperimentalForeignApi::class, ExperimentalMaterial3Api::class)
@Composable
actual fun RealTimePicker(
    state: AdaptiveTimePickerState,
    modifier: Modifier,
    title: @Composable (() -> Unit)?,
    headline: @Composable (() -> Unit)?,
    colors: DatePickerColors,
) {
    val datePicker = remember {
        UIDatePicker()
    }
    val timePickerManager = remember {
        TimePickerManager(
            datePicker = datePicker,
            initialMinute = state.minute,
            initialHour = state.hour,
            is24Hour = state.is24hour,
            onHourChanged = { hour ->
                state.hourState = hour
            },
            onMinuteChanged = { minute ->
                state.minuteState = minute
            }
        )
    }


    UIKitView(
        factory = {
            datePicker
        },
        modifier = modifier.background(Color.Blue)
            .then(
                if (timePickerManager.datePickerWidth.value > 0f)
                    Modifier.width(timePickerManager.datePickerWidth.value.dp)
                else
                    Modifier
            )
            .then(
                if (timePickerManager.datePickerHeight.value > 0f)
                    Modifier.height(timePickerManager.datePickerHeight.value.dp)
                else
                    Modifier
            )
    )
}