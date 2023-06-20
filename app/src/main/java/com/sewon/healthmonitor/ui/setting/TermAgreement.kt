package com.sewon.healthmonitor.ui.setting

import android.content.res.AssetManager
import android.content.res.Resources
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.config.AppDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TermAgreement() {
    val context = LocalContext.current
    val res: Resources = context.resources
    val in_s = res.openRawResource(R.raw.term_of_use)
    val b = ByteArray(in_s.available())
    in_s.read(b)
    val json_string = (String(b))

    val scroll = rememberScrollState(0)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Terms and Agreements", fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = json_string,
            overflow = TextOverflow.Visible,
            modifier = Modifier
                .padding(30.dp)
                .height(500.dp)
                .verticalScroll(scroll)
        )
        Button(
            onClick = {

            }
        ) {
            Text(text = "Agree")
        }
    }
}