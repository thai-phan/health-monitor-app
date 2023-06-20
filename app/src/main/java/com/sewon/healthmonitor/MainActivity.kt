package com.sewon.healthmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sewon.healthmonitor.config.AppDatabase
import com.sewon.healthmonitor.databinding.ActivityMainBinding
import com.sewon.healthmonitor.entity.User
import com.sewon.healthmonitor.theme.DataStoreTheme
import com.sewon.healthmonitor.ui.setting.TermAgreement
import com.sewon.healthmonitor.ui.setting.UserSettings
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sewon.healthmonitor.R.id.*


// TODO: Check first load

// TODO: Navigate
// TODO: Run back ground

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            DataStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserSettings()
                }
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                navigation_home,
                navigation_dashboard,
                navigation_notifications,
                navigation_agreement

            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }




//
//
//
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

