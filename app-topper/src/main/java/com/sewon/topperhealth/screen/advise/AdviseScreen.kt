package com.sewon.topperhealth.screen.advise

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sewon.topperhealth.data.HealthDataStore
import com.sewon.topperhealth.screen.a0common.theme.topperShapes
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AdviseScreen(
  modifier: Modifier,
  viewModel: AdviceViewModel = hiltViewModel(),
  closeScreen: () -> Unit,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()

  val context = LocalContext.current
  val dataStore = HealthDataStore(context)
  val openAIKey by remember { dataStore.openAIKey }.collectAsState(initial = "")
  val isDialogOpen = rememberSaveable { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 20.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .height(100.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text("ChatGPT", style = topperTypography.headlineSmall)

      Button(
        onClick = { isDialogOpen.value = !isDialogOpen.value },
      ) {
        Text("Setting key")
      }
//      Image(
//        painter = painterResource(id = R.drawable.ic_intellinest_white),
//        contentDescription = "intellinest",
//        modifier = Modifier
//          .size(120.dp)
//      )
    }
    Card(
      modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 20.dp),
      shape = topperShapes.small,
      colors = CardDefaults.cardColors(containerColor = Color(0x33000000))
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
          .shadow(
            elevation = 24.dp,
            spotColor = Color(0x40000000),
            ambientColor = Color(0x40000000)
          )
          .padding(20.dp),
      ) {
        Text(
          uiState.question,
          fontSize = 20.sp,
          fontWeight = FontWeight(600)
        )
        Spacer(Modifier.height(20.dp))
        Button(onClick = { viewModel.queryOpenAI(openAIKey) }) {
          Text("Get Advise from ChatGPT")
        }
        Spacer(Modifier.height(20.dp))

        if (!uiState.isLoading) {
          Text(
            uiState.advise,
            modifier = Modifier
              .weight(1f)
              .verticalScroll(rememberScrollState()),
            textAlign = TextAlign.Justify,
          )
        } else {
          CircularProgressIndicator(
            modifier = Modifier
              .width(64.dp)
              .weight(1f),
          )
        }

        Spacer(Modifier.height(20.dp))
        Button(
          onClick = { closeScreen() },
        ) {
          Text("Close")
        }
      }
    }

    if (isDialogOpen.value) {
      DialogOpenAI(openAIKey, onDismiss = {
        isDialogOpen.value = !isDialogOpen.value
      }) {
        CoroutineScope(Dispatchers.IO).launch {
          dataStore.saveOpenAIKey(it)
        }
      }
    }
  }
}