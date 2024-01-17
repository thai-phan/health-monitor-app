package com.sewon.topperhealth.ui.screen.device

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.R

import com.sewon.topperhealth.ui.screen.device.components.BleWrapper
import com.sewon.topperhealth.ui.screen.device.components.BleDevices
import com.sewon.topperhealth.service.bluetooth.LowEnergyClient


@SuppressLint("MissingPermission")
@Composable
fun DeviceScreen(
  modifier: Modifier = Modifier,
  goToActivity: () -> Unit,
) {
  val context = LocalContext.current


  fun selectBleDevice(bleDevice: BluetoothDevice) {
    LowEnergyClient.deviceAddress.value = bleDevice.address
    LowEnergyClient.deviceName.value = bleDevice.name
    goToActivity()
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(
        vertical = 20.dp, horizontal = 20.dp
      ),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {

    Image(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth(),
      painter = painterResource(id = R.drawable.ic_intellinest_white),
      contentDescription = "intellinest",
    )

    Image(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth(),
      painter = painterResource(id = R.drawable.ic_bluetooth_wing),
      contentDescription = "Logo",
    )

    BleWrapper(modifier = Modifier.weight(6f)) {
      BleDevices {
        selectBleDevice(it)
      }
    }
//        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
  }
}

//@Preview
//@Composable
//fun PreviewUserSetting() {
//  DeviceScreen()
//}