package switch

import androidx.compose.runtime.Composable

@Composable
expect fun CustomSwitch(
    shouldPreSelect: Boolean,
    onValueChange: (Boolean) -> Unit
)