package com.sewon.topperhealth.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sewon.topperhealth.R
import com.sewon.topperhealth.screen.a0common.theme.topperTypography
import kotlinx.coroutines.delay


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SplashScreen(modifier: Modifier = Modifier, delayTime: Long, onRedirectRoute: () -> Unit) {
  val scale = remember {
    Animatable(0f)
  }

  // Animation
  LaunchedEffect(key1 = true) {
    scale.animateTo(
      targetValue = 0.7f,
      // tween Animation
      animationSpec = tween(
        durationMillis = 800,
        easing = {
          OvershootInterpolator(4f).getInterpolation(it)
        })
    )
    // Customize the delay time
    delay(delayTime)
    onRedirectRoute()
  }


  Column(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 20.dp),
  ) {
    Column(
      modifier = Modifier
        .weight(3f),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Bottom
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .weight(2f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Image(
          modifier = Modifier
            .fillMaxSize(0.4f),
          painter = painterResource(id = R.drawable.ic_intellinest_white),
          contentDescription = "intellinest",
        )
      }
      Column(
        modifier = Modifier
//          .border(BorderStroke(1.dp, Color.Red))
          .weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Text(stringResource(R.string.app_name), style = topperTypography.headlineMedium)
        FlowRow {
          Text("수면건강 ", style = topperTypography.headlineMedium)
          Text("모니터링 ", style = topperTypography.headlineMedium)
          Text("시스템", style = topperTypography.headlineMedium)
        }
      }
    }

    Column(
      modifier = Modifier
        .weight(2f),
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally

    ) {
      Image(
        painter = painterResource(id = R.mipmap.mm_sewon_white_foreground),
        contentDescription = "Logo",
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(0.5f)
      )
    }
  }
}
