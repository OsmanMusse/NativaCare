import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import data.local.PreferencesKeys
import datePicker.AdaptiveDatePickerState
import datePicker.DatePickerManager
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toNSDate
import platform.Foundation.NSDate
import platform.Foundation.NSSelectorFromString
import platform.MapKit.MKMapView
import platform.UIKit.UIControlEventEditingChanged
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UIDatePicker
import platform.UIKit.UITextField
import screens.settings

@OptIn(ExperimentalForeignApi::class, ExperimentalMaterial3Api::class)
@Composable
actual fun RealDatePicker(
    state: AdaptiveDatePickerState,
    modifier: Modifier,
    dateFormatter: DatePickerFormatter,
    title: @Composable (() -> Unit)?,
    headline: @Composable (() -> Unit)?,
    showModeToggle: Boolean,
    onConfirmRequest: (miliSeconds: Long) -> Unit,
    onDismissRequest: (Boolean) -> Unit,
    colors: DatePickerColors,
) {
    val datePicker = remember {
        UIDatePicker()
    }
    val datePickerManager = remember {
        DatePickerManager(
            datePicker = datePicker,
            shouldPreSelect = showModeToggle,
            onSelectionChanged = { dateMillis ->
                state.setSelection(dateMillis = dateMillis)
            }
        )
    }



    UIKitView(
        factory = {
            datePicker
        },
        modifier = modifier.background(Color.Blue)
            .then(
                if (datePickerManager.datePickerWidth.value > 0f)
                    Modifier.width(datePickerManager.datePickerWidth.value.dp)
                else
                    Modifier
            )
            .then(
                if (datePickerManager.datePickerHeight.value > 0f)
                    Modifier.height(datePickerManager.datePickerHeight.value.dp)
                else
                    Modifier
            )
    )
}