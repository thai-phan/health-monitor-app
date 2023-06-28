package com.sewon.healthmonitor.ui

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
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.sewon.healthmonitor.config.AppDataStore
import com.sewon.healthmonitor.ui.mainview.mainNavGraph
import com.sewon.healthmonitor.ui.termagreement.TermAgreement
import com.sewon.healthmonitor.ui.splashscreen.SplashScreen




@Composable
fun NavGraph(
    modifier: Modifier = Modifier,
    finishActivity: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestinations.SPLASH_ROUTE,
    showOnboardingInitially: Boolean = false
) {
    val context = LocalContext.current
    val store = AppDataStore(context)
    val isAccepted = store.getIsTermAgreementAccepted.collectAsState(initial = false)

    // Onboarding could be read from shared preferences.
    val onboardingComplete = remember(showOnboardingInitially) {
        mutableStateOf(!showOnboardingInitially)
    }

    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        var redirectRoute = AppDestinations.TERM_AGREEMENT_ROUTE
        if (isAccepted.value) {
            redirectRoute = AppDestinations.ACTIVITY_ROUTE
        }
//        // Onboarding could be read from shared preferences.
//        val onboardingComplete = remember(showOnboardingInitially) {
//            mutableStateOf(!showOnboardingInitially)
//        }


//        if onboardingComplete

        composable(AppDestinations.SPLASH_ROUTE) {
            SplashScreen(
                navController = navController,
                redirectRoute = redirectRoute
            )
        }

        composable(AppDestinations.TERM_AGREEMENT_ROUTE) {
            // Intercept back in Onboarding: make it finish the activity
            BackHandler {
                finishActivity()
            }

            TermAgreement(
                navController = navController,
                redirectRoute = AppDestinations.ACTIVITY_ROUTE
            )
        }

        navigation(
            route = AppDestinations.MAIN_ROUTE,
            startDestination = MainTabs.USER.route
        ) {
            mainNavGraph(
                onCourseSelected = actions.openCourse,
                onboardingComplete = onboardingComplete,
                navController = navController,
                modifier = modifier
            )
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
            navController.navigate("${AppDestinations.USER_ROUTE}/$newCourseId")
        }
    }

    // Used from COURSE_DETAIL_ROUTE
    val relatedCourse = { newCourseId: Long, from: NavBackStackEntry ->
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${AppDestinations.REPORT_ROUTE}/$newCourseId")
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

