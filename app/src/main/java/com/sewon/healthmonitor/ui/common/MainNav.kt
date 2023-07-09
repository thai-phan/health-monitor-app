
package com.sewon.healthmonitor.ui.common

import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sewon.healthmonitor.ui.MainTabs
import com.sewon.healthmonitor.ui.screen.activity.SleepActivity
import com.sewon.healthmonitor.ui.screen.report.Report
import com.sewon.healthmonitor.ui.screen.setting.UserSetting

fun NavGraphBuilder.mainNavGraph(
    onCourseSelected: (Long, NavBackStackEntry) -> Unit,
    onboardingComplete: State<Boolean>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    composable(MainTabs.ACTIVITY.route) { from ->
        SleepActivity()
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

    composable(MainTabs.REPORT.route) { from ->
        Report()
//        MyCourses(
//            courses = courses,
//            { id -> onCourseSelected(id, from) },
//            modifier
//        )
    }

    composable(MainTabs.USER.route) {
        UserSetting()
    }

//    composable(MainTabs.USER.route) { from ->
//        MyCourses(
//            courses = courses,
//            { id -> onCourseSelected(id, from) },
//            modifier
//        )
//    }

}




