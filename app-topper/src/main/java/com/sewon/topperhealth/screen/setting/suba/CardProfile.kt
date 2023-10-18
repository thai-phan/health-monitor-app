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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.setting.suba.component.ModalDate
import com.sewon.topperhealth.screen.setting.suba.component.ModalAGender


@Composable
fun ProfileSetting(
  viewModel: ViewModelCardProfile = hiltViewModel()

) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  var openGenderModal by rememberSaveable { mutableStateOf(false) }
  var openDateModal by rememberSaveable { mutableStateOf(false) }

  val height = 45.dp
  Card(
    shape = RoundedCornerShape(size = 10.dp),
    colors = CardDefaults.cardColors(containerColor = Color(0x33000000))
  ) {
    Column(
      verticalArrangement = Arrangement.SpaceAround,
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp, horizontal = 20.dp)

    ) {
      Text(
        stringResource(R.string.profile),
        fontSize = 18.sp,
        fontWeight = FontWeight(900),
        color = Color(0xFFEDEDED)
      )

      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .height(height)
          .clickable(onClick = { openGenderModal = !openGenderModal }),
      ) {
        Text("성별")
        Row() {
          Text(uiState.gender, color = Color.White)
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Icon(
            Icons.Filled.ChevronRight,
            tint = Color.White,
            contentDescription = "contentDescription",
            modifier = Modifier.size(ButtonDefaults.IconSize)
          )
        }
      }
      Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)

      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .height(height)
          .clickable(onClick = { openDateModal = !openDateModal }),
      ) {
        Text("연령")
        Row() {
          Text(uiState.birthday, color = Color.White)
          Spacer(Modifier.size(ButtonDefaults.IconSpacing))
          Icon(
            Icons.Filled.ChevronRight,
            tint = Color.White,
            contentDescription = "contentDescription",
            modifier = Modifier.size(ButtonDefaults.IconSize)
          )
        }
      }
    }
  }

  if (openGenderModal) {
    ModalAGender(uiState,
      onChangeGender = viewModel::changeGender,
      onToggleModal = { openGenderModal = !openGenderModal })
  }

  if (openDateModal) {
    ModalDate(uiState,
      onSubmitBirthday = viewModel::changeBirthday,
      onToggleModal = { openDateModal = !openDateModal })
  }

}