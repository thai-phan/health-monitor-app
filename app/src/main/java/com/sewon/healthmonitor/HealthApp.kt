package com.sewon.healthmonitor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sewon.healthmonitor.common.BottomBar
import com.sewon.healthmonitor.common.HealthNavGraph
import com.sewon.healthmonitor.common.MainDestinations
import com.sewon.healthmonitor.common.MainTabs
import com.sewon.healthmonitor.common.blackGreenBackground
import com.sewon.healthmonitor.common.theme.HealthAppTheme

@Composable
fun HealthApp(finishActivity: () -> Unit) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }

    when (navBackStackEntry?.destination?.route) {
        MainDestinations.ACTIVITY_ROUTE -> {
            bottomBarState.value = false
        }
        MainDestinations.REPORT_ROUTE -> {
            bottomBarState.value = true
        }
        MainDestinations.USER_ROUTE -> {
            bottomBarState.value = true
        }
    }

    HealthAppTheme() {
        val tabs = remember { MainTabs.values() }

        Scaffold(
            bottomBar = {
                BottomBar(navController = navController, bottomBarState,  tabs)
            },
//            topBar = {
//                Text("Top ")
//            }
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

