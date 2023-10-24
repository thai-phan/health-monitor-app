package com.sewon.topperhealth.screen.activity.sub

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.screen.a0common.theme.checkedBorderColor
import com.sewon.topperhealth.screen.a0common.theme.checkedThumbColor
import com.sewon.topperhealth.screen.a0common.theme.checkedTrackColor
import com.sewon.topperhealth.screen.a0common.theme.uncheckedBorderColor
import com.sewon.topperhealth.screen.a0common.theme.uncheckedThumbColor
import com.sewon.topperhealth.screen.a0common.theme.uncheckedTrackColor
import com.sewon.topperhealth.screen.activity.component.DialogRelay

@SuppressLint("MissingPermission")
@Composable
fun SwitchAction(

) {
  val switchColors: SwitchColors = SwitchDefaults.colors(
    checkedThumbColor = checkedThumbColor,
    checkedTrackColor = checkedTrackColor,
    checkedBorderColor = checkedBorderColor,
    uncheckedThumbColor = uncheckedThumbColor,
    uncheckedTrackColor = uncheckedTrackColor,
    uncheckedBorderColor = uncheckedBorderColor,
  )

  val isPlaySoundSleepInduceUI by MainActivity.bleServiceHandler.isPlaySoundSleepInduce

  val isRelayDialog = rememberSaveable { mutableStateOf(false) }
  val isRelayClose = rememberSaveable { mutableStateOf(false) }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = { isRelayDialog.value = !isRelayDialog.value }),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text("수면유도에너지")

    if (isRelayClose.value) {
      Text("Connected")
    } else {
      Text("No Connection")
    }

    Switch(checked = isRelayClose.value,
      colors = switchColors,
      onCheckedChange = {
        isRelayClose.value = !isRelayClose.value
      })
  }

  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text("수면유도사운드")
    Switch(checked = isPlaySoundSleepInduceUI,
      colors = switchColors,
      onCheckedChange = {
        MainActivity.bleServiceHandler.toggleSoundSleepInduce()
      })
  }

  if (isRelayDialog.value) {
    DialogRelay(onDismissRequest = {
      isRelayDialog.value = false
    })
  }
}