package com.sewon.topperhealth.screen.setting.subd.component

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
fun ModalBClearHistory(
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
      Text("수면기록 모두 지우기", style = topperTypography.titleLarge)

      Spacer(modifier = Modifier.height(20.dp))


      Text(
        "실행으로 모든 수면 기록과 분석결과가 삭제되어 과거 레포트를 확인하실 수 없습니다.\n" +
            "실행 하시겠습니까?"
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