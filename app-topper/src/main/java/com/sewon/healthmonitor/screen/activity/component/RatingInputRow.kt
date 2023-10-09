/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sewon.healthmonitor.screen.activity.component

import android.widget.RatingBar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import com.sewon.healthmonitor.R

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
