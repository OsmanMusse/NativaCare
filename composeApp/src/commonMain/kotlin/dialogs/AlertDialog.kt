package dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

@Composable
expect fun AdaptiveAlertDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String,
    dismissText: String,
    title: String,
    text: String,
    properties: DialogProperties
)