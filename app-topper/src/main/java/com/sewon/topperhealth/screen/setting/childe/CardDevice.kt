package com.sewon.topperhealth.screen.setting.childe

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.component.CustomSwitch
import com.sewon.topperhealth.screen.a0common.theme.topperShapes
import com.sewon.topperhealth.screen.a0common.theme.topperTypography


@Composable
fun DeviceConnectionSetting(
  rowHeight: Dp,
) {
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
        stringResource(R.string.setting_e_product_registration),
        style = topperTypography.titleMedium
      )
      Spacer(Modifier.height(10.dp))
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .height(rowHeight)
      ) {
        Text(stringResource(R.string.setting_e_product_number))

        var checked by remember { mutableStateOf(true) }

        CustomSwitch(
          modifier = Modifier.semantics { contentDescription = "Demo" },
          checked = checked,
          onCheckedChange = { checked = it })
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
      ) {
        Text(stringResource(R.string.setting_e_bluetooth))

        var checked by remember { mutableStateOf(true) }

        CustomSwitch(
          modifier = Modifier.semantics { contentDescription = "Demo" },
          checked = checked,
          onCheckedChange = { checked = it })
      }
    }
  }
}