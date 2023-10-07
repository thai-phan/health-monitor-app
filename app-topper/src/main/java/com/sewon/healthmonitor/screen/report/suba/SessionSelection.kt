package com.sewon.healthmonitor.screen.report.suba

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
import com.sewon.healthmonitor.data.model.SleepSession


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionSelection(data: List<SleepSession>, selectReportSession: (id: Int) -> Unit) {
  var expanded by remember { mutableStateOf(false) }

  if (data.isNotEmpty()) {
    var selectedText by remember { mutableStateOf(data[0]) }

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(32.dp)
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

          data.forEach { item ->
            DropdownMenuItem(
              text = { Text("${item.sessionId} : ${item.actualEndTime}")  },
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

}