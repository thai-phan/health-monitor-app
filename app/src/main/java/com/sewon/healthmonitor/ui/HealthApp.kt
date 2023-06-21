package com.sewon.healthmonitor.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sewon.healthmonitor.courses.CourseTabs
import com.sewon.healthmonitor.ui.theme.DataStoreTheme
import com.sewon.healthmonitor.ui.setting.TermAgreement
import com.sewon.healthmonitor.ui.theme.BlueTheme
import java.util.Locale

@Composable
fun HealthApp(finishActivity: () -> Unit) {
    BlueTheme {
        // A surface container using the 'background' color from the theme
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            TermAgreement()
//        }
        val tabs = remember { CourseTabs.values() }
        val navController = rememberNavController()
        Scaffold(
            backgroundColor = androidx.compose.material.MaterialTheme.colors.primarySurface,
            bottomBar = { OwlBottomBar(navController = navController, tabs) }
        ) { innerPaddingModifier ->
            NavGraph(
                finishActivity = finishActivity,
                navController = navController,
                modifier = Modifier.padding(innerPaddingModifier)
            )
        }
    }
}

@Composable
fun OwlBottomBar(navController: NavController, tabs: Array<CourseTabs>) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
        ?: CourseTabs.FEATURED.route

    val routes = remember { CourseTabs.values().map { it.route } }
    if (currentRoute in routes) {
        BottomNavigation(
            Modifier.windowInsetsBottomHeight(
                WindowInsets.navigationBars.add(WindowInsets(bottom = 56.dp))
            )
        ) {
            tabs.forEach { tab ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(tab.icon), contentDescription = null) },
                    label = { Text(stringResource(tab.title).uppercase(Locale.getDefault())) },
                    selected = currentRoute == tab.route,
                    onClick = {
                        if (tab.route != currentRoute) {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    alwaysShowLabel = false,
                    selectedContentColor = androidx.compose.material.MaterialTheme.colors.secondary,
                    unselectedContentColor = LocalContentColor.current,
                    modifier = Modifier.navigationBarsPadding()
                )
            }
        }
    }
}
