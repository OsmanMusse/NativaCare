package datePicker


import androidx.compose.runtime.mutableStateOf
import data.local.PreferencesKeys
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import kotlinx.datetime.Instant
import kotlinx.datetime.toNSDate
import platform.Foundation.*
import platform.UIKit.*
import platform.objc.sel_registerName
import screens.settings

@OptIn(ExperimentalForeignApi::class)
class DatePickerManager internal constructor(
    private val datePicker: UIDatePicker,
    shouldPreSelect: Boolean,
    private val onSelectionChanged: (dateMillis: Long?) -> Unit,
) {

    /**
     * Pointer to the [dateSelection] method.
     */
    @OptIn(ExperimentalForeignApi::class)
    private val dateSelectionPointer get() = sel_registerName("dateSelection")

    /**
     * Dismisses the dialog.
     */
    @OptIn(BetaInteropApi::class)
    @ObjCAction
    fun dateSelection() {
        onSelectionChanged(
            stripTimeFromDate(datePicker.date).timeIntervalSince1970.toLong() * 1000
        )
    }

    val datePickerWidth = mutableStateOf(0f)
    val datePickerHeight = mutableStateOf(0f)

    init {
        if(!shouldPreSelect){
            datePicker.date = NSDate()
        } else {
            datePicker.setDate(Instant.fromEpochMilliseconds(settings.getLong(PreferencesKeys.DUE_DATE,0L)).toNSDate())
        }

        // Should Preselect
//
//        val preSelectedDate = Instant.fromEpochMilliseconds(shouldPreSelect).toNSDate()
//        if (shouldPreSelect != 0L) datePicker.date = preSelectedDate
        datePicker.locale = NSLocale.currentLocale
        datePicker.datePickerMode = UIDatePickerMode.UIDatePickerModeDate
        datePicker.minimumDate = datePicker.date
        datePicker.maximumDate = NSCalendar
            .currentCalendar()
            .dateByAddingUnit(NSCalendarUnitDay,280,datePicker.date,NSCalendarOptions.MIN_VALUE)
        datePicker.preferredDatePickerStyle = UIDatePickerStyle.UIDatePickerStyleCompact
        datePicker.addTarget(
            target = this,
            action = dateSelectionPointer,
            forControlEvents = UIControlEventValueChanged
        )
        datePicker.frame.useContents {
            datePickerWidth.value = this.size.width.toFloat()
            datePickerHeight.value = this.size.height.toFloat()
        }
    }

    private fun stripTimeFromDate(originalDate: NSDate): NSDate {
        val components = NSCalendar.currentCalendar.components(
            NSCalendarUnitYear or NSCalendarUnitMonth or NSCalendarUnitDay,
            originalDate
        )
        return NSCalendar.currentCalendar.dateFromComponents(components) ?: originalDate
    }
}