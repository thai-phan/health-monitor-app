package com.sewon.healthmonitor.ui

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


// TODO: Check first load
// TODO: Navigate
// TODO: Run back ground
// TODO: Add multi language
// TODO: Back button
// TODO: Scroll view

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
