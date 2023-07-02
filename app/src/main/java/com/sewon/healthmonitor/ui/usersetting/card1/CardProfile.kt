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
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSetting() {



    var openGenderModal by rememberSaveable { mutableStateOf(false) }
    var openDateModal by rememberSaveable { mutableStateOf(false) }

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
                TextButton(
                    onClick = {
                        openGenderModal = !openGenderModal
                    },
                ) {
                    Text("Select", color = Color.White)
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = "Localized description",
                        tint = Color.White,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                }
            }


            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("연령")
                TextButton(
                    onClick = {
                        openDateModal = !openDateModal
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




//    var skipPartiallyExpanded by remember { mutableStateOf(false) }
//    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
//
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
//        Button(onClick = { openGenderModal = !openGenderModal }) {
//            Text(text = "Show Bottom Sheet")
//        }
//    }

// Sheet content
    if (openGenderModal) {
        GenderModal()
    }

    if (openDateModal) {

    }


//    NumberPicker()

//    Spinner()
}