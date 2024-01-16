package com.sewon.topperhealth.screen.activity

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sewon.topperhealth.data.DataStoreManager
import com.sewon.topperhealth.screen.a0common.component.CustomSwitch
import com.sewon.topperhealth.screen.a0common.theme.BackgroundMiddle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.IOException


@Composable
fun DialogDevMode(
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()
  val dataStore = DataStoreManager(context)

  val isLogShowed by remember { dataStore.isLogShowed }.collectAsState(initial = false)
  val isDimDisabled by remember { dataStore.isDimDisabled }.collectAsState(initial = false)
  val referenceCount by remember { dataStore.referenceCount }.collectAsState(initial = 0)

  fun bbb() {
    coroutineScope.launch {
      try {
        val outputDir = context.dataDir
        val root = File(context.dataDir, "csv")
        if (!root.exists()) {
          root.mkdirs()
        }
        val gpxfile = File(root, "file.txt")
        val writer = FileWriter(gpxfile)
        writer.append("aaaaaa")
        writer.flush()
        writer.close()
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
      } catch (e: IOException) {
        e.printStackTrace()
      }

    }
  }

  fun testingAPI() {
    val thread = Thread {
      try {
        val root = File(context.dataDir, "csv")
        if (!root.exists()) {
          root.mkdirs()
        }
        val filecsv = File(root, "data_small.csv")
        val client = OkHttpClient()
        val mediaType = "text/plain".toMediaType()
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
          .addFormDataPart(
            "file",
            "",
            filecsv.asRequestBody("application/octet-stream".toMediaType())
          ).build()
        val request =
          Request.Builder().url("http://175.196.118.115:8080/predict").post(body).build()
        val response = client.newCall(request).execute()
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }

    thread.start()

//    coroutineScope.launch {
//
//
//
//
////    val outputDir = context.dataDir // context being the Activity pointer
//////    val outputFile = File.createTempFile("prefix", ".csv", outputDir)
//////    /data/user/0/com.sewon.topperhealth/file.txt
////
////    val file = File(outputDir, "file.txt")
////
////
////    val text = StringBuilder("asdfasdf")
////
////    try {
////      val br = BufferedReader(FileReader(file))
////      var line: String?
////      while (br.readLine().also { line = it } != null) {
////        text.append(line)
////        text.append('\n')
////      }
////      br.close()
////    } catch (e: IOException) {
////      //You'll need to add proper error handling here
////    }
////    https://stackoverflow.com/questions/3425906/creating-temporary-files-in-android
//
////    https://stackoverflow.com/questions/39953457/how-to-upload-an-image-file-in-retrofit-2
//
//
////      val inputStreammm = context.resources.openRawResource(R.raw.sewon_data)
//
////      val root = File(context.dataDir, "csv")
//////      if (!root.exists()) {
//////        root.mkdirs()
//////      }
////      val filecsv = File(root, "data_small.csv")
////
//////      val file = File("/storage/emulated/0/Download/Corrections 6.jpg")
////      val body = RequestBodyAlgorithm(
////        file = filecsv,
////      )
////      val response = ServiceSewon.create().getAlgorithm(body)
////      println(response.toString())
//    }
  }



  Dialog(onDismissRequest = onDismiss) {
    Card(
      modifier = Modifier
        .fillMaxWidth()
        .height(400.dp)
        .padding(16.dp),
      colors = CardDefaults.cardColors(BackgroundMiddle),
      shape = RoundedCornerShape(16.dp),
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text("Developer Setting")
        Button(onClick = ::testingAPI) {

        }
        Button(onClick = ::bbb) {

        }
        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Show Log")
          CustomSwitch(
            checked = isLogShowed,
            onCheckedChange = {
              CoroutineScope(Dispatchers.IO).launch {
                dataStore.saveShowLog(it)
              }
            })
        }
        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Disable dim screen")
          CustomSwitch(
            checked = isDimDisabled,
            onCheckedChange = {
              CoroutineScope(Dispatchers.IO).launch {
                dataStore.saveDisableDim(it)
              }
            })
        }
        Row(
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("Ref Count")
          TextField(
            value = referenceCount.toString(),
            onValueChange = {
              CoroutineScope(Dispatchers.IO).launch {
                try {
                  Timber.tag("dev").d(it)
                  dataStore.saveReferenceCount(it.toInt())
                } catch (exception: NumberFormatException) {
                  dataStore.saveReferenceCount(0)
                }
              }
            },
          )
        }
      }
    }
  }
}