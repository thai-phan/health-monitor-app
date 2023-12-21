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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.R
import com.sewon.topperhealth.data.DataStoreManager
import com.sewon.topperhealth.screen.a0common.theme.topperShapes
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.setting.childd.component.ModalALanguage
import com.sewon.topperhealth.screen.setting.childd.component.ModalBDeviceAccess
import com.sewon.topperhealth.screen.setting.childd.component.ModalCClearHistory
import com.sewon.topperhealth.screen.setting.childd.component.ModalDSOSRecipient
import com.sewon.topperhealth.util.AppLanguage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun GeneralSetting(
  rowHeight: Dp,
  viewModel: CardGeneralViewModel = hiltViewModel()
) {
  val context = LocalContext.current

  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val dataStore = DataStoreManager(context)

  var openLanguageModal by rememberSaveable { mutableStateOf(false) }
  var openDeviceAccessModal by rememberSaveable { mutableStateOf(false) }
  var openClearHistoryModal by rememberSaveable { mutableStateOf(false) }
  var openSOSRecipientModal by rememberSaveable { mutableStateOf(false) }
  val appLanguage = dataStore.currentLanguage

  fun updateLanguage(language: AppLanguage) {
    CoroutineScope(Dispatchers.Default).launch {
      dataStore.saveSelectedLanguage(language)
    }
  }

  Card(
    shape = topperShapes.small,
    colors = CardDefaults.cardColors(containerColor = Color(0x33000000))
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
      verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
      Text(stringResource(R.string.setting_d_general_setting), style = topperTypography.titleMedium)
      Spacer(Modifier.height(5.dp))
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openLanguageModal = !openLanguageModal })
      ) {
        Text(stringResource(R.string.setting_d_language))
        Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
      }
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
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
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
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
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
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

  if (openLanguageModal) {
    ModalALanguage(
      appLanguage,
      onToggleModal = { openLanguageModal = !openLanguageModal },
      onChangeLanguage = ::updateLanguage
    )
  }

  if (openDeviceAccessModal) {
    ModalBDeviceAccess(
      onToggleModal = { openDeviceAccessModal = !openDeviceAccessModal },
      onChangeDeviceAccess = viewModel::onChangeAccessDevice
    )
  }

  if (openClearHistoryModal) {
    ModalCClearHistory(
      onToggleModal = { openClearHistoryModal = !openClearHistoryModal },
      onClearHistory = viewModel::onClearHistory
    )
  }

  if (openSOSRecipientModal) {
    ModalDSOSRecipient(
      uiState,
      onToggleModal = { openSOSRecipientModal = !openSOSRecipientModal },
      onChangeSOSRecipient = viewModel::onChangeSOSRecipient
    )
  }
}