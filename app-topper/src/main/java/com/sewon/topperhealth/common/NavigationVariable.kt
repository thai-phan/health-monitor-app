package com.sewon.topperhealth.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sewon.topperhealth.R


object Destinations {
  const val SPLASH_ROUTE = "splash_screen"
  const val TERM_AGREEMENT_ROUTE = "term_agreement"
  const val DEVICE_ROUTE = "device"
  const val ACTIVITY_ROUTE = "main/activity"
  const val REPORT_ROUTE = "main/report"
  const val SETTING_ROUTE = "main/setting"
}


enum class MainTabs(
  @StringRes val title: Int,
  @DrawableRes val icon: Int,
  val route: String
) {
  ACTIVITY(R.string.activity_page, R.drawable.ic_tab_activity, Destinations.ACTIVITY_ROUTE),
  REPORT(R.string.report_page, R.drawable.ic_tab_report, Destinations.REPORT_ROUTE),
  SETTING(R.string.setting_page, R.drawable.ic_tab_setting, Destinations.SETTING_ROUTE),
}
