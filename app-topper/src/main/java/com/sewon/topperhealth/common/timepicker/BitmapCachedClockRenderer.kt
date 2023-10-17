package com.sewon.topperhealth.common.timepicker

interface BitmapCachedClockRenderer : ClockRenderer {
  var isBitmapCacheEnabled: Boolean

  fun invalidateBitmapCache()
  fun recycleBitmapCache()
}