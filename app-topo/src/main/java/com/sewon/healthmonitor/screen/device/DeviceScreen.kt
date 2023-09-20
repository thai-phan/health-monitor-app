package com.sewon.healthmonitor.screen.device

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.screen.device.components.BluetoothWrapper
import com.sewon.healthmonitor.screen.device.components.FindDevicesScreen


@SuppressLint("MissingPermission")
@Composable
fun DeviceScreen(
  modifier: Modifier = Modifier,
  navController: NavHostController = rememberNavController(),
  viewModel: ViewModelUserSetting = hiltViewModel()
) {

  Column(
    modifier = modifier.padding(
      start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp
    ),
  ) {
    Spacer(modifier = Modifier.height(20.dp))
    Column(
      verticalArrangement = Arrangement.spacedBy(20.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_splash_icon),
        contentDescription = "Logo",
        modifier = Modifier
          .size(width = 400.dp, height = 150.dp)
      )
      Image(
        painter = painterResource(id = R.drawable.ic_bluetooth_wing),
        contentDescription = "Logo",
      )
      BluetoothWrapper {
        FindDevicesScreen(navController)
      }
    }

//        PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))

  }

}

@Preview
@Composable
fun PreviewUserSetting() {
  DeviceScreen()
}