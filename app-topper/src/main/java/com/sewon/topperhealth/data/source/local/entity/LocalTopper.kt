package com.sewon.topperhealth.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime


@Entity(tableName = "topper")
data class LocalTopper(
  @ColumnInfo(name = "stable") val stable: Int,
  @ColumnInfo(name = "hr") val hr: Int,
  @ColumnInfo(name = "br") val br: Int,
  @ColumnInfo(name = "hrv") val hrv: Double,
  @ColumnInfo(name = "hr_wfm") val hrWfm: Double,
  @ColumnInfo(name = "br_wfm") val brWfm: Double,
  @ColumnInfo(name = "is_sleep") val isSleep: Boolean,
  @ColumnInfo(name = "session_id") val sessionId: Int,
  @ColumnInfo(name = "created_at") val createdAt: LocalTime,
) {
  @PrimaryKey(autoGenerate = true)
  var uid: Int = 0
}