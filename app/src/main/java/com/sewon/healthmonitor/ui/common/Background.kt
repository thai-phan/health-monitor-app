package com.sewon.healthmonitor.ui.common

import android.annotation.SuppressLint
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import com.sewon.healthmonitor.ui.theme.BackgroundBottom
import com.sewon.healthmonitor.ui.theme.BackgroundMiddle
import com.sewon.healthmonitor.ui.theme.BackgroundTop


@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.blackGreenBackground(): Modifier = this.composed {
        Modifier.drawWithCache {
            val gradientBrush = Brush.verticalGradient(
                listOf(BackgroundTop, BackgroundMiddle, BackgroundBottom)
            )
            onDrawBehind {
                drawRect(gradientBrush)
            }
        }
}