package com.sewon.topperhealth.screen.setting.childd.component

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import com.sewon.topperhealth.screen.setting.childd.UiStateD

data class RecipientState(
  var relation1: String,
  var contact1: String,
  var relation2: String,
  var contact2: String,
  var relation3: String,
  var contact3: String,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalCSOSRecipient(
  uiState: UiStateD,
  onToggleModal: () -> Unit,
  onChangeSOSRecipient: (RecipientState) -> Unit
) {
  val skipPartiallyExpanded by remember { mutableStateOf(false) }
  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)

  val (recipientState, setRecipientState) = remember {
    mutableStateOf(
      RecipientState(
        uiState.relation1, uiState.contact1,
        uiState.relation2, uiState.contact2,
        uiState.relation3, uiState.contact3
      )
    )
  }

//  val number = Uri.parse("tel:123456789")
//  val callIntent = Intent(Intent.ACTION_DIAL, number)
//  context.startActivity(callIntent)

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

      Row {
        TextField(
          label = {
            Text(stringResource(R.string.setting_d_relation))
          },
          value = recipientState.relation1,
          modifier = Modifier.weight(1f),
          onValueChange = {
            setRecipientState(recipientState.copy(relation1 = it))
          })
        Spacer(Modifier.width(10.dp))
        TextField(
          label = {
            Text(stringResource(R.string.setting_d_contact_information))
          },
          value = recipientState.contact1,
          modifier = Modifier.weight(2f),
          onValueChange = {
            setRecipientState(recipientState.copy(contact1 = it))
          })
      }

      Row() {
        TextField(
          label = {
            Text(stringResource(R.string.setting_d_relation))
          },
          value = recipientState.relation2,
          modifier = Modifier.weight(1f),
          onValueChange = {
            setRecipientState(recipientState.copy(relation2 = it))
          })
        Spacer(Modifier.width(10.dp))
        TextField(
          label = {
            Text(stringResource(R.string.setting_d_contact_information))
          },
          value = recipientState.contact2,
          modifier = Modifier.weight(2f),
          onValueChange = {
            setRecipientState(recipientState.copy(contact2 = it))
          })
      }

      Row {
        TextField(
          label = {
            Text(stringResource(R.string.setting_d_relation))
          },
          value = recipientState.relation3,
          modifier = Modifier.weight(1f),
          onValueChange = {
            setRecipientState(recipientState.copy(relation3 = it))
          })
        Spacer(Modifier.width(10.dp))
        TextField(
          label = {
            Text(stringResource(R.string.setting_d_contact_information))
          },
          value = recipientState.contact3,
          modifier = Modifier.weight(2f),
          onValueChange = {
            setRecipientState(recipientState.copy(contact3 = it))
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
          onToggleModal()
          onChangeSOSRecipient(recipientState)
        }) {
          Text(stringResource(R.string.save))
        }
      }
    }
  }
}