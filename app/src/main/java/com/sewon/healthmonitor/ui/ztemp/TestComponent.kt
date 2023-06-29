package com.sewon.healthmonitor.ui.ztemp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun TestComponent() {
    // Decoupled snackbar host state from scaffold state for demo purposes.
    Text("asdfadsf", fontSize = 30.sp)
    Card() {
//        color= MaterialTheme.colorScheme.primary
        Text(
            text = "asdasd", fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
