package com.sewon.topperhealth.screen.setting.subc.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalAInduceEnergy(
  onToggleModal: () -> Unit
) {
  val skipPartiallyExpanded by remember { mutableStateOf(false) }
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)

  ModalBottomSheet(
    onDismissRequest = onToggleModal,
    sheetState = bottomSheetState,
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 50.dp)
    ) {
      Text("수면유도 에너지", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 22.sp)
      Spacer(modifier = Modifier.height(20.dp))
      Text(
        "미주신경 자극으로 심신을 안정화시켜 잠에 빨리 들 수 있도록 도와줍니다. " +
            "소리에 민감한 사용자에게 추천합니다."
      )
      Spacer(modifier = Modifier.height(20.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(100.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        Button(onClick = onToggleModal) {
          Text("취소")
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(onClick = {}) {
          Text("저장")
        }
      }
    }


  }
}