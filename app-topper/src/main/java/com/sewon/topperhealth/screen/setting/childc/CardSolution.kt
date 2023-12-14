package com.sewon.topperhealth.screen.setting.childc

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
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperShapes
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.setting.childc.component.ModalAInduceEnergy
import com.sewon.topperhealth.screen.setting.childc.component.ModalBInduceSound
import com.sewon.topperhealth.screen.setting.childc.component.ModalCScoreThreshold


// Card 3
@Composable
fun InductionSolutionSetting(
  rowHeight: Dp,
  viewModel: CardSolutionViewModel = hiltViewModel(),
) {
  var openInduceEnergyModal by rememberSaveable { mutableStateOf(false) }
  var openInduceSoundModal by rememberSaveable { mutableStateOf(false) }
  var openScoreThresholdModal by rememberSaveable { mutableStateOf(false) }


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
      Text(
        stringResource(R.string.setting_c_sleep_inducing_solution),
        style = topperTypography.titleMedium
      )
      Spacer(Modifier.height(10.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openInduceEnergyModal = !openInduceEnergyModal }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(stringResource(R.string.setting_c_sleep_inducing_energy))
        Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
      }
      Spacer(Modifier.height(5.dp))
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
      Spacer(Modifier.height(5.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openInduceSoundModal = !openInduceSoundModal }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(stringResource(R.string.setting_c_sleep_inducing_sound))
        Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
      }
      Spacer(Modifier.height(5.dp))
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
      Spacer(Modifier.height(5.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openScoreThresholdModal = !openScoreThresholdModal }),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(stringResource(R.string.setting_c_sleep_score_thresholds))
        Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
      }
    }
  }

  if (openInduceEnergyModal) {
    ModalAInduceEnergy(
      onToggleModal = {
        openInduceEnergyModal = !openInduceEnergyModal
      },
      onChangeInduceEnergy = viewModel::changeInduceEnergy
    )
  }

  if (openInduceSoundModal) {
    ModalBInduceSound(
      onToggleModal = { openInduceSoundModal = !openInduceSoundModal },
      onChangeInduceSound = viewModel::changeInduceSound
    )
  }

  if (openScoreThresholdModal) {
    ModalCScoreThreshold(
      onToggleModal = { openScoreThresholdModal = !openScoreThresholdModal },
      onChangeScoreThreshold = viewModel::changeScoreThreshold
    )
  }
}