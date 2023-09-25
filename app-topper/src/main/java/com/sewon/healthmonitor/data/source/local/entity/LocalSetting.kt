package com.sewon.healthmonitor.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime
import java.util.Date


@Entity(tableName = "setting")
data class LocalSetting(
  @ColumnInfo(name = "user_id") val userId: Int,
  @ColumnInfo(name = "alarm_time") val alarmTime: LocalTime,
  @ColumnInfo(name = "bed_time") val bedTime: LocalTime,
  @ColumnInfo(name = "alarm_on") val alarmOn: Boolean,
  @ColumnInfo(name = "alarm_setting") val alarmSetting: String,
  @ColumnInfo(name = "bed_on") val bedOn: Boolean,
  @ColumnInfo(name = "energy_on") val energyOn: Boolean,
  @ColumnInfo(name = "sound_on") val soundOn: Boolean,
  @ColumnInfo(name = "cache_on") val cacheOn: Boolean,
  @ColumnInfo(name = "init_on") val initOn: Boolean,
  @ColumnInfo(name = "sos_on") val sosOn: Boolean,
  @ColumnInfo(name = "product_sn") val productSn: String,
  @ColumnInfo(name = "threshold") val threshold: String,
  @ColumnInfo(name = "created_at") val createdAt: Date,
  @ColumnInfo(name = "updated_at") val updatedAt: Date,

  ) {
  @ColumnInfo(name = "setting_id")
  @PrimaryKey(autoGenerate = true)
  var settingId: Int = 0
}