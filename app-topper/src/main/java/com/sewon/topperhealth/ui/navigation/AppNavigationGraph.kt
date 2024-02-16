package com.sewon.topperhealth.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sewon.topperhealth.data.DataStoreManager
import com.sewon.topperhealth.ui.screen.activity.SleepActivity
import com.sewon.topperhealth.ui.screen.advise.AdviseScreen
import com.sewon.topperhealth.ui.screen.device.DeviceScreen
import com.sewon.topperhealth.ui.screen.report.ReportScreen
import com.sewon.topperhealth.ui.screen.setting.SettingScreen
import com.sewon.topperhealth.ui.screen.splash.SplashScreen
import com.sewon.topperhealth.ui.screen.term.TermAgreement

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun AppNavigationGraph(
  modifier: Modifier = Modifier,
  finishActivity: () -> Unit = {},
  navController: NavHostController = rememberNavController(),
) {

  val startDestination: String = Destinations.SPLASH_ROUTE

  val context = LocalContext.current

  val dataStore = DataStoreManager(context)

  val isAccepted = remember {
    dataStore.isTermAccepted
  }.collectAsState(initial = false)

  val splashScreenRedirect = remember {
    mutableStateOf(Destinations.TERM_AGREEMENT_ROUTE)
  }

  if (isAccepted.value) {
    splashScreenRedirect.value = Destinations.DEVICE_ROUTE
  }

  NavHost(navController = navController, startDestination = startDestination) {
    composable(Destinations.SPLASH_ROUTE) {
      SplashScreen(
        modifier,
        delayTime = 1000L
      ) {
        navController.navigate(splashScreenRedirect.value)
      }
    }

    composable(Destinations.TERM_AGREEMENT_ROUTE) {
      TermAgreement(
        modifier
      ) {
        CoroutineScope(Dispatchers.IO).launch {
          dataStore.saveAcceptTerm(true)
        }
        navController.navigate(Destinations.DEVICE_ROUTE)
      }
    }

    composable(Destinations.DEVICE_ROUTE) {
      DeviceScreen(modifier) {
        navigateWithState(navController, Destinations.ACTIVITY_ROUTE)
      }
    }

    composable(MainTabs.ACTIVITY.route) { backStackEntry ->
      BackHandler {
        finishActivity()
      }
//        val parentEntry = remember(backStackEntry) {
//            navController.getBackStackEntry("parentNavigationRoute")
//        }
//        val parentViewModel = hiltViewModel(parentEntry)
      SleepActivity(modifier, redirectAdvisePage = {
        navigateWithState(navController, Destinations.ADVISE_ROUTE)
      }) {
        navigateWithState(navController, Destinations.REPORT_ROUTE)
      }
    }

    composable(MainTabs.REPORT.route) { from ->
      ReportScreen(modifier)
    }

    composable(MainTabs.SETTING.route) {
      SettingScreen(modifier)
    }

    composable(Destinations.ADVISE_ROUTE) {
      AdviseScreen(modifier) {
        navigateWithState(navController, Destinations.REPORT_ROUTE)
      }
    }
  }
}


fun navigateWithState(navController: NavController, route: String) {
  navController.navigate(route) {
//    popUpTo(navController.graph.startDestinationId) {
//      // Save backstack state. This will ensure restoration of
//      // nested navigation screen when the user comes back to
//      // the destination.
//      saveState = false
//    }
    // prevent duplicate destinations when the navigation is
    // clicked multiple times
    launchSingleTop = true
    // restore state if previously saved
    restoreState = true
  }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
  this.lifecycle.currentState == Lifecycle.State.RESUMED

