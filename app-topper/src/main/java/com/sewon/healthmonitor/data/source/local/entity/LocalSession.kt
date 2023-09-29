package com.sewon.healthmonitor.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime


//INSERT INTO session (`created_at`,`finished_at`,`session_id`) VALUES ("20:21:46.571240","20:21:46.571240", 1)
@Entity(tableName = "session")
data class LocalSession(
  @ColumnInfo(name = "mean_hrv") val meanHRV: Double,
  @ColumnInfo(name = "mean_hr") val meanHR: Double,
  @ColumnInfo(name = "mean_br") val meanBR: Double,
  @ColumnInfo(name = "rating") val rating: Int,
  @ColumnInfo(name = "start_time") val startTime: LocalTime,
  @ColumnInfo(name = "sleep_time") val sleepTime: LocalTime,
  @ColumnInfo(name = "wake_up_count") val wakeUpCount: Int,
  @ColumnInfo(name = "end_time") val endTime: LocalTime,
) {
  @ColumnInfo(name = "session_id")
  @PrimaryKey(autoGenerate = true)
  var sessionId: Int = 0
}