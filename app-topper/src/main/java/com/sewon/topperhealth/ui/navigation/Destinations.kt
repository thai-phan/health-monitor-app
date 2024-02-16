package com.sewon.topperhealth.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sewon.topperhealth.R


object Destinations {
  const val SPLASH_ROUTE = "splash_screen"
  const val TERM_AGREEMENT_ROUTE = "term_agreement_screen"
  const val DEVICE_ROUTE = "device_screen"
  const val ACTIVITY_ROUTE = "activity_screen"
  const val REPORT_ROUTE = "report_screen"
  const val SETTING_ROUTE = "setting_screen"
  const val ADVISE_ROUTE = "advise_screen"
}


enum class MainTabs(
  @StringRes val title: Int,
  @DrawableRes val icon: Int,
  val route: String
) {
  ACTIVITY(
    R.string.activity,
    R.drawable.ic_tab_activity,
    Destinations.ACTIVITY_ROUTE
  ),
  REPORT(
    R.string.report,
    R.drawable.ic_tab_report,
    Destinations.REPORT_ROUTE
  ),
  SETTING(
    R.string.setting,
    R.drawable.ic_tab_setting,
    Destinations.SETTING_ROUTE
  ),
}
