package com.sewon.healthmonitor.ui.usersetting.card1

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetting() {

    val snackState = remember { SnackbarHostState() }
    val snackScope = rememberCoroutineScope()
    SnackbarHost(hostState = snackState, Modifier)
    val openDialog = remember { mutableStateOf(false) }
    // TODO demo how to read the selected date from the state.
    if (openDialog.value) {
        val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
        val confirmEnabled = derivedStateOf { datePickerState.selectedDateMillis != null }
        DatePickerDialog(
            onDismissRequest = {
                // Dismiss the dialog when the user clicks outside the dialog or on the back
                // button. If you want to disable that functionality, simply use an empty
                // onDismissRequest.
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        snackScope.launch {
                            snackState.showSnackbar(
                                "Selected date timestamp: ${datePickerState.selectedDateMillis}"
                            )
                        }
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }


    Card(
        shape = RoundedCornerShape(size = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0x33000000))
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {
            Text(
                "프로필", fontSize = 16.sp, fontWeight = FontWeight(900), color = Color(0xFFEDEDED)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Divider(color = Color(0x1AFFFFFF), thickness = 1.dp)
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("성별")

            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("연령")
                TextButton(
                    onClick = {
                        openDialog.value = true
                    },
                ) {
                    Text("Select", color = Color.White)
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = "Localized description",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
            }
        }
    }

//
//    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
//    var skipPartiallyExpanded by remember { mutableStateOf(false) }
//    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope()
//    val bottomSheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = skipPartiallyExpanded
//    )
//
//    // App content
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Row(
//            Modifier.toggleable(
//                value = skipPartiallyExpanded,
//                role = Role.Checkbox,
//                onValueChange = { checked -> skipPartiallyExpanded = checked }
//            )
//        ) {
//            Checkbox(checked = skipPartiallyExpanded, onCheckedChange = null)
//            Spacer(Modifier.width(16.dp))
//            Text("Skip partially expanded State")
//        }
//        Row(
//            Modifier.toggleable(
//                value = edgeToEdgeEnabled,
//                role = Role.Checkbox,
//                onValueChange = { checked -> edgeToEdgeEnabled = checked }
//            )
//        ) {
//            Checkbox(checked = edgeToEdgeEnabled, onCheckedChange = null)
//            Spacer(Modifier.width(16.dp))
//            Text("Toggle edge to edge enabled.")
//        }
//        Button(onClick = { openBottomSheet = !openBottomSheet }) {
//            Text(text = "Show Bottom Sheet")
//        }
//    }
//
//// Sheet content
//    if (openBottomSheet) {
//        ModalBottomSheet(
//            onDismissRequest = { openBottomSheet = false },
//            sheetState = bottomSheetState,
//        ) {
//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//                Button(
//                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
//                    // you must additionally handle intended state cleanup, if any.
//                    onClick = {
//                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
//                            if (!bottomSheetState.isVisible) {
//                                openBottomSheet = false
//                            }
//                        }
//                    }
//                ) {
//                    Text("Hide Bottom Sheet")
//                }
//            }
//            var text by remember { mutableStateOf("") }
//            OutlinedTextField(value = text, onValueChange = { text = it })
//            LazyColumn {
//                items(50) {
//                    ListItem(
//                        headlineContent = { Text("Item $it") },
//                        leadingContent = {
//                            Icon(
//                                Icons.Default.Favorite,
//                                contentDescription = "Localized description"
//                            )
//                        }
//                    )
//                }
//            }
//        }
//    }
//    NumberPicker()

//    Spinner()
}