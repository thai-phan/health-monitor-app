package com.sewon.healthmonitor.ui.setting

import android.content.res.AssetManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun TermAgreement() {
    val context = LocalContext.current
    val assetFiles: AssetManager = context.assets
    val termFile = "term_of_use.txt"
    val text: String = assetFiles.open(termFile).bufferedReader().use{
        it.readText()
    }

    val scroll = rememberScrollState(0)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Terms and Agreements", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = text,
            overflow = TextOverflow.Visible,
            modifier = Modifier
                .padding(30.dp)
                .height(500.dp)
                .verticalScroll(scroll)
        )
        val checkedState = remember { mutableStateOf(true) }
//        TODO: Row check
        Row {
            Checkbox(
                checked = false,
                modifier = Modifier.padding(16.dp),
                onCheckedChange = {checkedState.value = it}
            )
            Text(text = "Agree", modifier = Modifier.padding(16.dp))
        }

        Button(
            onClick = {

            }
        ) {
            Text(text = "Agree")
        }
    }
}