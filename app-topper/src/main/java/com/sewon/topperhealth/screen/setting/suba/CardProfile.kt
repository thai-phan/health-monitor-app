package com.sewon.topperhealth.screen.setting.suba

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperShapes
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.setting.suba.component.ModalAGender
import com.sewon.topperhealth.screen.setting.suba.component.ModalDate


@Composable
fun ProfileSetting(
  rowHeight: Dp,
  viewModel: ViewModelCardProfile = hiltViewModel()

) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  var openGenderModal by rememberSaveable { mutableStateOf(false) }
  var openDateModal by rememberSaveable { mutableStateOf(false) }

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
      Text(stringResource(R.string.setting_a_profile), style = topperTypography.titleMedium )
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openGenderModal = !openGenderModal }),
      ) {
        Text(stringResource(R.string.setting_a_gender))
        Row() {
          Text(uiState.gender, color = Color.White)
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
        }
      }
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)

      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
          .clickable(onClick = { openDateModal = !openDateModal }),
      ) {
        Text(stringResource(R.string.setting_a_age))
        Row() {
          Text(uiState.birthday, color = Color.White)
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Icon(Icons.Filled.ChevronRight, contentDescription = "contentDescription")
        }
      }
    }
  }

  if (openGenderModal) {
    ModalAGender(
      uiState,
      onToggleModal = { openGenderModal = !openGenderModal },
      onChangeGender = viewModel::changeGender,
    )
  }

  if (openDateModal) {
    ModalDate(
      uiState,
      onToggleModal = { openDateModal = !openDateModal },
      onSubmitBirthday = viewModel::changeBirthday,
    )
  }

}