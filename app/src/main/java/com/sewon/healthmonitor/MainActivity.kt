package com.sewon.healthmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.sewon.healthmonitor.api.ServerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import timber.log.Timber.Forest.plant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Add multi language
// TODO: Check first load

// TODO: Loading state

// TODO: Run back ground

// TODO: Back button

// TODO: Navigate


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        actionBar?.hide();
        super.onCreate(savedInstanceState)

//        val quotesApi = ServerService.create()
//        // launching a new coroutine
//        lifecycleScope.launch {
//            val result = quotesApi.testServer()
//            Timber.d("ayush: ", result.toString())
//        }



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
