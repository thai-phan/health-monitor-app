package com.sewon.healthmonitor.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
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

@Composable
fun HealthApp(finishActivity: () -> Unit) {
    HealthAppTheme() {
        val tabs = remember { MainTabs.values() }
        val navController = rememberNavController()

        Scaffold(
            bottomBar = {
                BottomBar(navController = navController, tabs)
            }
        ) { innerPaddingModifier ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .blackGreenBackground()
            ) {
                HealthNavGraph(
                    finishActivity = finishActivity,
                    navController = navController,
                    modifier = Modifier.padding(innerPaddingModifier)
                )
            }

        }

    }
}

