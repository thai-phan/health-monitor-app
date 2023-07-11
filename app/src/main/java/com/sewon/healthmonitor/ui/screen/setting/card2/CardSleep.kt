package com.sewon.healthmonitor.ui.screen.setting.card2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.*

// Card 2
@Composable
fun SleepSetting(switchColors: SwitchColors) {


    Card(
        shape = RoundedCornerShape(size = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x33000000))
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {
            Text(
                "수면설정", fontSize = 16.sp, fontWeight = FontWeight(900), color = Color(0xFFEDEDED)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
            Spacer(modifier = Modifier.height(5.dp))

            var checkedOne by remember { mutableStateOf(true) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { checkedOne = !checkedOne }),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("기상알람시간")
                Switch(
                    colors = switchColors,
                    modifier = Modifier.semantics { contentDescription = "Demo" },
                    checked = checkedOne,
                    onCheckedChange = { checkedOne = it })
            }

            var checkedTwo by remember { mutableStateOf(true) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { checkedTwo = !checkedTwo }),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("취침시간")
                Switch(
                    colors = switchColors,
                    modifier = Modifier.semantics { contentDescription = "Demo" },
                    checked = checkedTwo,
                    onCheckedChange = { checkedTwo = it })
            }

            var checkedThree by remember { mutableStateOf(true) }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { checkedThree = !checkedThree }),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("알람설정")
                Switch(
                    colors = switchColors,
                    modifier = Modifier.semantics { contentDescription = "Demo" },
                    checked = checkedThree,
                    onCheckedChange = { checkedThree = it })
            }
        }
    }
}