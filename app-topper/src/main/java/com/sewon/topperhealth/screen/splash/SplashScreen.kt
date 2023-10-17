package com.sewon.topperhealth.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sewon.topperhealth.R
import kotlinx.coroutines.delay


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SplashScreen(delayTime: Long, onRedirectRoute: () -> Unit) {
  val scale = remember {
    androidx.compose.animation.core.Animatable(0f)
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
    verticalArrangement = Arrangement.SpaceBetween,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .statusBarsPadding()
      .systemBarsPadding()
      .fillMaxSize(),
  ) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.End
    ) {
      Image(
        painter = painterResource(id = R.mipmap.ic_intellinest_foreground),
        contentDescription = "intellinest",
        modifier = Modifier
          .size(200.dp)
      )
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
      Image(
        painter = painterResource(id = R.drawable.ic_splash_icon),
        contentDescription = "splash",
        modifier = Modifier
          .size(100.dp)
          .scale(scale.value)
      )
      Text("인텔리네스트 ", fontSize = 26.sp)
      FlowRow {
        Text("수면건강 ", fontSize = 26.sp)
        Text("모니터링 ", fontSize = 26.sp)
        Text("시스템", fontSize = 26.sp)
      }

    }
    Image(
      painter = painterResource(id = R.mipmap.ic_sewon_foreground),
      contentDescription = "sewon",
      modifier = Modifier
        .size(250.dp)
    )
  }
}
