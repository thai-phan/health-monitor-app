package com.sewon.healthmonitor.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.sewon.healthmonitor.R


object AppDestinations {
    const val SPLASH_ROUTE = "splash_screen"
    const val TERM_AGREEMENT_ROUTE = "term_agreement"
    const val MAIN_ROUTE = "main"
    const val ACTIVITY_ROUTE = "main/activity"
    const val REPORT_ROUTE = "main/report"
    const val USER_ROUTE = "main/user"
}


enum class MainTabs(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    ACTIVITY(R.string.activity_page, R.drawable.ic_grain, AppDestinations.ACTIVITY_ROUTE),
    REPORT(R.string.report_page, R.drawable.baseline_list_alt_24, AppDestinations.REPORT_ROUTE),
    USER(R.string.user_page, R.drawable.baseline_manage_accounts_24, AppDestinations.USER_ROUTE),
}
