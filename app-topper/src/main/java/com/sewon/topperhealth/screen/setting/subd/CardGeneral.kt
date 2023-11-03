package com.sewon.topperhealth.screen.setting.subd

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.component.CustomSwitch
import com.sewon.topperhealth.screen.a0common.theme.topperShapes
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.setting.subd.component.ModalADeviceAccess
import com.sewon.topperhealth.screen.setting.subd.component.ModalBClearHistory
import com.sewon.topperhealth.screen.setting.subd.component.ModalCSOSRecipient


// Card 4
@Composable
fun GeneralSetting(

  viewModel: CardGeneralViewModel = hiltViewModel()
) {

  var openDeviceAccessModal by rememberSaveable { mutableStateOf(false) }
  var openClearHistoryModal by rememberSaveable { mutableStateOf(false) }
  var openSOSRecipientModal by rememberSaveable { mutableStateOf(false) }


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
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = { openDeviceAccessModal = !openDeviceAccessModal })
      ) {
        Text(stringResource(R.string.setting_d_access))

        CustomSwitch(
          modifier = Modifier.semantics { contentDescription = "Demo" },
          checked = openDeviceAccessModal,
          onCheckedChange = { openDeviceAccessModal = it })
      }
      Spacer(modifier = Modifier.height(5.dp))
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
      Spacer(modifier = Modifier.height(5.dp))
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = { openClearHistoryModal = !openClearHistoryModal })
      ) {
        Text(stringResource(R.string.setting_d_clear))

        CustomSwitch(
          modifier = Modifier.semantics { contentDescription = "Demo" },
          checked = openClearHistoryModal,
          onCheckedChange = { openClearHistoryModal = it })
      }
      Spacer(modifier = Modifier.height(5.dp))
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
      Spacer(modifier = Modifier.height(5.dp))
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .clickable(onClick = { openSOSRecipientModal = !openSOSRecipientModal })
      ) {
        Text(stringResource(R.string.setting_d_emergency))

        CustomSwitch(
          modifier = Modifier.semantics { contentDescription = "Demo" },
          checked = openSOSRecipientModal,
          onCheckedChange = { openSOSRecipientModal = it })
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
      onClearHistory = viewModel::onChangeClearHistory
    )
  }

  if (openSOSRecipientModal) {
    ModalCSOSRecipient(
      onToggleModal = { openSOSRecipientModal = !openSOSRecipientModal },
      onChangeSOSRecipient = viewModel::onChangeSOSRecipient
    )
  }
}