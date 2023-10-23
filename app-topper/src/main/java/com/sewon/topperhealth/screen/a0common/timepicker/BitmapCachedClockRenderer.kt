package com.sewon.topperhealth.screen.a0common.timepicker

interface BitmapCachedClockRenderer : ClockRenderer {
  var isBitmapCacheEnabled: Boolean

  fun invalidateBitmapCache()
  fun recycleBitmapCache()
}