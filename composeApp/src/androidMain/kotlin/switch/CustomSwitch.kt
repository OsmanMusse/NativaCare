package switch

import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
actual fun CustomSwitch(
    shouldPreSelect: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(shouldPreSelect) }

    Switch(checked = checked, onCheckedChange = {
        onValueChange(it)
        checked = it
    })
}