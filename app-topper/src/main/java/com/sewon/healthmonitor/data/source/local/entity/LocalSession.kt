package com.sewon.healthmonitor.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime


//INSERT INTO session (`created_at`,`finished_at`,`session_id`) VALUES ("20:21:46.571240","20:21:46.571240", 1)
@Entity(tableName = "session")
data class LocalSession(
  // TODO: Implement
  @ColumnInfo(name = "ref_hrv") val refHRV: Double,
  // TODO: Implement
  @ColumnInfo(name = "ref_hr") val refHR: Double,
  // TODO: Implement
  @ColumnInfo(name = "ref_br") val refBR: Double,

  // TODO: Implement
  @ColumnInfo(name = "mean_hr") val meanHR: Double,
  // TODO: Implement
  @ColumnInfo(name = "mean_br") val meanBR: Double,
  // TODO: Implement
  @ColumnInfo(name = "sdrp") val SDRP: Double,
  // TODO: Implement
  @ColumnInfo(name = "rmssd") val RMSSD: Double,
  // TODO: Implement
  @ColumnInfo(name = "rpi_triangular") val RPITriangular: Double,
  // TODO: Implement
  @ColumnInfo(name = "low_freq") val lowFreq: Double,
  // TODO: Implement
  @ColumnInfo(name = "high_freq") val highFreq: Double,
  // TODO: Implement
  @ColumnInfo(name = "lf_hf_ratio") val LfHfRatio: Double,

  // TODO: Implement
  @ColumnInfo(name = "rating") val rating: Int,
  // TODO
  @ColumnInfo(name = "wake_up_count") val wakeUpCount: Int,
  // TODO: Implement
  @ColumnInfo(name = "start_time") val startTime: LocalTime,
  // TODO: Implement
  @ColumnInfo(name = "sleep_time") val sleepTime: LocalTime,
  // TODO: Implement
  @ColumnInfo(name = "end_time") val endTime: LocalTime,
) {
  @ColumnInfo(name = "session_id")
  @PrimaryKey(autoGenerate = true)
  var sessionId: Int = 0
}