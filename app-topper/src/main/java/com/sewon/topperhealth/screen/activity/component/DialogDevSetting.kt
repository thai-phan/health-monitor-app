package com.sewon.topperhealth.screen.activity.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sewon.topperhealth.data.HealthDataStore
import com.sewon.topperhealth.screen.a0common.component.CustomSwitch
import com.sewon.topperhealth.screen.a0common.theme.BackgroundMiddle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun DialogDevMode(
  openDevMode: MutableState<Boolean>
) {
  val context = LocalContext.current

  val dataStore = HealthDataStore(context)

  val isLogShowed = remember {
    dataStore.isLogShowed
  }.collectAsState(initial = false)

  val isDimDisabled = remember {
    dataStore.isDimDisabled
  }.collectAsState(initial = false)



  Dialog(onDismissRequest = { openDevMode.value = false }) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(375.dp)
        .padding(16.dp),
      colors = CardDefaults.cardColors(BackgroundMiddle),
      shape = RoundedCornerShape(16.dp),
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text("Developer Setting")
        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Show Log")

          CustomSwitch(
            checked = isLogShowed.value,
            onCheckedChange = {
              CoroutineScope(Dispatchers.IO).launch {
                dataStore.saveShowLog(it)
              }
            })
        }

        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Disable dim screen")

          CustomSwitch(
            checked = isDimDisabled.value,
            onCheckedChange = {
              CoroutineScope(Dispatchers.IO).launch {
                dataStore.saveDisableDim(it)
              }

            })
        }
      }
    }
  }

}