package com.sewon.healthmonitor.ui.ztemp

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
import androidx.compose.ui.unit.dp
import com.sewon.healthmonitor.config.AppDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.sewon.healthmonitor.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserSettings() {
    val context = LocalContext.current
    val assetFiles: AssetManager = context.assets
    val file_name = "term_of_use.txt"
    val text: String = assetFiles.open(file_name).bufferedReader().use{
        it.readText()
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val tokenValue = remember {
        mutableStateOf(TextFieldValue())
    }
//    val bufferedReader: BufferedReader = File("example.txt").bufferedReader()
//    val inputString = bufferedReader.use { it.readText() }
//    println(inputString)
//    val aaa = Environment.getDataDirectory().listFiles()

    val store = AppDataStore(context)
    val tokenText = store.getAccessToken.collectAsState(initial = "")
    val scroll = rememberScrollState(0)
    val res: Resources = context.resources
    val in_s = res.openRawResource(R.raw.test)
    val b = ByteArray(in_s.available())
    in_s.read(b)
    val json_string = (String(b))


    Column(
        modifier = Modifier.clickable { keyboardController?.hide() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "DataStorage Example", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = tokenText.value)

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = tokenValue.value,
            onValueChange = { tokenValue.value = it },
        )
//
//        TextField(
//            value = tokenValue.value,
//            onValueChange = { tokenValue.value = it },
//        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    store.saveToken(tokenValue.value.text)
                }
            }
        ) {
            Text(text = "Update Token")
        }


        Text(
            text = text,
            overflow = TextOverflow.Visible,
            modifier = Modifier
                .padding(30.dp)
                .height(300.dp)
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