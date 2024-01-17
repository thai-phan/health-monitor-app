package com.sewon.topperhealth.ui.screen.activity.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.R
import com.sewon.topperhealth.ui.theme.topperTypography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalStarQuality(
  onToggleModal: () -> Unit,
  onSaveQuality: (Int, String) -> Unit
) {
  val skipPartiallyExpanded by remember { mutableStateOf(false) }

  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)

  val (rating, setRating) = remember { mutableIntStateOf(4) }

  var memo by remember { mutableStateOf("") }

  ModalBottomSheet(
    onDismissRequest = onToggleModal,
    sheetState = bottomSheetState,
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 50.dp)
    ) {
      Text(stringResource(R.string.sleep_assessment), style = topperTypography.titleLarge)
      Spacer(Modifier.height(20.dp))
      Text(stringResource(R.string.sleep_quality_question))
      Spacer(Modifier.height(20.dp))
      Column(
        modifier = Modifier
          .selectableGroup()
          .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {

        RatingInputRow(rating = rating) { rating ->
          setRating(rating)
        }

        TextField(value = memo, onValueChange = {
          memo = it
        })
      }
      Spacer(Modifier.height(20.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(80.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        Button(onClick = onToggleModal) {
          Text(stringResource(R.string.cancel))
        }
        Spacer(Modifier.width(20.dp))
        Button(onClick = {
          onSaveQuality(rating, memo)
        }) {
          Text(stringResource(R.string.save))
        }
      }
    }
  }
}