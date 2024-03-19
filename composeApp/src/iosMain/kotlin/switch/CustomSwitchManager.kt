package switch

import dev.icerock.moko.resources.getUIColor
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCAction
import kotlinx.cinterop.useContents
import platform.UIKit.UIControlEventValueChanged
import platform.UIKit.UISwitch
import platform.objc.sel_registerName
import kotlin.experimental.ExperimentalObjCName
import androidx.compose.runtime.mutableStateOf
import org.example.nativacare.MR

@OptIn(ExperimentalForeignApi::class)
class CustomSwitchManager internal constructor(
    private val customSwitch: UISwitch,
    private val shouldPreSelect: Boolean,
    private val onSelectChange: (Boolean) -> Unit
) {

    private val dateSelectionPointer get() = sel_registerName("onSelectionChanged")

    @OptIn(ExperimentalObjCName::class)
    @ObjCAction
    fun onSelectionChanged() {
        onSelectChange(customSwitch.isOn())
    }

    val customSwitchWidth =  mutableStateOf(0f)
    val customSwitchHeight = mutableStateOf(0f)
    init {

        customSwitch.onTintColor = MR.colors.primaryColor.getUIColor()
        customSwitch.setOn(shouldPreSelect)
        customSwitch.addTarget(
            target = this,
            action = dateSelectionPointer,
            forControlEvents = UIControlEventValueChanged
        )



        customSwitch.frame.useContents {
            customSwitchWidth.value = this.size.width.toFloat()
            customSwitchHeight.value = this.size.height.toFloat()
        }
    }
}