package com.sewon.topperhealth.ui.screen.report.childa

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.data.model.SleepSession


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionSelection(sessionList: List<SleepSession>, selectReportSession: (id: Int) -> Unit) {
  var expanded by remember { mutableStateOf(false) }

  var selectedText by remember { mutableStateOf(sessionList[0]) }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .padding(0.dp)
  ) {
    ExposedDropdownMenuBox(
      expanded = expanded,
      onExpandedChange = {
        expanded = !expanded
      }
    ) {
      TextField(
        value = "${selectedText.sessionId} : ${selectedText.actualEndTime}",
        onValueChange = {},
        readOnly = true,
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        modifier = Modifier.menuAnchor()
      )

      ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {

        sessionList.forEach { item ->
          DropdownMenuItem(
            text = { Text("${item.sessionId} : ${item.actualEndTime}") },
            onClick = {
              selectReportSession(item.sessionId)
              selectedText = item
              expanded = false
            }
          )
        }
      }
    }
  }
}