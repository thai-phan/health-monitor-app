package com.sewon.healthmonitor.ui.usersetting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sewon.healthmonitor.ui.usersetting.card1.ProfileSetting
import com.sewon.healthmonitor.ui.usersetting.card2.SleepSetting
import com.sewon.healthmonitor.ui.usersetting.card3.InductionSolutionSetting
import com.sewon.healthmonitor.ui.usersetting.card4.GeneralSetting


@Composable
fun User() {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Text("내 설정", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        ProfileSetting()

        SleepSetting()

        InductionSolutionSetting()

        GeneralSetting()
    }
}