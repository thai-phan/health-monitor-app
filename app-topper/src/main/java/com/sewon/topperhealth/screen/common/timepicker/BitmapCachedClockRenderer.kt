package com.sewon.topperhealth.screen.common.timepicker

interface BitmapCachedClockRenderer : ClockRenderer {
  var isBitmapCacheEnabled: Boolean

  fun invalidateBitmapCache()
  fun recycleBitmapCache()
}