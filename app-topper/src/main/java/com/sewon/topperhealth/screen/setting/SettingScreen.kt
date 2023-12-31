package com.sewon.topperhealth.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.setting.childa.ProfileSetting
import com.sewon.topperhealth.screen.setting.childb.SleepSetting
import com.sewon.topperhealth.screen.setting.childc.InductionSolutionSetting
import com.sewon.topperhealth.screen.setting.childd.GeneralSetting
import com.sewon.topperhealth.screen.setting.childe.DeviceConnectionSetting

@Composable
fun SettingScreen(
  modifier: Modifier = Modifier,
  viewModel: SettingViewModel = hiltViewModel()
) {

  val rowHeight = 45.dp

  Column(
    modifier = modifier
      .padding(
        start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp
      ),
  ) {
    Text(stringResource(R.string.setting_title), style = topperTypography.headlineSmall)
    Spacer(Modifier.height(20.dp))
    Column(
      modifier = Modifier.verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
      ProfileSetting(rowHeight)

      SleepSetting(rowHeight)

      InductionSolutionSetting(rowHeight)

      GeneralSetting(rowHeight)

      DeviceConnectionSetting(rowHeight)
    }
  }
}