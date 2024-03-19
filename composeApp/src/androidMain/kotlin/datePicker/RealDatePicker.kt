import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.commandiron.wheel_picker_compose.WheelDatePicker
import com.commandiron.wheel_picker_compose.WheelDateTimePicker
import com.commandiron.wheel_picker_compose.core.WheelPickerDefaults
import datePicker.AdaptiveDatePickerState
import datePicker.rememberAdaptiveDatePickerState
import dev.icerock.moko.resources.compose.colorResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import org.example.nativacare.MR
import java.time.LocalTime
import java.time.Month
import java.time.ZoneOffset


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
    colors: DatePickerColors
) {

        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                onDismissRequest(false)
            },
        ) {
            Column(
                modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WheelDatePicker(
                    onSnappedDate = {
                        val date = LocalDate(it.year,it.month,it.dayOfMonth)
                        println("LOCAL DATE == ${LocalDate(it.year,it.month,it.dayOfMonth)}}")
                        val epoch = date.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                        state.selectedDateMillis = epoch
                        onConfirmRequest(epoch)

                    },
                    selectorProperties = WheelPickerDefaults.selectorProperties(
                        enabled = true,
                        color = Color.Transparent,
                        shape = RoundedCornerShape(0.dp),
                        border = BorderStroke(0.dp,Color.Transparent)
                    )
                )
                Spacer(modifier = modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            onDismissRequest(false)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ){
                        Text(
                            text = "Cancel".uppercase(),
                            color = colorResource(resource = MR.colors.primaryColor)
                        )
                    }

                    Button(
                        onClick = {
                            onDismissRequest(false)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        )
                    ){
                        Text(
                            text = "Ok".uppercase(),
                            color = colorResource(resource = MR.colors.primaryColor)
                        )
                    }
                }
            }

        }
}