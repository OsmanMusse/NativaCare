package datePicker

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.datetime.Clock

@Composable
@ExperimentalMaterial3Api
fun rememberAdaptiveDatePickerState(
    initialSelectedDateMillis: Long? = null,
    initialDisplayedMonthMillis: Long? = initialSelectedDateMillis,
    yearRange: IntRange = DatePickerDefaults.YearRange,
    initialMaterialDisplayMode: DisplayMode = DisplayMode.Picker,
    initialUIKitDisplayMode: UIKitDisplayMode = UIKitDisplayMode.Picker,
) =
    AdaptiveDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis,
        initialDisplayedMonthMillis = initialDisplayedMonthMillis,
        yearRange = yearRange,
        initialMaterialDisplayMode = initialMaterialDisplayMode,
        initialUIKitDisplayMode = initialUIKitDisplayMode
    )


@OptIn(ExperimentalMaterial3Api::class)
@Stable
expect class AdaptiveDatePickerState(
    initialSelectedDateMillis: Long?,
    initialDisplayedMonthMillis: Long?,
    yearRange: IntRange,
    initialMaterialDisplayMode: DisplayMode,
    initialUIKitDisplayMode: UIKitDisplayMode,
) {

    /**
     * A timestamp that represents the _start_ of the day of the selected date in _UTC_ milliseconds
     * from the epoch.
     *
     * In case no date was selected or provided, the state will hold a `null` value.
     *
     * @see [setSelection]
     */
    internal var selectedDateMillis: Long?

    /**
     * Sets the selected date.
     *
     * @param dateMillis timestamp in _UTC_ milliseconds from the epoch that represents the date
     * selection, or `null` to indicate no selection.
     *
     * @throws IllegalArgumentException in case the given timestamps do not fall within the year
     * range this state was created with.
     */
    fun setSelection(dateMillis: Long?)

    /**
     * A mutable state of [DisplayMode] that represents the current display mode of the UI
     * (i.e. picker or input).
     */
    var displayMode: DisplayMode

    companion object {
        /**
         * The default [Saver] implementation for [AdaptiveDatePickerState].
         */
        fun Saver(): Saver<AdaptiveDatePickerState, *>
    }
}

@OptIn(ExperimentalMaterial3Api::class)
internal val DisplayMode.value: Int
    get() = when (this) {
        DisplayMode.Picker -> 0
        DisplayMode.Input -> 1
        else -> -1
    }

@OptIn(ExperimentalMaterial3Api::class)
internal fun displayModeFromValue(value: Int) = when (value) {
    0 -> DisplayMode.Picker
    else -> DisplayMode.Input
}

internal fun uiKitDisplayModeFromValue(value: Int) = when (value) {
    0 -> UIKitDisplayMode.Picker
    else -> UIKitDisplayMode.Wheels
}