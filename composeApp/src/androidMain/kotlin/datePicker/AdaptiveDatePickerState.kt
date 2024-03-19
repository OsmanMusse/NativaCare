package datePicker

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.NoLiveLiterals
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue


@Stable
@NoLiveLiterals
actual class AdaptiveDatePickerState @OptIn(ExperimentalMaterial3Api::class)
actual constructor(
    private val initialSelectedDateMillis: Long?,
    private val initialDisplayedMonthMillis: Long?,
    private val yearRange: IntRange,
    private val initialMaterialDisplayMode: DisplayMode,
    internal val initialUIKitDisplayMode: UIKitDisplayMode
) {
    /**
     * A timestamp that represents the _start_ of the day of the selected date in _UTC_ milliseconds
     * from the epoch.
     *
     * In case no date was selected or provided, the state will hold a `null` value.
     *
     * @see [setSelection]
     */
    internal actual var selectedDateMillis: Long?
        get() = System.currentTimeMillis()
        set(value) {}

    /**
     * Sets the selected date.
     *
     * @param dateMillis timestamp in _UTC_ milliseconds from the epoch that represents the date
     * selection, or `null` to indicate no selection.
     *
     * @throws IllegalArgumentException in case the given timestamps do not fall within the year
     * range this state was created with.
     */
    actual fun setSelection(dateMillis: Long?) {
    }

    /**
     * A mutable state of [DisplayMode] that represents the current display mode of the UI
     * (i.e. picker or input).
     */
    @OptIn(ExperimentalMaterial3Api::class)
    actual var displayMode: DisplayMode
        get() = DisplayMode.Picker
        set(value) {}

    actual companion object {
        /**
         * The default [Saver] implementation for [AdaptiveDatePickerState].
         */
        @OptIn(ExperimentalMaterial3Api::class)
        actual fun Saver(): Saver<AdaptiveDatePickerState, *> = Saver(
            save = {
                listOf(
                    it.selectedDateMillis,
                    it.yearRange.first,
                    it.yearRange.last,
                    it.displayMode.value,
                    it.initialUIKitDisplayMode.value,
                )
            },
            restore = { value ->
                AdaptiveDatePickerState(
                    initialSelectedDateMillis = value[0] as Long?,
                    initialDisplayedMonthMillis = value[0] as Long?,
                    yearRange = IntRange(value[1] as Int, value[2] as Int),
                    initialMaterialDisplayMode = displayModeFromValue(value[3] as Int),
                    initialUIKitDisplayMode = uiKitDisplayModeFromValue(value[4] as Int),
                )
            }
        )
    }
}