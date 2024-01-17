package com.sewon.topperhealth.ui.screen.report.component


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.sewon.topperhealth.data.DataStoreManager

@Composable
fun CircularChart(
  modifier: Modifier = Modifier,
  label: String,
  angle: Float = 50f,
  brush: Brush = Brush.verticalGradient(listOf(Color.White, Color.Black)),
  backgroundCircleColor: Color = Color.LightGray.copy(alpha = 0.3f),
  thickness: Dp = 12.dp,
  gapBetweenCircles: Dp = 20.dp
) {
  val context = LocalContext.current
  val dataStore = DataStoreManager(context)

  val isLogShowed by remember { dataStore.isLogShowed }.collectAsState(initial = false)

  val sweepAngles = 360 * angle / 100

  var sizeCom by remember { mutableStateOf(IntSize.Zero) }

  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Canvas(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f)
        .onGloballyPositioned { coordinates ->
          sizeCom = coordinates.size
        }
    ) {
      var arcRadius = sizeCom.width.toFloat()

      arcRadius -= gapBetweenCircles.toPx()

      drawCircle(
        color = backgroundCircleColor,
        radius = arcRadius / 2,
        style = Stroke(width = thickness.toPx(), cap = StrokeCap.Butt)
      )

      drawArc(
        brush = brush,
        startAngle = -90f,
        sweepAngle = sweepAngles,
        useCenter = false,
        style = Stroke(width = thickness.toPx(), cap = StrokeCap.Round),
        size = Size(arcRadius, arcRadius),
        topLeft = Offset(
          x = (sizeCom.width.toFloat() - arcRadius) / 2,
          y = (sizeCom.width.toFloat() - arcRadius) / 2
        ),
        blendMode = BlendMode.SrcOver
      )
    }
    if (isLogShowed) {
      Text(angle.toString())
    }

    Text(label)
  }
}