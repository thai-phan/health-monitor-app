
package com.sewon.healthmonitor.ui.mainview

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sewon.healthmonitor.model.courses
import com.sewon.healthmonitor.ui.AppDestinations
import com.sewon.healthmonitor.ui.MainTabs
import com.sewon.healthmonitor.ui.courses.FeaturedCourses
import com.sewon.healthmonitor.ui.courses.MyCourses
import com.sewon.healthmonitor.ui.usersetting.User

fun NavGraphBuilder.mainNavGraph(
    onCourseSelected: (Long, NavBackStackEntry) -> Unit,
    onboardingComplete: State<Boolean>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    composable(MainTabs.ACTIVITY.route) { from ->
        // Show onboarding instead if not shown yet.
        LaunchedEffect(onboardingComplete) {
            if (!onboardingComplete.value) {
                navController.navigate(AppDestinations.ONBOARDING_ROUTE)
            }
        }
        if (onboardingComplete.value) { // Avoid glitch when showing onboarding
            FeaturedCourses(
                courses = courses,
                selectCourse = { id -> onCourseSelected(id, from) },
                modifier = modifier
            )
        }
    }

    composable(MainTabs.REPORT.route) { from ->
        MyCourses(
            courses = courses,
            { id -> onCourseSelected(id, from) },
            modifier
        )
    }

    composable(MainTabs.USER.route) {
        User()
    }

//    composable(MainTabs.USER.route) { from ->
//        MyCourses(
//            courses = courses,
//            { id -> onCourseSelected(id, from) },
//            modifier
//        )
//    }

}




