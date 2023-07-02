package com.sewon.healthmonitor.ui.usersetting.card1

import android.view.LayoutInflater
import android.widget.NumberPicker
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDate() {

    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    ModalBottomSheet(
        onDismissRequest = { openBottomSheet = false },
        sheetState = bottomSheetState,
    ) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->

                val view = LayoutInflater.from(context).inflate(R.layout.number_picker, null)
                val picker = view.findViewById<NumberPicker>(R.id.number_picker)

                val data = arrayOf("Male", "Female")
                picker.minValue = 0
                picker.maxValue = data.size - 1
                picker.displayedValues = data
                picker

            }
        )
    }
}