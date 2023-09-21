package com.sewon.healthmonitor.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sewon.healthmonitor.R


object AppDestinations {
  const val SPLASH_ROUTE = "splash_screen"
  const val TERM_AGREEMENT_ROUTE = "term_agreement"
  const val DEVICE_ROUTE = "device"
  const val MAIN_ROUTE = "main"

}

object MainDestinations {
  const val ACTIVITY_ROUTE = "main/activity"
  const val REPORT_ROUTE = "main/report"
  const val USER_ROUTE = "main/user"
}


enum class MainTabs(
  @StringRes val title: Int,
  @DrawableRes val icon: Int,
  val route: String
) {
  ACTIVITY(R.string.activity_page, R.drawable.ic_tab_activity, MainDestinations.ACTIVITY_ROUTE),
  REPORT(R.string.report_page, R.drawable.ic_tab_report, MainDestinations.REPORT_ROUTE),
  USER(R.string.user_page, R.drawable.ic_tab_setting, MainDestinations.USER_ROUTE),
}
