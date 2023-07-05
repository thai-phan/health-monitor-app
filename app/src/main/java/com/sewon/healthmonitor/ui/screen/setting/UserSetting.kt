package com.sewon.healthmonitor.ui.screen.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sewon.healthmonitor.ui.screen.setting.card1.ProfileSetting
import com.sewon.healthmonitor.ui.screen.setting.card2.SleepSetting
import com.sewon.healthmonitor.ui.screen.setting.card3.InductionSolutionSetting
import com.sewon.healthmonitor.ui.screen.setting.card4.GeneralSetting


@Composable
fun UserSetting() {
    val switchColors: SwitchColors = SwitchDefaults.colors(
        checkedThumbColor = Color(0xFF09897D),
        checkedTrackColor = Color(0xFF03DAC5),
        checkedBorderColor = Color(0xFF03DAC5),
        uncheckedThumbColor = Color(0xFF938F99),
        uncheckedTrackColor = Color(0xFF36343B),
        uncheckedBorderColor = Color(0xFF938F99),
    )

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text("내 설정", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        ProfileSetting()

        SleepSetting(switchColors)

        InductionSolutionSetting(switchColors)

        GeneralSetting(switchColors)
    }
}