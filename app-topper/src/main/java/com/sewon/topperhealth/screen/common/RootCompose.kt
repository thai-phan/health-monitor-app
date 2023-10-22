package com.sewon.topperhealth.screen.common

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
import com.sewon.topperhealth.screen.common.theme.HealthAppTheme
import com.sewon.topperhealth.screen.NavigationGraph

@Composable
fun RootCompose(finishActivity: () -> Unit) {

  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val bottomBarState = rememberSaveable { mutableStateOf(true) }

  when (navBackStackEntry?.destination?.route) {
    Destinations.ACTIVITY_ROUTE -> {
      bottomBarState.value = false
    }

    Destinations.REPORT_ROUTE -> {
      bottomBarState.value = true
    }

    Destinations.SETTING_ROUTE -> {
      bottomBarState.value = true
    }
  }

  HealthAppTheme() {
    val tabs = remember { MainTabs.values() }

    Scaffold(
      bottomBar = {
        BottomBar(navController = navController, bottomBarState, tabs)
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
        NavigationGraph(
          finishActivity = finishActivity,
          navController = navController,
          modifier = Modifier.padding(innerPaddingModifier)
        )
      }
    }
  }
}

