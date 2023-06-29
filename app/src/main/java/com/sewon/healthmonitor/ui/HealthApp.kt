package com.sewon.healthmonitor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sewon.healthmonitor.ui.common.BottomBar
import com.sewon.healthmonitor.ui.common.blackGreenBackground
import com.sewon.healthmonitor.ui.theme.HealthAppTheme
import com.sewon.healthmonitor.ui.ztemp.TestApp

@Composable
fun HealthApp(finishActivity: () -> Unit) {
    HealthAppTheme() {
        val tabs = remember { MainTabs.values() }
        val navController = rememberNavController()

        TestApp()

//        Scaffold(
//            bottomBar = {
//                BottomBar(navController = navController, tabs)
//            }
//        ) { innerPadding ->
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.blackGreenBackground()
//            ) {
//                HealthNavGraph(
//                    finishActivity = finishActivity,
//                    navController = navController,
//                    modifier = Modifier.padding(innerPadding)
//                )
//            }
//
//        }

    }
}

