package com.sewon.healthmonitor.ui.ztemp

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun TestComponent() {
    // Decoupled snackbar host state from scaffold state for demo purposes.
    Text("asdfadsf", fontSize = 30.sp)
    Card() {
//        color= MaterialTheme.colorScheme.primary
        Text(text = "asdasd", fontSize = 30.sp)
    }
}