package com.sewon.healthmonitor.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sewon.healthmonitor.ui.ztemp.TestApp


// TODO: Check first load
// TODO: Navigate
// TODO: Run back ground
// TODO: Add multi language
// TODO: Back button


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        actionBar?.hide();
        super.onCreate(savedInstanceState)

        setContent {
            HealthApp {
                finish()
            }

//
        }
    }

    override fun onResume() {
        super.onResume()
    }
}
