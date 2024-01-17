package com.sewon.topperhealth.ui.screen.setting.childc.component

import android.view.LayoutInflater
import android.widget.NumberPicker
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
import com.sewon.topperhealth.ui.theme.topperTypography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalCScoreThreshold(
  onToggleModal: () -> Unit,
  onChangeScoreThreshold: () -> Unit
) {
  val skipPartiallyExpanded by remember { mutableStateOf(false) }
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)

  val mutablePicker = remember { mutableStateOf("") }

  ModalBottomSheet(
    onDismissRequest = onToggleModal,
    sheetState = bottomSheetState,
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 50.dp)
    ) {
      Text(
        stringResource(R.string.setting_c_sleep_score_thresholds),
        style = topperTypography.titleLarge
      )
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(200.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        AndroidView(
          factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.number_picker, null)
            val numberPicker = view.findViewById<NumberPicker>(R.id.number_picker)
            val data = arrayOf("80", "81")

            numberPicker.minValue = 0
            numberPicker.maxValue = data.size - 1
            numberPicker.displayedValues = data
            numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
              mutablePicker.value = data.get(newVal)
            }
            numberPicker

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
        Spacer(Modifier.width(20.dp))
        Button(onClick = {
          onChangeScoreThreshold()
          onToggleModal()
        }) {
          Text(stringResource(R.string.save))
        }
      }
    }

  }
}