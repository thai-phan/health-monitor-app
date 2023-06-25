/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sewon.healthmonitor.ui.courses

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
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
import com.sewon.healthmonitor.ui.MainDestinations

fun NavGraphBuilder.courses(
    onCourseSelected: (Long, NavBackStackEntry) -> Unit,
    onboardingComplete: State<Boolean>, // https://issuetracker.google.com/174783110
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    composable(MainTabs.ACTIVITY.route) { from ->
        // Show onboarding instead if not shown yet.
        LaunchedEffect(onboardingComplete) {
            if (!onboardingComplete.value) {
                navController.navigate(MainDestinations.ONBOARDING_ROUTE)
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

//    composable(CourseTabs.SEARCH.route) {
//        SearchCourses(topics, modifier)
//    }
}

@Composable
fun CoursesAppBar() {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.height(80.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterVertically),
            painter = painterResource(id = R.drawable.ic_lockup_white),
            contentDescription = null
        )
        IconButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = { /* todo */ }
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = stringResource(R.string.label_profile)
            )
        }
    }
}

/**
 * Destinations used in the ([OwlApp]).
 */
private object HealthDestinations {
    const val ACTIVITY_ROUTE = "courses/activity"
    const val REPORT_ROUTE = "courses/report"
    const val USER_ROUTE = "courses/user"

//
//    const val FEATURED_ROUTE = "courses/featured"
//    const val MY_COURSES_ROUTE = "courses/my"
//    const val SEARCH_COURSES_ROUTE = "courses/search"

}

enum class MainTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    ACTIVITY(R.string.activity_page, R.drawable.ic_grain, HealthDestinations.ACTIVITY_ROUTE),
    REPORT(R.string.report_page, R.drawable.ic_featured, HealthDestinations.REPORT_ROUTE),
    USER(R.string.user_page, R.drawable.ic_grain, HealthDestinations.USER_ROUTE),
//    MY_COURSES(R.string.my_courses, R.drawable.ic_grain, HealthDestinations.MY_COURSES_ROUTE),
//    FEATURED(R.string.featured, R.drawable.ic_featured, HealthDestinations.FEATURED_ROUTE),
//    SEARCH(R.string.search, R.drawable.ic_search, HealthDestinations.SEARCH_COURSES_ROUTE)
}


