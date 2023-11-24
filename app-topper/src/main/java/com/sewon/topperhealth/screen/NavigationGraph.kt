package com.sewon.topperhealth.screen

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sewon.topperhealth.data.HealthDataStore
import com.sewon.topperhealth.screen.a0common.Destinations
import com.sewon.topperhealth.screen.a0common.MainTabs
import com.sewon.topperhealth.screen.activity.SleepActivity
import com.sewon.topperhealth.screen.device.DeviceScreen
import com.sewon.topperhealth.screen.report.ReportScreen
import com.sewon.topperhealth.screen.setting.SettingScreen
import com.sewon.topperhealth.screen.splash.SplashScreen
import com.sewon.topperhealth.screen.term.TermAgreement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun NavigationGraph(
  modifier: Modifier = Modifier,
  finishActivity: () -> Unit = {},
  navController: NavHostController = rememberNavController(),
) {

  val startDestination: String = Destinations.SPLASH_ROUTE

  val context = LocalContext.current

  val dataStore = HealthDataStore(context)

  val isAccepted = remember {
    dataStore.getIsTermAgreementAccepted
  }.collectAsState(initial = false)


  val splashScreenRedirect = remember {
    mutableStateOf(Destinations.TERM_AGREEMENT_ROUTE)
  }

  if (isAccepted.value) {
    splashScreenRedirect.value = Destinations.DEVICE_ROUTE
  }

  NavHost(
    navController = navController, startDestination = startDestination
  ) {
    composable(Destinations.SPLASH_ROUTE) {
      BackHandler {
        finishActivity()
      }

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
          dataStore.acceptTermAgreement(true)
        }
        navController.navigate(Destinations.DEVICE_ROUTE)
      }
    }

    composable(Destinations.DEVICE_ROUTE) {
      DeviceScreen(
        modifier
      ) {
        navController.navigate(Destinations.ACTIVITY_ROUTE) {
          popUpTo(navController.graph.startDestinationId) {
            saveState = true
          }
          launchSingleTop = true
          restoreState = true
        }
      }
    }

    composable(MainTabs.ACTIVITY.route) { backStackEntry ->
//        val parentEntry = remember(backStackEntry) {
//            navController.getBackStackEntry("parentNavigationRoute")
//        }
//        val parentViewModel = hiltViewModel(parentEntry)
      SleepActivity(modifier) {
        navController.navigate(Destinations.REPORT_ROUTE) {
          //          TODO: research
          popUpTo(navController.graph.startDestinationId) {
            // Save backstack state. This will ensure restoration of
            // nested navigation screen when the user comes back to
            // the destination.
            saveState = true
          }
          // prevent duplicate destinations when the navigation is
          // clicked multiple times
          launchSingleTop = true
          // restore state if previously saved
          restoreState = true
        }
      }
    }

    composable(Destinations.REPORT_ROUTE) { from ->
      ReportScreen(modifier)
    }

    composable(Destinations.SETTING_ROUTE) {
      SettingScreen(modifier)
    }
  }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
  this.lifecycle.currentState == Lifecycle.State.RESUMED

