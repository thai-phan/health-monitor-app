package com.sewon.healthmonitor.ui.screen.setting.card3

import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalScoreTheshold(onOpenGenderModal: () -> Unit) {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    var test = "aaaa"


    ModalBottomSheet(
        onDismissRequest = onOpenGenderModal,
        sheetState = bottomSheetState,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(200.dp),
            horizontalArrangement = Arrangement.Center) {
            AndroidView(
                factory = { context ->

                    val view = LayoutInflater.from(context).inflate(R.layout.number_picker, null)

                    val picker = view.findViewById<NumberPicker>(R.id.number_picker)

                    val data = arrayOf("Male", "Female")
//                picker.textColor =  Color.parseColor("#FABBBB")
//                picker.div
                    picker.minValue = 0
                    picker.maxValue = data.size - 1
                    picker.displayedValues = data
                    picker.setOnValueChangedListener { picker, oldVal, newVal ->
                        test = data.get(newVal)
                        println("thaiph")
                        println(test)
                        // do your other stuff depends on the new value
                    }
                    picker

                }
            )
        }

        Row(modifier = Modifier.fillMaxWidth().height(100.dp),
            horizontalArrangement = Arrangement.Center) {
            Button(onClick = onOpenGenderModal) {
                Text("취소")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {}) {
                Text("저장")
            }
        }


    }
}