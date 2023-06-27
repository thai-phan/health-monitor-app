package com.sewon.healthmonitor.ui.termagreement

import android.content.res.AssetManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun TermAgreement(navController: NavController, redirectRoute: String) {
    val context = LocalContext.current
    val assetFiles: AssetManager = context.assets
    val termFile = "term_of_use.txt"
    val text: String = assetFiles.open(termFile).bufferedReader().use {
        it.readText()
    }

    val scroll = rememberScrollState(0)

    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)
    ){
        Text("건강모니터링 토퍼", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 30.sp)
        Column(
            modifier = Modifier
                .offset(x = 20.dp, y = 89.dp)
                .shadow(
                    elevation = 24.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .width(320.dp)
                .height(584.dp)
                .background(color = Color(0x99000000), shape = RoundedCornerShape(size = 10.dp))
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = text,
                overflow = TextOverflow.Visible,

                modifier = Modifier
                    .padding(30.dp)
                    .height(400.dp)
                    .verticalScroll(scroll),
                color = Color.White
            )
            val checkedState = remember { mutableStateOf(true) }
//        TODO: Row check
            Row {
                Checkbox(
                    checked = false,
                    modifier = Modifier.padding(16.dp),
                    onCheckedChange = { checkedState.value = it },

                    )
                Text(text = "Agree", modifier = Modifier.padding(16.dp), color = Color.White)
            }

            Button(
                onClick = {
                    navController.navigate(redirectRoute)
                }
            ) {
                Text(text = "Agree", color = Color.White)
            }
        }

    }


}