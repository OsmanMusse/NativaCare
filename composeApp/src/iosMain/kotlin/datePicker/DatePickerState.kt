package datePicker

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver


@OptIn(ExperimentalMaterial3Api::class)
@Stable
actual class AdaptiveDatePickerState actual constructor(
    private val initialSelectedDateMillis: Long?,
    private val initialDisplayedMonthMillis: Long?,
    private val yearRange: IntRange,
    private val initialMaterialDisplayMode: DisplayMode,
    internal val initialUIKitDisplayMode: UIKitDisplayMode
) {
    internal var selectedDateMillisState by mutableStateOf(initialSelectedDateMillis)

    /**
     * A timestamp that represents the _start_ of the day of the selected date in _UTC_ milliseconds
     * from the epoch.
     *
     * In case no date was selected or provided, the state will hold a `null` value.
     *
     * @see [setSelection]
     */
    internal actual var selectedDateMillis: Long? = null
        get() = selectedDateMillisState

    /**
     * Sets the selected date.
     *
     * @param dateMillis timestamp in _UTC_ milliseconds from the epoch that represents the date
     * selection, or `null` to indicate no selection.
     *
     * @throws IllegalArgumentException in case the given timestamps do not fall within the year
     * range this state was created with.
     */
    actual fun setSelection(@Suppress("AutoBoxing") dateMillis: Long?) {
        selectedDateMillisState = dateMillis
    }

    /**
     * A mutable state of [DisplayMode] that represents the current display mode of the UI
     * (i.e. picker or input).
     */
    actual var displayMode: DisplayMode = initialMaterialDisplayMode

    actual companion object {
        /**
         * The default [Saver] implementation for [DatePickerState].
         */
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