package com.sewon.healthmonitor

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sewon.healthmonitor.R.id.*
import com.sewon.healthmonitor.databinding.ActivityMainBinding
import com.sewon.healthmonitor.ui.agreement.AgreementFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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



        super.onCreate(savedInstanceState);
        setContentView(R.layout.fl_main_settings);

        //If you want to insert data in your settings
//        val settingsFragment = AgreementFragment();
//        settingsFragment.
//        getSupportFragmentManager().beginTransaction().replace(R.id.test_frame).commit();

        //Else
        getSupportFragmentManager().beginTransaction().replace(R.id.test_frame, AgreementFragment()).commit();
    }
}