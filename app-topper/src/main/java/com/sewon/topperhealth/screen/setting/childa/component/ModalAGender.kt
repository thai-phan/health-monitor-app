package com.sewon.topperhealth.screen.setting.childa.component

import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.sewon.topperhealth.screen.setting.childa.UiStateA


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalAGender(
  uiState: UiStateA,
  onToggleModal: () -> Unit,
  onChangeGender: (value: String) -> Unit,
) {
  val skipPartiallyExpanded by remember { mutableStateOf(false) }
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)
  val genderOption = arrayOf("남성", "여성")
  val genderInit = if (uiState.gender != "") uiState.gender else "남성"
  val (gender, setGender) = remember { mutableStateOf(genderInit) }

  ModalBottomSheet(
    onDismissRequest = onToggleModal,
    sheetState = bottomSheetState,
  ) {
    Column(modifier = Modifier.padding(horizontal = 50.dp)) {
      Text(stringResource(R.string.setting_a_gender), style = topperTypography.titleLarge)

      Row(
        modifier = Modifier
          .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
      ) {

        AndroidView(
          factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.number_picker, null)
            val picker = view.findViewById<NumberPicker>(R.id.number_picker)
            picker.minValue = 0
            picker.maxValue = genderOption.size - 1
            picker.displayedValues = genderOption
            picker.value = genderOption.indexOf(gender)
            picker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS;
            picker.setOnValueChangedListener { _, _, newVal ->
              setGender(genderOption[newVal])
            }
            picker
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
          onChangeGender(gender)
          onToggleModal()
        }) {
          Text(stringResource(R.string.save))
        }
      }
    }

  }
}