package timePicker

import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import datePicker.AdaptiveDatePickerState
import datePicker.AdaptiveTimePickerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
expect fun RealTimePicker(
    state: AdaptiveTimePickerState,
    modifier: Modifier,
    title: @Composable (() -> Unit)?,
    headline: @Composable (() -> Unit)?,
    colors: DatePickerColors
)