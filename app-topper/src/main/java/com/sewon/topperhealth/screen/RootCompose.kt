package com.sewon.topperhealth.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sewon.topperhealth.screen.a0common.theme.TopperAppTheme
import com.sewon.topperhealth.screen.a0common.BottomBar
import com.sewon.topperhealth.screen.a0common.Destinations
import com.sewon.topperhealth.screen.a0common.MainTabs
import com.sewon.topperhealth.screen.a0common.blackGreenBackground

@Composable
fun RootCompose(setLocale: (String) -> Unit, finishActivity: () -> Unit) {

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

  TopperAppTheme() {
    val tabs = remember { MainTabs.values() }

    Scaffold(
      bottomBar = {
        BottomBar(navController = navController, bottomBarState, tabs)
      },
    ) { innerPaddingModifier ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .blackGreenBackground(),
        horizontalAlignment = Alignment.CenterHorizontally
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

