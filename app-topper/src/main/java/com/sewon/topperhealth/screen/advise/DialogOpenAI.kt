package com.sewon.topperhealth.screen.advise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sewon.topperhealth.screen.a0common.theme.BackgroundMiddle

@Composable
fun DialogOpenAI(
  openAIKey: String,
  onDismiss: () -> Unit,
  onValueChange: (String) -> Unit
) {

  val keyState = remember { mutableStateOf(openAIKey) }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .padding(0.dp),
      colors = CardDefaults.cardColors(BackgroundMiddle),
      shape = RoundedCornerShape(16.dp),
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text("OpenAI Configuration")
        Spacer(Modifier.height(20.dp))
        Column(
          verticalArrangement = Arrangement.SpaceBetween,
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("API Key")
          TextField(
            value = keyState.value,
            onValueChange = {
              keyState.value = it
            },
          )
        }
        Spacer(Modifier.height(20.dp))
        Button(onClick = {
          onValueChange(keyState.value)
          onDismiss()
        }) {
          Text("Save")
        }
      }
    }
  }
}