package com.sewon.topperhealth.screen.activity.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperTypography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalAssessment(
  onToggleModal: () -> Unit,
  onSaveAssessment: (String) -> Unit
) {
  val skipPartiallyExpanded by remember { mutableStateOf(false) }

  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)
  val radioOptions = listOf("매우 그렇다", "그렇다", "그렇지 않다", "전혀 그렇지 않다")

  val (selectedOption, onOptionSelected) = remember { mutableStateOf("매우 그렇다") }


  ModalBottomSheet(
    onDismissRequest = onToggleModal,
    sheetState = bottomSheetState,
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 50.dp)
    ) {
      Text("최근 수면의 질 평가", style = topperTypography.titleLarge)
      Spacer(modifier = Modifier.height(20.dp))
      Text("질문 : 시간동안 얼마나 자주 피곤하고 무기력감을 느꼈나요?")

      Column(
        modifier = Modifier
          .selectableGroup(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {


        radioOptions.forEach { radio ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(40.dp)
              .selectable(
                selected = (radio == selectedOption),
                onClick = { onOptionSelected(radio) },
                role = Role.RadioButton
              )
              .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            RadioButton(
              selected = (radio == selectedOption),
              onClick = null // null recommended for accessibility with screenreaders
            )
            Text(
              text = radio,
              modifier = Modifier
                .padding(start = 16.dp)
                .width(120.dp),
              color = Color.White
            )
          }
        }
      }
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(80.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        Button(onClick = onToggleModal) {
          Text(stringResource(R.string.cancel))

        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(onClick = {
          onSaveAssessment(selectedOption)
        }) {
          Text(stringResource(R.string.save))
        }
      }
    }
  }
}