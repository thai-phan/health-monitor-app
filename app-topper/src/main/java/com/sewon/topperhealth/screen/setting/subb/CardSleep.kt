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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.screen.setting.subb.component.ModalCAlarmSetting
import com.sewon.topperhealth.screen.setting.subb.component.ModalBSleepTime
import com.sewon.topperhealth.screen.setting.subb.component.ModalAWakeUpTime

@Composable
fun SleepSetting(
  switchColors: SwitchColors = SwitchDefaults.colors(),
  viewModel: ViewModelCardSleep = hiltViewModel(),
) {

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  var openWakeupModal by rememberSaveable { mutableStateOf(false) }
  var openSleepTimeModal by rememberSaveable { mutableStateOf(false) }
  var openAlarmTypeModal by rememberSaveable { mutableStateOf(false) }


  val height = 45.dp
  Card(
    shape = RoundedCornerShape(size = 10.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0x33000000))
  ) {
    Column(
      verticalArrangement = Arrangement.SpaceAround,
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)

    ) {
      Text(
        "수면설정", fontSize = 18.sp, fontWeight = FontWeight(900), color = Color(0xFFEDEDED)
      )

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(height)
          .clickable(onClick = { openWakeupModal = !openWakeupModal }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text("기상알람시간")
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
        Text("취침시간")
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
        Text("알람설정")
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
      onChangeAlarmTime = viewModel::changeSettingAlarmTime,
      onToggleModal = { openWakeupModal = !openWakeupModal })
  }

  if (openSleepTimeModal) {
    ModalBSleepTime(
      uiState,
      onChangeBedTime = viewModel::changeSettingBedTime,
      onToggleModal = { openSleepTimeModal = !openSleepTimeModal })
  }


  if (openAlarmTypeModal) {
    ModalCAlarmSetting(
      uiState,
      onChangeAlarmType = viewModel::changeSettingAlarmBehavior,
      onToggleModal = { openAlarmTypeModal = !openAlarmTypeModal })
  }
}