package com.sewon.healthmonitor.common

import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

fun NavGraphBuilder.mainNavGraph(
  onCourseSelected: (Long, NavBackStackEntry) -> Unit,
  onboardingComplete: State<Boolean>,
  navController: NavHostController,
  modifier: Modifier = Modifier
) {

}




