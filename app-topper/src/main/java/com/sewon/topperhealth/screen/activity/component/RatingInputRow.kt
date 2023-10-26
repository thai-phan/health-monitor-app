package com.sewon.topperhealth.screen.activity.component

import android.widget.RatingBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.topperhealth.R

/**
 * Showing star rating by using RatingBar through AndroidView
 */
@Composable
fun RatingInputRow(rating: Int, onRatingChange: (Int) -> Unit, modifier: Modifier = Modifier) {
  InputRow(inputLabel = stringResource(R.string.app_name), modifier = modifier) {
    AndroidView(
      factory = { context ->
        RatingBar(context).apply {
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
  inputLabel: String,
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
