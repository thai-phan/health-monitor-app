package com.sewon.healthmonitor

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.sewon.healthmonitor.databinding.ActivityMainBinding
import com.sewon.healthmonitor.entity.User


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var prefs: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("com.sewon.healthmonitor", MODE_PRIVATE)

//
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




//        setContentView(R.layout.fl_main_settings);

        //If you want to insert data in your settings
//        val settingsFragment = AgreementFragment();
//        settingsFragment.
//        getSupportFragmentManager().beginTransaction().replace(R.id.test_frame).commit();

        //Else
//        getSupportFragmentManager().beginTransaction().replace(R.id.test_frame, AgreementFragment()).commit();
    }

    override fun onResume() {
        super.onResume()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
        val userDao = db.userDao()
        val users: List<User> = userDao.getAll()

        if (prefs!!.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            setContentView(R.layout.fragment_agreement);
            // using the following line to edit/commit prefs
            prefs!!.edit().putBoolean("firstrun", false).commit()
        } else {
            setContentView(R.layout.activity_main);
        }
    }
}