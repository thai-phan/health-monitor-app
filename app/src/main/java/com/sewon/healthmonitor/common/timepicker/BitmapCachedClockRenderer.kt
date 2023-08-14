package com.sewon.healthmonitor.common.timepicker

interface BitmapCachedClockRenderer: ClockRenderer {
    var isBitmapCacheEnabled: Boolean

    fun invalidateBitmapCache()
    fun recycleBitmapCache()
}