package com.sewon.topperhealth.screen.activity.component

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.getSystemService
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.screen.a0common.theme.BackgroundTop
import com.sewon.topperhealth.screen.a0common.theme.topperShapes
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.service.bluetooth.ClassicClient
import com.sewon.topperhealth.service.bluetooth.ClassicGatt
import com.sewon.topperhealth.service.bluetooth.util.Connected
import timber.log.Timber

@SuppressLint("InlinedApi", "MissingPermission")
@RequiresPermission(Manifest.permission.BLUETOOTH_SCAN)
@Composable
fun DialogRelay(onDismissRequest: () -> Unit) {
  val context = LocalContext.current

  val adapter = checkNotNull(context.getSystemService<BluetoothManager>()?.adapter)

  val pairedDevices = remember {
    mutableStateListOf<BluetoothDevice>(*adapter.bondedDevices.toTypedArray())
  }

  fun composeConnectToRelay(classicDevice: BluetoothDevice) {
    try {
      val device = adapter.getRemoteDevice(classicDevice.address)
      val classicGatt = ClassicGatt(context, device)
      MainActivity.classicService.connect(classicGatt)
      ClassicClient.connected.value = Connected.Pending
      ClassicClient.deviceAddress.value = device.address
      ClassicClient.deviceName.value = device.name

    } catch (exception: Exception) {
      Timber.tag("composeConnectToRelay").w(exception)
    }
  }

  Dialog(onDismissRequest = onDismissRequest) {
    Card(
      colors = CardDefaults.cardColors(BackgroundTop),
      modifier = Modifier
        .fillMaxHeight(0.7f),
      shape = topperShapes.medium,
    ) {

      LazyColumn(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        item {
          Text("Saved devices", style = topperTypography.titleLarge)
          Spacer(modifier = Modifier.height(10.dp))
        }
        items(pairedDevices) {
          DialogRelayItem(
            Color(0xFFFFFFFF),
            bluetoothDevice = it,
            onSelectDevice = {
              composeConnectToRelay(it)
            }
          )
        }
        item {
          Spacer(modifier = Modifier.height(20.dp))
          Button(onClick = { onDismissRequest() }) {
            Text("Close")
          }
        }
      }
    }
  }
}