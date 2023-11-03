package com.sewon.topperhealth.screen.setting.subb

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperShapes
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.setting.subb.component.ModalAWakeUpTime
import com.sewon.topperhealth.screen.setting.subb.component.ModalBSleepTime
import com.sewon.topperhealth.screen.setting.subb.component.ModalCAlarmSetting

@Composable
fun SleepSetting(
  viewModel: ViewModelCardSleep = hiltViewModel(),
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  var openWakeupModal by rememberSaveable { mutableStateOf(false) }
  var openSleepTimeModal by rememberSaveable { mutableStateOf(false) }
  var openAlarmTypeModal by rememberSaveable { mutableStateOf(false) }


  val height = 45.dp
  Card(
    shape = topperShapes.small,
    colors = CardDefaults.cardColors(containerColor = Color(0x33000000))
  ) {
    Column(
      verticalArrangement = Arrangement.SpaceAround,
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)

    ) {
      Text(stringResource(R.string.setting_b_sleep_setting), style = topperTypography.titleMedium)

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(height)
          .clickable(onClick = { openWakeupModal = !openWakeupModal }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(stringResource(R.string.wakeup_time))
        Row {
          Text(uiState.wakeupTimeStr)
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Icon(
            Icons.Filled.ChevronRight,
            tint = Color.White,
            contentDescription = "contentDescription",
            modifier = Modifier.size(ButtonDefaults.IconSize)
          )
        }
      }

      Spacer(modifier = Modifier.height(5.dp))
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
      Spacer(modifier = Modifier.height(5.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(height)
          .clickable(onClick = { openSleepTimeModal = !openSleepTimeModal }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(stringResource(R.string.bedtime))
        Row {
          Text(uiState.sleepTimeStr)
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Icon(
            Icons.Filled.ChevronRight,
            tint = Color.White,
            contentDescription = "contentDescription",
            modifier = Modifier.size(ButtonDefaults.IconSize)
          )
        }
      }

      Spacer(modifier = Modifier.height(5.dp))
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
      Spacer(modifier = Modifier.height(5.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(height)
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
          Icon(
            Icons.Filled.ChevronRight,
            tint = Color.White,
            contentDescription = "contentDescription",
            modifier = Modifier.size(ButtonDefaults.IconSize)
          )
        }
      }
    }
  }


  if (openWakeupModal) {
    ModalAWakeUpTime(
      uiState,
      onToggleModal = { openWakeupModal = !openWakeupModal },
      onChangeAlarmTime = viewModel::changeSettingAlarmTime,
    )
  }

  if (openSleepTimeModal) {
    ModalBSleepTime(
      uiState,
      onToggleModal = { openSleepTimeModal = !openSleepTimeModal },
      onChangeBedTime = viewModel::changeSettingBedTime
    )
  }


  if (openAlarmTypeModal) {
    ModalCAlarmSetting(
      uiState,
      onToggleModal = { openAlarmTypeModal = !openAlarmTypeModal },
      onChangeAlarmType = viewModel::changeSettingAlarmBehavior
    )
  }
}