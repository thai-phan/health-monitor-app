package com.sewon.topperhealth.screen.setting.subd.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperTypography


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalCSOSRecipient(
  onToggleModal: () -> Unit,
  onChangeSOSRecipient: () -> Unit
) {
  val skipPartiallyExpanded by remember { mutableStateOf(false) }
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)

  val relation = stringResource(R.string.setting_d_relation)
  val contact = stringResource(R.string.setting_d_contact_information)

  ModalBottomSheet(
//    modifier = Modifier.fillMaxHeight(),
    onDismissRequest = onToggleModal,
    sheetState = bottomSheetState,
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 50.dp)
    ) {
      Text(
        stringResource(R.string.setting_d_recipient_setting),
        style = topperTypography.titleLarge
      )
      Spacer(modifier = Modifier.height(20.dp))

      val relation1 by remember { mutableStateOf(TextFieldValue(relation)) }
      val contact1 by remember { mutableStateOf(TextFieldValue(contact)) }
      Row {
        TextField(
          relation1,
          modifier = Modifier.weight(1f),
          onValueChange = {

          })
        TextField(
          contact1,
          modifier = Modifier.weight(1f),
          onValueChange = {

          })
      }

      val relation2 by remember { mutableStateOf(TextFieldValue(relation)) }
      val contact2 by remember { mutableStateOf(TextFieldValue(contact)) }
      Row() {
        TextField(
          relation2,
          modifier = Modifier.weight(1f),
          onValueChange = {

          })
        TextField(
          contact2,
          modifier = Modifier.weight(1f),
          onValueChange = {

          })
      }

      val relation3 by remember { mutableStateOf(TextFieldValue(relation)) }
      val contact3 by remember { mutableStateOf(TextFieldValue(contact)) }
      Row {
        TextField(
          relation3,
          modifier = Modifier.weight(1f),
          onValueChange = {

          })
        TextField(
          contact3,
          modifier = Modifier.weight(1f),
          onValueChange = {

          })
      }
      Spacer(modifier = Modifier.height(20.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(100.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        Button(onClick = onToggleModal) {
          Text(stringResource(R.string.cancel))
        }
        Spacer(modifier = Modifier.width(20.dp))
        Button(onClick = {

          onChangeSOSRecipient()
        }) {
          Text(stringResource(R.string.save))
        }
      }
    }
  }
}