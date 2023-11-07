package com.sewon.topperhealth.screen.setting.subb.component

import android.view.LayoutInflater
import android.widget.NumberPicker
import android.widget.TimePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.setting.subb.UiStateB
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBSleepTime(
  uiState: UiStateB,
  onToggleModal: () -> Unit,
  onChangeBedTime: (LocalTime) -> Unit,
) {
  val skipPartiallyExpanded by remember { mutableStateOf(false) }
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)

  val (time, setTime) = remember { mutableStateOf(uiState.sleepTime) }


  ModalBottomSheet(
    onDismissRequest = onToggleModal,
    sheetState = bottomSheetState,
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 50.dp)
    ) {
      Text(stringResource(R.string.bedtime), style = topperTypography.titleLarge)

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        AndroidView(
          modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
          factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.time_picker, null)
            val timePicker = view.findViewById<TimePicker>(R.id.timePicker)
            timePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            timePicker.hour = time.hour
            timePicker.minute = time.minute
            timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
              setTime(LocalTime.of(hourOfDay, minute))
            }
            timePicker
          }
        )

      }

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(100.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        Button(onClick = onToggleModal) {
          Text(stringResource(R.string.cancel))

        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(onClick = {
          onChangeBedTime(time)
          onToggleModal()
        }) {
          Text(stringResource(R.string.save))
        }
      }
    }
  }
}