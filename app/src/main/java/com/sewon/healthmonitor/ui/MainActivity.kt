package com.sewon.healthmonitor.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


// TODO: Check first load
// TODO: Navigate
// TODO: Run back ground
// TODO: Add multi language

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
}
