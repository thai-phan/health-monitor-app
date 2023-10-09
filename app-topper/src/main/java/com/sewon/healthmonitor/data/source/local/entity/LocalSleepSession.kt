package com.sewon.healthmonitor.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


//INSERT INTO session (`created_at`,`finished_at`,`session_id`) VALUES ("20:21:46.571240","20:21:46.571240", 1)
@Entity(tableName = "sleep_session")
data class LocalSleepSession(
  @ColumnInfo(name = "ref_hrv") val refHRV: Double,
  @ColumnInfo(name = "ref_hr") val refHR: Double,
  @ColumnInfo(name = "ref_br") val refBR: Double,
  @ColumnInfo(name = "is_generated_report") val isGeneratedReport: Boolean,

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
  @ColumnInfo(name = "lf_hf_ratio") val lfHfRatio: Double,

  // TODO: Implement
  @ColumnInfo(name = "rating") val rating: Int,
  // TODO: Implement
  @ColumnInfo(name = "wake_up_count") val wakeUpCount: Int,

  @ColumnInfo(name = "picker_start_time") val pickerStartTime: Date,
  @ColumnInfo(name = "picker_end_time") val pickerEndTime: Date,
  @ColumnInfo(name = "actual_start_time") val actualStartTime: Date,
  @ColumnInfo(name = "actual_end_time") val actualEndTime: Date,
  @ColumnInfo(name = "sleep_time") val sleepTime: Date,

  @ColumnInfo(name = "assessment") val assessment: String,
  @ColumnInfo(name = "memo") val memo: String,

  ) {
  @ColumnInfo(name = "session_id")
  @PrimaryKey(autoGenerate = true)
  var sessionId: Int = 0
}