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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.screen.a0common.theme.checkedBorderColor
import com.sewon.topperhealth.screen.a0common.theme.checkedThumbColor
import com.sewon.topperhealth.screen.a0common.theme.checkedTrackColor
import com.sewon.topperhealth.screen.a0common.theme.uncheckedBorderColor
import com.sewon.topperhealth.screen.a0common.theme.uncheckedThumbColor
import com.sewon.topperhealth.screen.a0common.theme.uncheckedTrackColor
import com.sewon.topperhealth.screen.activity.component.DialogRelay
import com.sewon.topperhealth.service.bluetooth.util.TextUtil

@SuppressLint("MissingPermission")
@Composable
fun SwitchAction(

) {

  val context = LocalContext.current

  val switchColors: SwitchColors = SwitchDefaults.colors(
    checkedThumbColor = checkedThumbColor,
    checkedTrackColor = checkedTrackColor,
    checkedBorderColor = checkedBorderColor,
    uncheckedThumbColor = uncheckedThumbColor,
    uncheckedTrackColor = uncheckedTrackColor,
    uncheckedBorderColor = uncheckedBorderColor,
  )

  val isPlaySoundSleepInduceUI by MainActivity.lowEnergyService.isPlaySoundSleepInduce

  val isRelayDialog = rememberSaveable { mutableStateOf(false) }
  val isRelayClose = rememberSaveable { mutableStateOf(false) }

  val deviceName by remember {
    mutableStateOf(MainActivity.classicService.deviceName)
  }

  fun toggleRelay(value: Boolean) {
    isRelayClose.value = value

    if (value) {
      val byteArray = TextUtil.fromHexString("A0 01 01 A2")
      MainActivity.classicService.writeFromService(context, byteArray)
    } else {
      val byteArray = TextUtil.fromHexString("A0 01 00 A1")
      MainActivity.classicService.writeFromService(context, byteArray)
    }
  }

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = { isRelayDialog.value = !isRelayDialog.value }),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text("수면유도에너지")

    if (deviceName !== "") {
      Text(deviceName)
    } else {
      Text("No Connection")
    }

    Switch(checked = isRelayClose.value,
      colors = switchColors,
      onCheckedChange = {
        toggleRelay(it)
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
        MainActivity.lowEnergyService.toggleSoundSleepInduce()
      })
  }

  if (isRelayDialog.value) {
    DialogRelay(onDismissRequest = {
      isRelayDialog.value = false
    })
  }
}