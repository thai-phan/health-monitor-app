package com.sewon.healthmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.sewon.healthmonitor.HealthApp
import dagger.hilt.android.AndroidEntryPoint


// TODO: Check first load
// TODO: Navigate
// TODO: Run back ground
// TODO: Add multi language
// TODO: Back button

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        actionBar?.hide();
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            HealthApp {
                finish()
            }

        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
