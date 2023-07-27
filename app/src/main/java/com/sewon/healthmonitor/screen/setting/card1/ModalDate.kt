package com.sewon.healthmonitor.screen.setting.card1

import android.util.Log
import android.view.LayoutInflater
import android.widget.DatePicker
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDate(
    uiState: UiState,
    onUpdateBirthday: (Date) -> Unit,
    onSubmitBirthday: (Date) -> Unit,
    onToggleModal: () -> Unit,

    ) {

    var skipPartiallyExpanded by remember { mutableStateOf(false) }
    var edgeToEdgeEnabled by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val calendar = Calendar.getInstance() // show today by default
    var date: Date = calendar.time

    ModalBottomSheet(
        onDismissRequest = onToggleModal,
        sheetState = bottomSheetState,
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            factory = { context ->
                val view = LayoutInflater.from(context).inflate(R.layout.date_picker, null)
                val datePicker = view.findViewById<DatePicker>(R.id.datePicker)


                if (uiState.localUser != null) {
                    calendar.time = uiState.localUser.birthday
                }


                datePicker.init(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH),
                    { view, year, monthOfYear, dayOfMonth ->
                        // onDateChanged
                        date = Calendar.getInstance().apply {
                            set(year, monthOfYear, dayOfMonth)
                        }.time
                        Log.d("asdfasdf","Date value")

                    }
                )
                datePicker
            }
        )

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
                onSubmitBirthday(date)
            }) {
                Text("저장")
            }
        }
    }
}