package com.sewon.topperhealth.screen.setting.childb.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.setting.childb.UiStateB


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalCAlarmSetting(
  uiState: UiStateB,
  onToggleModal: () -> Unit,
  onChangeAlarmType: (String) -> Unit
) {

  val skipPartiallyExpanded by remember { mutableStateOf(false) }
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)

  var state by remember { mutableStateOf(true) }

  ModalBottomSheet(
    onDismissRequest = onToggleModal,
    sheetState = bottomSheetState,
  ) {

    val iconOptions = listOf(
      R.drawable.ic_bell_on,
      R.drawable.ic_speaker_phone,
      R.drawable.ic_volume_off
    )

    val radioOptions = listOf("벨소리", "진동", "무음")

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(uiState.alarmBehavior) }

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 50.dp)
    ) {
      Text(stringResource(R.string.setting_b_alarm_setting), style = topperTypography.titleLarge)

      Column(
        modifier = Modifier
          .selectableGroup()
          .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        iconOptions.zip(radioOptions).forEach { pair ->
          Row(
            modifier = Modifier
              .height(56.dp)
              .selectable(
                selected = (pair.second == selectedOption),
                onClick = { onOptionSelected(pair.second) },
                role = Role.RadioButton
              )
              .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            RadioButton(
              selected = (pair.second == selectedOption),
              onClick = null // null recommended for accessibility with screenreaders
            )
            Text(
              text = pair.second,
              modifier = Modifier
                .padding(start = 16.dp)
                .width(50.dp),
              color = Color.White
            )
            Spacer(Modifier.width(10.dp))
            Image(
              painter = painterResource(id = pair.first),
              contentDescription = "Logo",
            )
          }
        }
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
          onChangeAlarmType(selectedOption)
          onToggleModal()
        }) {
          Text(stringResource(R.string.save))
        }
      }
    }


  }
}