package com.sewon.topperhealth.ui.screen.activity.component

import android.widget.RatingBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun RatingInputRow(modifier: Modifier = Modifier, rating: Int, onRatingChange: (Int) -> Unit) {
  InputRow(modifier = modifier) {
    AndroidView(
      factory = { context ->
        RatingBar(context).apply {
          numStars = 4
          stepSize = 1f
        }
      },
      update = { ratingBar ->
        ratingBar.rating = rating.toFloat()
        ratingBar.setOnRatingBarChangeListener { _, _, _ ->
          onRatingChange(ratingBar.rating.toInt())
        }
      }
    )
  }
}

@Composable
fun InputRow(
  modifier: Modifier = Modifier,
  content: @Composable () -> Unit
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Box() {
      content()
    }
  }
}
