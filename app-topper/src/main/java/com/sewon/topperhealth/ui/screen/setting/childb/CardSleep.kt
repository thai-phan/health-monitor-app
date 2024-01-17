package com.sewon.topperhealth.ui.screen.setting.childb

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.R
import com.sewon.topperhealth.ui.theme.topperShapes
import com.sewon.topperhealth.ui.theme.topperTypography
import com.sewon.topperhealth.ui.screen.setting.childb.component.ModalAWakeUpTime
import com.sewon.topperhealth.ui.screen.setting.childb.component.ModalBSleepTime
import com.sewon.topperhealth.ui.screen.setting.childb.component.ModalCAlarmSetting

@Composable
fun SleepSetting(
  rowHeight: Dp,
  viewModel: CardSleepViewModel = hiltViewModel(),
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  var openWakeupModal by rememberSaveable { mutableStateOf(false) }
  var openSleepTimeModal by rememberSaveable { mutableStateOf(false) }
  var openAlarmTypeModal by rememberSaveable { mutableStateOf(false) }

  Card(
    shape = topperShapes.small,
    colors = CardDefaults.cardColors(containerColor = Color(0x33000000))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      verticalArrangement = Arrangement.spacedBy(5.dp)

    ) {
      Text(stringResource(R.string.setting_b_sleep_setting), style = topperTypography.titleMedium)
      Spacer(Modifier.height(5.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openWakeupModal = !openWakeupModal }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(stringResource(R.string.wakeup_time))
        Row {
          Text(uiState.wakeupTimeStr)
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
        }
      }
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openSleepTimeModal = !openSleepTimeModal }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(stringResource(R.string.bedtime))
        Row {
          Text(uiState.sleepTimeStr)
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
        }
      }
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = {
            openAlarmTypeModal = !openAlarmTypeModal
          }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(stringResource(R.string.setting_b_alarm_setting))
        Row {
          Text(uiState.alarmBehavior)
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
        }
      }
    }
  }


  if (openWakeupModal) {
    ModalAWakeUpTime(
      uiState,
      onToggleModal = { openWakeupModal = !openWakeupModal },
      onChangeWakeupTime = viewModel::changeWakeupTime,
    )
  }

  if (openSleepTimeModal) {
    ModalBSleepTime(
      uiState,
      onToggleModal = { openSleepTimeModal = !openSleepTimeModal },
      onChangeBedTime = viewModel::changeBedTime
    )
  }


  if (openAlarmTypeModal) {
    ModalCAlarmSetting(
      uiState,
      onToggleModal = { openAlarmTypeModal = !openAlarmTypeModal },
      onChangeAlarmType = viewModel::changeAlarmBehavior
    )
  }
}