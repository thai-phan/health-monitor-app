
package com.sewon.healthmonitor.ui.mainview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sewon.healthmonitor.R
import com.sewon.healthmonitor.model.courses
import com.sewon.healthmonitor.model.topics
import com.sewon.healthmonitor.ui.AppDestinations
import com.sewon.healthmonitor.ui.MainTabs
import com.sewon.healthmonitor.ui.courses.FeaturedCourses
import com.sewon.healthmonitor.ui.courses.MyCourses

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




