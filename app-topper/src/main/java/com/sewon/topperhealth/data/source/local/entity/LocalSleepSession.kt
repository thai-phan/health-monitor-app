package com.sewon.topperhealth.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "sleep_session")
data class LocalSleepSession(
  @ColumnInfo(name = "ref_hrv") val refHRV: Double,
  @ColumnInfo(name = "ref_hr") val refHR: Double,
  @ColumnInfo(name = "ref_br") val refBR: Double,
  @ColumnInfo(name = "is_generated_report") val isGeneratedReport: Boolean,

  @ColumnInfo(name = "mean_hr") val meanHR: Double,
  @ColumnInfo(name = "mean_br") val meanBR: Double,
  @ColumnInfo(name = "sdrp") val SDRP: Double,
  @ColumnInfo(name = "rmssd") val RMSSD: Double,
  @ColumnInfo(name = "rpi_triangular") val RPITriangular: Double,
  @ColumnInfo(name = "low_freq") val lowFreq: Double,
  @ColumnInfo(name = "high_freq") val highFreq: Double,
  @ColumnInfo(name = "lf_hf_ratio") val lfHfRatio: Double,

  @ColumnInfo(name = "rating") val rating: Int,
  @ColumnInfo(name = "wake_up_count") val wakeUpCount: Int,

  @ColumnInfo(name = "picker_start_time") val pickerStartTime: Date,
  @ColumnInfo(name = "picker_end_time") val pickerEndTime: Date,
  @ColumnInfo(name = "actual_start_time") val actualStartTime: Date,
  @ColumnInfo(name = "actual_end_time") val actualEndTime: Date,
  @ColumnInfo(name = "fell_asleep_time") val fellAsleepTime: Date,

  @ColumnInfo(name = "assessment") val assessment: String,
  @ColumnInfo(name = "memo") val memo: String,

  ) {
  @ColumnInfo(name = "session_id")
  @PrimaryKey(autoGenerate = true)
  var sessionId: Int = 0
}