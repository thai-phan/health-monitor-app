package com.sewon.topperhealth.ui.screen.term

import android.content.res.AssetManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sewon.topperhealth.R
import com.sewon.topperhealth.ui.theme.topperShapes
import com.sewon.topperhealth.ui.theme.topperTypography


@Composable
fun TermAgreement(modifier: Modifier = Modifier, onRedirectRoute: () -> Unit) {
  val context = LocalContext.current

  val termFile = "term_of_use.txt"

  val assetFiles: AssetManager = context.assets
  val bufferedReader = assetFiles.open(termFile).bufferedReader()

  val termHeader = bufferedReader.readLine()
  val termContent = bufferedReader.use {
    it.readText()
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 20.dp)
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(stringResource(R.string.app_name), style = topperTypography.headlineSmall)
      Image(
        painter = painterResource(id = R.drawable.ic_intellinest_white),
        contentDescription = "intellinest",
        modifier = Modifier
          .size(120.dp)
      )
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
          termHeader,
          fontSize = 20.sp,
          fontWeight = FontWeight(600)
        )
        Text(
          termContent,
          modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState()),
          textAlign = TextAlign.Justify,

          )
        val checkedState = remember { mutableStateOf(false) }
        Row(
          modifier = Modifier
            .clickable { checkedState.value = !checkedState.value },
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
              checkedState.value = it
            },
          )
          Text(stringResource(R.string.agree), modifier = Modifier.padding(16.dp))
        }

        Button(
          onClick = {
            onRedirectRoute()
          },
          enabled = checkedState.value
        ) {
          Text(stringResource(R.string.agree))
        }
      }
    }
  }
}