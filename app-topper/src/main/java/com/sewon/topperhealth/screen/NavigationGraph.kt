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


@Composable
fun NavigationGraph(
  modifier: Modifier = Modifier,
  finishActivity: () -> Unit = {},
  navController: NavHostController = rememberNavController(),
  showOnboardingInitially: Boolean = false
) {

  val startDestination: String = Destinations.SPLASH_ROUTE

  val mainStartDestination = Destinations.REPORT_ROUTE

  val context = LocalContext.current
  val store = HealthDataStore(context)
  val isAccepted = store.getIsTermAgreementAccepted.collectAsState(initial = false)

  val onboardingComplete = remember(showOnboardingInitially) {
    mutableStateOf(!showOnboardingInitially)
  }

  NavHost(
    navController = navController, startDestination = startDestination
  ) {
    var redirectRoute = Destinations.TERM_AGREEMENT_ROUTE
    if (isAccepted.value) {
      redirectRoute = Destinations.ACTIVITY_ROUTE
//      redirectRoute = Destinations.TERM_AGREEMENT_ROUTE
    }

    composable(Destinations.SPLASH_ROUTE) {
      // Intercept back in Onboarding: make it finish the activity
      BackHandler {
        finishActivity()
      }

      SplashScreen(
        modifier,
        delayTime = 1000L
      ) {
        navController.navigate(redirectRoute)
      }
    }

    composable(Destinations.TERM_AGREEMENT_ROUTE) {
      TermAgreement(
        modifier
      ) {
        store.acceptTermAgreement(true)
        navController.navigate(Destinations.DEVICE_ROUTE)
      }
    }

    composable(Destinations.DEVICE_ROUTE) {
      DeviceScreen(
        modifier
      ) {
        navController.navigate(Destinations.ACTIVITY_ROUTE) {
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

    composable(MainTabs.ACTIVITY.route) { backStackEntry ->
//        val parentEntry = remember(backStackEntry) {
//            navController.getBackStackEntry("parentNavigationRoute")
//        }
//        val parentViewModel = hiltViewModel(parentEntry)
      SleepActivity(modifier, redirectReportPage = {
        navController.navigate(Destinations.REPORT_ROUTE) {
          popUpTo(navController.graph.startDestinationId) {
            saveState = true
          }
          launchSingleTop = true
          restoreState = true
        }
      })
      // Show onboarding instead if not shown yet.
//        LaunchedEffect(onboardingComplete) {
//            if (!onboardingComplete.value) {
//                navController.navigate(AppDestinations.SPLASH_ROUTE)
//            }
//        }
//        if (onboardingComplete.value) { // Avoid glitch when showing onboarding
//            FeaturedCourses(
//                courses = courses,
//                selectCourse = { id -> onCourseSelected(id, from) },
//                modifier = modifier
//            )
//        }

    }

    composable(Destinations.REPORT_ROUTE) { from ->
      ReportScreen(modifier)
    }

    composable(Destinations.SETTING_ROUTE) {
      SettingScreen(modifier)
    }

//        composable(
//            "${MainDestinations.COURSE_DETAIL_ROUTE}/{$COURSE_DETAIL_ID_KEY}",
//            arguments = listOf(
//                navArgument(COURSE_DETAIL_ID_KEY) { type = NavType.LongType }
//            )
//        ) { backStackEntry: NavBackStackEntry ->
//            val arguments = requireNotNull(backStackEntry.arguments)
//            val currentCourseId = arguments.getLong(COURSE_DETAIL_ID_KEY)
//            CourseDetails(
//                courseId = currentCourseId,
//                selectCourse = { newCourseId ->
//                    actions.relatedCourse(newCourseId, backStackEntry)
//                },
//                upPress = { actions.upPress(backStackEntry) }
//            )
//        }
  }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
  this.lifecycle.currentState == Lifecycle.State.RESUMED

