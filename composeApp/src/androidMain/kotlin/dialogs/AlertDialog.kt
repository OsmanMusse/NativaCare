package dialogs

import android.util.Log
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun AdaptiveAlertDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    confirmText: String,
    dismissText: String,
    title: String,
    text: String,
    properties: DialogProperties
){
    val showShowDialog = remember { mutableStateOf(true) }


    // Show The Alert Dialog

    showShowDialog
    AlertDialog(
        onDismissRequest = {
            println("DISMISSS === ")
        },
        title = {
            Text(text = "$title",color = Color.Black, fontWeight = FontWeight.Medium)
        },
        text = {
            Text(text = "$text",color = Color.Black, fontWeight = FontWeight.Normal)
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ){
                Text("Yes".uppercase())
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )
            ){
                Text("Cancel".uppercase())
            }
        },
        properties = DialogProperties()
    )
}