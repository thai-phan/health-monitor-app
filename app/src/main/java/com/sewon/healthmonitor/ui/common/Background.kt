package com.sewon.healthmonitor.ui.common

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.blackGreenBackground(): Modifier = this.composed {
        Modifier.drawWithCache {
            val gradientBrush = Brush.verticalGradient(
                listOf(Color(0xff006d63), Color(0xff091d3f), Color(0xff071224))
            )
            onDrawBehind {
                drawRect(gradientBrush)
            }
        }
}