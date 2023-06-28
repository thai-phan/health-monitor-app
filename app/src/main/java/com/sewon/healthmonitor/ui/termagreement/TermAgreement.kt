package com.sewon.healthmonitor.ui.termagreement

import android.content.res.AssetManager
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sewon.healthmonitor.R


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
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        Text("건강모니터링 토퍼", fontWeight = FontWeight.Bold, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
                .shadow(
                    elevation = 24.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .background(color = Color(0x99000000), shape = RoundedCornerShape(size = 10.dp))
                .padding(20.dp),

            ) {
            Text(
                "생체신호 모니터링 매트리스 앱 서비스 이용동의서",
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                fontFamily = FontFamily(
                    Font(R.font.suite_regular)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = text,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .verticalScroll(scroll),
                color = Color.White
            )


            val checkedState = remember { mutableStateOf(true) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = false,
                    onCheckedChange = {
                        checkedState.value = it
                        navController.navigate(redirectRoute)
                    },
                )
                Text(text = "Agree", modifier = Modifier.padding(16.dp))
            }
            Button(
                onClick = {
                    navController.navigate(redirectRoute)

                }
            ) {
                Text(text = "Agree")
            }
        }

    }
}

@Preview
@Composable
fun TermAgreementPreview() {
    TermAgreement(rememberNavController(), "String")
}