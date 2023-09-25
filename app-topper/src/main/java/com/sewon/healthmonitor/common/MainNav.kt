package com.sewon.healthmonitor.common

import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.sewon.healthmonitor.screen.activity.SleepActivity
import com.sewon.healthmonitor.screen.report.Report
import com.sewon.healthmonitor.screen.setting.UserSetting

fun NavGraphBuilder.mainNavGraph(
  onCourseSelected: (Long, NavBackStackEntry) -> Unit,
  onboardingComplete: State<Boolean>,
  navController: NavHostController,
  modifier: Modifier = Modifier
) {

}




