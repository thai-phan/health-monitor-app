package com.sewon.healthmonitor.ui

import android.app.Activity
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

import com.sewon.healthmonitor.ui.theme.DataStoreTheme
import com.sewon.healthmonitor.ui.setting.TermAgreement
import android.view.animation.OvershootInterpolator
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sewon.healthmonitor.R
import kotlinx.coroutines.delay


// TODO: Check first load
// TODO: Navigate
// TODO: Run back ground



class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        actionBar?.hide();
        super.onCreate(savedInstanceState)

        setContent {
            HealthApp {
                finish()
            }
        }

//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                navigation_home,
//                navigation_dashboard,
//                navigation_notifications,
//                navigation_agreement
//
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
    }
//
////        setContentView(R.layout.fl_main_settings);
//
//        //If you want to insert data in your settings
////        val settingsFragment = AgreementFragment();
////        settingsFragment.
////        getSupportFragmentManager().beginTransaction().replace(R.id.test_frame).commit();
//
//        //Else
////        getSupportFragmentManager().beginTransaction().replace(R.id.test_frame, AgreementFragment()).commit();
//    }

    override fun onResume() {
        super.onResume()
//        val db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java, "health-monitor-db"
//        ).allowMainThreadQueries().build()
//        val userDao = db.userDao()
//        print("aaaaa")
//        val users: List<User> = userDao.getAll()
//
//        if (prefs!!.getBoolean("firstrun", true)) {
//            // Do first run stuff here then set 'firstrun' as false
//            setContentView(R.layout.fragment_agreement);
//            // using the following line to edit/commit prefs
//            prefs!!.edit().putBoolean("firstrun", false).commit()
//        } else {
//            setContentView(R.layout.activity_main);
//        }
    }
}

//
//import android.os.Bundle
//import android.view.animation.OvershootInterpolator
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material.Surface
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.sewon.healthmonitor.R
//import kotlinx.coroutines.delay
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
//                Navigation()
//            }
//        }
//        actionBar?.hide();
//    }
//}
//
//@Composable
//fun Navigation() {
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = "splash_screen"
//    ) {
//        composable("splash_screen") {
//            SplashScreen(navController = navController)
//        }
//
//        // Main Screen
//        composable("main_screen") {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                Text(text = "Main Screen", color = Color.Black, fontSize = 24.sp)
//            }
//        }
//    }
//}
//
//@Composable
//fun SplashScreen(navController: NavController) {
//    val scale = remember {
//        androidx.compose.animation.core.Animatable(0f)
//    }
//
//    // Animation
//    LaunchedEffect(key1 = true) {
//        scale.animateTo(
//            targetValue = 0.7f,
//            // tween Animation
//            animationSpec = tween(
//                durationMillis = 800,
//                easing = {
//                    OvershootInterpolator(4f).getInterpolation(it)
//                })
//        )
//        // Customize the delay time
//        delay(3000L)
//        navController.navigate("main_screen")
//    }
//
//    // Image
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        // Change the logo
//        Image(
//            painter = painterResource(id = R.drawable.ic_lockup_blue),
//            contentDescription = "Logo",
//            modifier = Modifier.scale(scale.value)
//        )
//    }
//}
