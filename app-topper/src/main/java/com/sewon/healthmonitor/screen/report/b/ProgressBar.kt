package com.sewon.healthmonitor.screen.report.b


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sewon.healthmonitor.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Composable
fun ProgressBar(
  value: Float = 0f
) {
  Canvas(
    modifier = Modifier
      .fillMaxWidth()
      .height(10.dp)
      .padding(start = 10.dp, end = 10.dp)
  ) {

    // Background indicator
    drawLine(
      color = Color.LightGray.copy(alpha = 0.3f),
      cap = StrokeCap.Round,
      strokeWidth = size.height,
      start = Offset(x = 0f, y = 0f),
      end = Offset(x = size.width, y = 0f)
    )

    // Convert the downloaded percentage into progress (width of foreground indicator)
    // size.width returns the width of the canvas
    val progress = (value / 100) * size.width

    // Foreground indicator
    drawLine(
      brush = Brush.linearGradient(
        colors = listOf(
          Color(0xFF6ce0c4),
          Color(0xFF40c7e7),
          Color(0xFF6ce0c4),
          Color(0xFF40c7e7)
        )
      ),
      cap = StrokeCap.Round,
      strokeWidth = size.height,
      start = Offset(x = 0f, y = 0f),
      end = Offset(x = progress, y = 0f)
    )

  }

  Spacer(modifier = Modifier.height(8.dp))
}

