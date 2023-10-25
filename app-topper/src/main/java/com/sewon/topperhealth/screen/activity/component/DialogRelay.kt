package com.sewon.topperhealth.screen.activity.component

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.getSystemService
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.screen.device.components.DeviceItem
import com.sewon.topperhealth.service.blc.BlcGattSocket
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

  fun composeConnectToRelay(blcDevice: BluetoothDevice) {
    try {
      Timber.tag("composeConnectToRelay").w("connecting")
      val device = adapter.getRemoteDevice(blcDevice.address)
      val blcSocket = BlcGattSocket(context, device)
      MainActivity.blcService.connect(blcSocket)
    } catch (exception: IllegalArgumentException) {
      Timber.tag("composeConnectToRelay").w(exception)
    }
  }

  Dialog(onDismissRequest = { onDismissRequest() }) {
    Card(
      modifier = Modifier
        .fillMaxHeight(0.5f)
        .padding(20.dp),
      colors = CardDefaults.cardColors(Color(0xFF404985)),
      shape = RoundedCornerShape(16.dp),
    ) {
      LazyColumn() {
        item {
          Text(text = "Saved devices", style = MaterialTheme.typography.titleSmall)
        }
        items(pairedDevices) {
          DeviceItem(
            Color(0xFFFFFFFF),
            bluetoothDevice = it,
            onSelectDevice = {
              composeConnectToRelay(it)
            }
          )
        }
      }
    }
  }
}