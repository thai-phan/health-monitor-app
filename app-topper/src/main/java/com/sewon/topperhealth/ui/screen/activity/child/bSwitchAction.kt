package com.sewon.topperhealth.ui.screen.activity.child

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.R
import com.sewon.topperhealth.ui.common.component.CustomSwitch
import com.sewon.topperhealth.ui.screen.activity.component.DialogRelay
import com.sewon.topperhealth.service.bluetooth.ClassicClient
import com.sewon.topperhealth.service.bluetooth.LowEnergyService
import com.sewon.topperhealth.service.bluetooth.util.Connected
import com.sewon.topperhealth.service.bluetooth.util.TextUtil

@SuppressLint("MissingPermission")
@Composable
fun SwitchAction() {
  val context = LocalContext.current

  val isRelayDialog = rememberSaveable { mutableStateOf(false) }
  val isRelayClose = rememberSaveable { mutableStateOf(false) }

  val isPlaySound by LowEnergyService.isPlaySound.observeAsState()
  val deviceName by ClassicClient.deviceName.observeAsState()
  val classicConnectState by ClassicClient.connected.observeAsState()

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

  fun toggleSound(value: Boolean) {
    MainActivity.lowEnergyService.toggleSoundSleepInduce(value)
  }

  Row(
    modifier = Modifier
      .fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(
      stringResource(R.string.setting_c_sleep_inducing_energy),
      maxLines = 2,
    )

    if (classicConnectState == Connected.True) {
      CustomSwitch(
        checked = isRelayClose.value,
        onCheckedChange = {
          toggleRelay(it)
        },
      )
    } else {
      Button(
        onClick = {
          isRelayDialog.value = !isRelayDialog.value
        }) {
        Text(deviceName!!)
      }
    }
  }
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Text(stringResource(R.string.setting_c_sleep_inducing_sound))
    CustomSwitch(checked = isPlaySound!!,
      onCheckedChange = {
        toggleSound(it)
      })
  }
  if (isRelayDialog.value) {
    DialogRelay(onDismissRequest = {
      isRelayDialog.value = false
    })
  }
}