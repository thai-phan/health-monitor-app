package com.sewon.topperhealth.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sewon.topperhealth.R

private val fonts = FontFamily(
  Font(R.font.suite_regular),
)

val topperTypography = typographyFromDefaults(
  displayLarge = TextStyle(),
  displayMedium = TextStyle(),
  displaySmall = TextStyle(),
  headlineLarge = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 32.sp,
    lineHeight = 40.sp,
    letterSpacing = 0.sp,
    color = Color.White,
  ),
  headlineMedium = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 26.sp,
    lineHeight = 36.sp,
    letterSpacing = 0.sp,
    color = Color.White,
  ),
  headlineSmall = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = 0.sp,
    color = Color.White,
  ),
  titleLarge = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp,
    color = Color.White,
  ),
  titleMedium = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp,
    color = Color.White,
  ),
  titleSmall = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.1.sp,
    color = Color.White,
  ),
  bodyLarge = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.15.sp,
    color = Color.White,
  ),
  bodyMedium = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.25.sp,
    color = Color.White,
  ),
  bodySmall = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.4.sp
  ),
  labelLarge = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.1.sp,
    color = Color.White,
  ),
  labelMedium = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    color = Color.White,
  ),
  labelSmall = TextStyle(
    fontFamily = fonts,
    fontWeight = FontWeight.SemiBold,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp,
    color = Color.White,
  )
)

fun typographyFromDefaults(
  displayLarge: TextStyle,
  displayMedium: TextStyle,
  displaySmall: TextStyle,
  headlineLarge: TextStyle,
  headlineMedium: TextStyle,
  headlineSmall: TextStyle,
  titleLarge: TextStyle,
  titleMedium: TextStyle,
  titleSmall: TextStyle,
  bodyLarge: TextStyle,
  bodyMedium: TextStyle,
  bodySmall: TextStyle,
  labelLarge: TextStyle,
  labelMedium: TextStyle,
  labelSmall: TextStyle,
): Typography {
  val defaults = Typography()
  return Typography(
    displayLarge = defaults.displayLarge.merge(displayLarge),
    displayMedium = defaults.displayMedium.merge(displayMedium),
    displaySmall = defaults.displaySmall.merge(displaySmall),
    headlineLarge = defaults.headlineLarge.merge(headlineLarge),
    headlineMedium = defaults.headlineMedium.merge(headlineMedium),
    headlineSmall = defaults.headlineSmall.merge(headlineSmall),
    titleLarge = defaults.titleLarge.merge(titleLarge),
    titleMedium = defaults.titleMedium.merge(titleMedium),
    titleSmall = defaults.titleSmall.merge(titleSmall),
    bodyLarge = defaults.bodyLarge.merge(bodyLarge),
    bodyMedium = defaults.bodyMedium.merge(bodyMedium),
    bodySmall = defaults.bodySmall.merge(bodySmall),
    labelLarge = defaults.labelLarge.merge(labelLarge),
    labelMedium = defaults.labelMedium.merge(labelMedium),
    labelSmall = defaults.labelSmall.merge(labelSmall),
  )
}

