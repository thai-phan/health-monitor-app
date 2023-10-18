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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sewon.topperhealth.common.Destinations
import com.sewon.topperhealth.common.MainTabs
import com.sewon.topperhealth.data.HealthDataStore
import com.sewon.topperhealth.screen.activity.SleepActivity
import com.sewon.topperhealth.screen.device.DeviceScreen
import com.sewon.topperhealth.screen.report.ReportScreen
import com.sewon.topperhealth.screen.setting.SettingScreen
import com.sewon.topperhealth.screen.term.TermAgreement
import com.sewon.topperhealth.screen.splash.SplashScreen


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

  val actions = remember(navController) { MainActions(navController) }
  NavHost(
    navController = navController, startDestination = startDestination
  ) {
    var redirectRoute = Destinations.TERM_AGREEMENT_ROUTE
    if (isAccepted.value) {
//      redirectRoute = MainDestinations.ACTIVITY_ROUTE
      redirectRoute = Destinations.TERM_AGREEMENT_ROUTE
    }

    composable(Destinations.SPLASH_ROUTE) {
      // Intercept back in Onboarding: make it finish the activity
      BackHandler {
        finishActivity()
      }

      SplashScreen(
        delayTime = 1000L
      ) {
        navController.navigate(redirectRoute)
      }
    }

    composable(Destinations.TERM_AGREEMENT_ROUTE) {
      TermAgreement(
        onRedirectRoute = {
          navController.navigate(Destinations.DEVICE_ROUTE)
        }
      )
    }

    composable(Destinations.DEVICE_ROUTE) {
      DeviceScreen(
        navController = navController,

        )
    }

    composable(
      "${MainTabs.ACTIVITY.route}/{deviceAddress}",
      arguments = listOf(navArgument("deviceAddress") { type = NavType.StringType })
    ) { backStackEntry ->
      val arguments = requireNotNull(backStackEntry.arguments)
      val deviceAddress = arguments.getString("deviceAddress")
//        val parentEntry = remember(backStackEntry) {
//            navController.getBackStackEntry("parentNavigationRoute")
//        }
//        val parentViewModel = hiltViewModel(parentEntry)
      if (deviceAddress != null) {
        SleepActivity(modifier, navController, deviceAddress = deviceAddress)
      }
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
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
  val onboardingComplete: () -> Unit = {
    navController.popBackStack()
  }

  // Used from COURSES_ROUTE
  val openCourse = { newCourseId: Long, from: NavBackStackEntry ->
    // In order to discard duplicated navigation events, we check the Lifecycle
    if (from.lifecycleIsResumed()) {
      navController.navigate("${Destinations.SETTING_ROUTE}/$newCourseId")
    }
  }

  // Used from COURSE_DETAIL_ROUTE
  val relatedCourse = { newCourseId: Long, from: NavBackStackEntry ->
    // In order to discard duplicated navigation events, we check the Lifecycle
    if (from.lifecycleIsResumed()) {
      navController.navigate("${Destinations.REPORT_ROUTE}/$newCourseId")
    }
  }

  // Used from COURSE_DETAIL_ROUTE
  val upPress: (from: NavBackStackEntry) -> Unit = { from ->
    // In order to discard duplicated navigation events, we check the Lifecycle
    if (from.lifecycleIsResumed()) {
      navController.navigateUp()
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

