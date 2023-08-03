package com.sewon.healthmonitor.data.source.local.entity.converter

import androidx.room.TypeConverter
import java.time.LocalTime

class TimeConverter {

    @TypeConverter
    fun fromTimestamp(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it) }
    }

    @TypeConverter
    fun timeToTimestamp(date: LocalTime?): String? {
        return date?.toString()
    }
}