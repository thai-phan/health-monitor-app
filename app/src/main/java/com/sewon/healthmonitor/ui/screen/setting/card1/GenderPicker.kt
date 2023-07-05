package com.sewon.healthmonitor.ui.screen.setting.card1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun GenderPicker() {


    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            // Note: If you provide logic outside of onDismissRequest to remove the sheet,
            // you must additionally handle intended state cleanup, if any.
            onClick = {

            }
        ) {
            Text("Hide Bottom Sheet")
        }
    }
    var text by remember { mutableStateOf("") }
    OutlinedTextField(value = text, onValueChange = { text = it })
    LazyColumn {
        items(50) {
            ListItem(
                headlineContent = { Text("Item $it") },
                leadingContent = {
                    Icon(
                        Icons.Default.Favorite,
                        contentDescription = "Localized description"
                    )
                }
            )
        }
    }

}