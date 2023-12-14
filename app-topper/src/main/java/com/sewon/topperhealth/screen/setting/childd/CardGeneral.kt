package com.sewon.topperhealth.screen.setting.childd

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperShapes
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.setting.childd.component.ModalADeviceAccess
import com.sewon.topperhealth.screen.setting.childd.component.ModalBClearHistory
import com.sewon.topperhealth.screen.setting.childd.component.ModalCSOSRecipient


// Card 4
@Composable
fun GeneralSetting(
  rowHeight: Dp,
  viewModel: CardGeneralViewModel = hiltViewModel()
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  var openDeviceAccessModal by rememberSaveable { mutableStateOf(false) }
  var openClearHistoryModal by rememberSaveable { mutableStateOf(false) }
  var openSOSRecipientModal by rememberSaveable { mutableStateOf(false) }
//  val context = LocalContext.current

//  val locale = remember { mutableStateOf("en") }
//  fun ssss(localeStr: String) {
//    Locale.setDefault(Locale(localeStr))
//    locale.value = localeStr
//    val config = context.resources.configuration
//    config.setLocale(Locale(localeStr))
//    context.resources.updateConfiguration(config, context.resources.displayMetrics)
//  }
//  Button(onClick = { ssss("en") }) {
//    Text("Changge en")
//  }
//  Button(onClick = { ssss("ko") }) {
//    Text("Changge ko")
//  }
//  Text(locale.value)


  Card(
    shape = topperShapes.small,
    colors = CardDefaults.cardColors(containerColor = Color(0x33000000))
  ) {
    Column(
      verticalArrangement = Arrangement.SpaceAround,
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp)

    ) {
      Text(stringResource(R.string.setting_d_general_setting), style = topperTypography.titleMedium)
      Spacer(Modifier.height(10.dp))
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openDeviceAccessModal = !openDeviceAccessModal })
      ) {
        Text(stringResource(R.string.setting_d_access))

        Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
      }
      Spacer(Modifier.height(5.dp))
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
      Spacer(Modifier.height(5.dp))
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openClearHistoryModal = !openClearHistoryModal })
      ) {
        Text(stringResource(R.string.setting_d_clear))

        Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
      }
      Spacer(Modifier.height(5.dp))
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
      Spacer(Modifier.height(5.dp))
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openSOSRecipientModal = !openSOSRecipientModal })
      ) {
        Text(stringResource(R.string.setting_d_emergency))
        Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
      }
    }
  }

  if (openDeviceAccessModal) {
    ModalADeviceAccess(
      onToggleModal = { openDeviceAccessModal = !openDeviceAccessModal },
      onChangeDeviceAccess = viewModel::onChangeAccessDevice
    )
  }

  if (openClearHistoryModal) {
    ModalBClearHistory(
      onToggleModal = { openClearHistoryModal = !openClearHistoryModal },
      onClearHistory = viewModel::onClearHistory
    )
  }

  if (openSOSRecipientModal) {
    ModalCSOSRecipient(
      uiState,
      onToggleModal = { openSOSRecipientModal = !openSOSRecipientModal },
      onChangeSOSRecipient = viewModel::onChangeSOSRecipient
    )
  }
}