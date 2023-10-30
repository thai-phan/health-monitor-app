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
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.screen.a0common.theme.topperTypography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBInduceSound(
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
      Text("수면유도 사운드", style = topperTypography.titleLarge)
      Spacer(modifier = Modifier.height(20.dp))
      Text(
        "특허받은 엠씨스퀘어 브레인동조화 사운드를 이용해 알파파를 유도하여 잠에 빨리 들 수 있도록 도와줍니다. 백색소음에 익숙한 사용자에게 추천합니다"
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