package com.sewon.healthmonitor.screen.setting.card1

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
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalGender(
    uiState: ProfileSettingUiState,
    onChangeGender: (value: String) -> Unit,
    onToggleModal: () -> Unit,
) {

    val (gender, setGender) = remember { mutableStateOf(uiState.gender) }

    val skipPartiallyExpanded by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )


    ModalBottomSheet(
        onDismissRequest = onToggleModal,
        sheetState = bottomSheetState,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            AndroidView(
                factory = { context ->
                    val view = LayoutInflater.from(context).inflate(R.layout.number_picker, null)
                    val picker = view.findViewById<NumberPicker>(R.id.number_picker)
                    val data = arrayOf("남성", "여성")
                    picker.minValue = 0
                    picker.maxValue = data.size - 1
                    picker.displayedValues = data
                    picker.value = data.indexOf(gender)
                    picker.setOnValueChangedListener { picker, oldVal, newVal ->
                        setGender(data.get(newVal))
                        // do your other stuff depends on the new value
                    }
                    picker
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = onToggleModal) {
                Text("취소")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                onChangeGender(gender)
                onToggleModal()
            }) {
                Text("저장")
            }
        }
    }
}