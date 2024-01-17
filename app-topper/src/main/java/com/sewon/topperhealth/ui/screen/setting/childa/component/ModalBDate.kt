package com.sewon.topperhealth.ui.screen.setting.childa.component

import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.NumberPicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.topperhealth.R
import com.sewon.topperhealth.ui.theme.topperTypography
import com.sewon.topperhealth.ui.screen.setting.childa.UiStateA
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDate(
  uiState: UiStateA,
  onToggleModal: () -> Unit,
  onSubmitBirthday: (Int, Int, Int) -> Unit,
) {
  val skipPartiallyExpanded by remember { mutableStateOf(false) }
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)

  val (year, setYear) = remember { mutableIntStateOf(uiState.calendar.get(Calendar.YEAR)) }
  val (month, setMonth) = remember { mutableIntStateOf(uiState.calendar.get(Calendar.MONTH)) }
  val (day, setDay) = remember { mutableIntStateOf(uiState.calendar.get(Calendar.DAY_OF_MONTH)) }

  ModalBottomSheet(
    onDismissRequest = onToggleModal,
    sheetState = bottomSheetState,
  ) {
    Column(modifier = Modifier.padding(horizontal = 50.dp)) {
      Text(stringResource(R.string.setting_a_age), style = topperTypography.titleLarge)
      AndroidView(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp), factory = { context ->
        val view = LayoutInflater.from(context).inflate(R.layout.date_picker, null)
        val datePicker = view.findViewById<DatePicker>(R.id.datePicker)
        datePicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        datePicker.init(
          year, month, day
        ) { _, year, monthOfYear, dayOfMonth ->
          setYear(year)
          setMonth(monthOfYear)
          setDay(dayOfMonth)
        }
        datePicker
      })

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(100.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        Button(onClick = onToggleModal) {
          Text(stringResource(R.string.cancel))

        }
        Spacer(Modifier.width(20.dp))
        Button(onClick = {
          onSubmitBirthday(year, month, day)
          onToggleModal()
        }) {
          Text(stringResource(R.string.save))
        }
      }
    }

  }
}