package com.sewon.topperhealth.screen.device

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sewon.topperhealth.R
import com.sewon.topperhealth.common.Destinations
import com.sewon.topperhealth.screen.device.components.BluetoothWrapper
import com.sewon.topperhealth.screen.device.components.FindDevicesScreen


@SuppressLint("MissingPermission")
@Composable
fun DeviceScreen(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController(),
  viewModel: DeviceScreenViewModel = hiltViewModel()
) {
  val context = LocalContext.current

  fun selectBleDevice(bleDevice: BluetoothDevice) {
    navController.navigate("${Destinations.ACTIVITY_ROUTE}/${bleDevice.address}")
  }

  Column(
    modifier = modifier
      .statusBarsPadding()
      .systemBarsPadding()
      .fillMaxSize()
      .padding(
        vertical = 0.dp, horizontal = 20.dp
      ),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      modifier = Modifier
//        .border(BorderStroke(2.dp, Color.Red))
        .weight(2f)
        .fillMaxWidth(),
      painter = painterResource(id = R.drawable.ic_intellinest),
      contentDescription = "intellinest",
    )
    Image(
      modifier = Modifier
//        .border(BorderStroke(2.dp, Color.Red))
        .weight(1f)
        .fillMaxWidth(),
      painter = painterResource(id = R.drawable.ic_bluetooth_wing),
      contentDescription = "Logo",
    )
    BluetoothWrapper(modifier = Modifier.weight(7f)) {
      FindDevicesScreen(navController, onSelectBle = {
        selectBleDevice(it)
      })
    }
//        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))

  }

}

@Preview
@Composable
fun PreviewUserSetting() {
  DeviceScreen()
}