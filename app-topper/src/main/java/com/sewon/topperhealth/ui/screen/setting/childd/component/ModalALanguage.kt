package com.sewon.topperhealth.ui.screen.setting.childd.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.R
import com.sewon.topperhealth.ui.theme.topperTypography
import com.sewon.topperhealth.util.AppLanguage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalALanguage(
  appLanguage: AppLanguage,
  onToggleModal: () -> Unit,
  onChangeLanguage: (AppLanguage) -> Unit
) {
  val skipPartiallyExpanded by remember { mutableStateOf(false) }

  val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded)

  val languageOptions = mapOf(
    "한국어" to "ko",
    "English" to "en",
  )

  val (selectedKey, setSelectedKey) = remember { mutableStateOf(appLanguage.selectedLang) }
  val (selectedValue, setSelectedValue) = remember { mutableStateOf(appLanguage.selectedLang) }

  ModalBottomSheet(
    onDismissRequest = onToggleModal,
    sheetState = bottomSheetState,
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 50.dp)
    ) {
      Text(stringResource(R.string.setting_d_select_language), style = topperTypography.titleLarge)
      Spacer(Modifier.height(20.dp))
      Column(
        modifier = Modifier
          .selectableGroup(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        languageOptions.forEach { (language, value) ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .height(40.dp)
              .selectable(
                selected = (language == selectedKey),
                onClick = {
                  setSelectedKey(language)
                  setSelectedValue(value)
                },
                role = Role.RadioButton
              )
              .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            RadioButton(
              selected = (language == selectedKey),
              onClick = null // null recommended for accessibility with screenreaders
            )
            Text(
              text = language,
              modifier = Modifier
                .padding(start = 16.dp)
                .width(120.dp),
              color = Color.White
            )
          }
        }
      }
      Spacer(Modifier.height(20.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .height(80.dp),
        horizontalArrangement = Arrangement.Center
      ) {
        Button(onToggleModal) {
          Text(stringResource(R.string.cancel))

        }
        Spacer(Modifier.width(20.dp))
        Button({
          onChangeLanguage(AppLanguage(selectedKey, selectedValue))
        }) {
          Text(stringResource(R.string.save))
        }
      }
    }
  }
}