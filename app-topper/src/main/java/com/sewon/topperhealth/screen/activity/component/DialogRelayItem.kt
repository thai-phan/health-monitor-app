package com.sewon.topperhealth.screen.activity.component

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.sewon.topperhealth.MainActivity
import com.sewon.topperhealth.screen.a0common.theme.topperShapes


@SuppressLint("MissingPermission")
@Composable
fun DialogRelayItem(
  color: Color,
  bluetoothDevice: BluetoothDevice,
  onSelectDevice: () -> Unit
) {

  val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

  DisposableEffect(lifecycleOwner) {

    // When the effect leaves the Composition, remove the observer and stop scanning
    onDispose {

    }
  }

  val deviceAddress by remember {
    mutableStateOf(MainActivity.classicService.deviceAddress)
  }

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .shadow(elevation = 10.dp, spotColor = Color(0x40000000), ambientColor = Color(0x40000000))
      .background(color = color, shape = topperShapes.small)
      .padding(10.dp)
      .clickable {
        onSelectDevice()
      },
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Row {
      Icon(Icons.Filled.Bluetooth, contentDescription = "Localized description", tint = Color.Black)
      Text(
        bluetoothDevice.name ?: "N/A",
        style = TextStyle(fontWeight = FontWeight.Normal),
        color = Color.Black
      )
    }

    Text(text = bluetoothDevice.address, color = Color.Black)
    if (deviceAddress == bluetoothDevice.address) {
      Text("Connected", color = Color.Black)
    }
  }
}

