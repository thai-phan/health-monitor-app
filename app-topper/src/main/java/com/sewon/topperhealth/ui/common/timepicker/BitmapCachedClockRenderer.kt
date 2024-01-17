package com.sewon.topperhealth.ui.common.timepicker

interface BitmapCachedClockRenderer : ClockRenderer {
  var isBitmapCacheEnabled: Boolean

  fun invalidateBitmapCache()
  fun recycleBitmapCache()
}