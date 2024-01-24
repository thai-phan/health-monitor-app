package com.sewon.topperhealth.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime
import java.util.Date


@Entity(tableName = "setting")
data class LocalSetting(
  @ColumnInfo(name = "user_id") val userId: Int,
  @ColumnInfo(name = "wakeup_time") val wakeupTime: LocalTime,
  @ColumnInfo(name = "sleep_time") val sleepTime: LocalTime,
  @ColumnInfo(name = "alarm_on") val alarmOn: Boolean,
  @ColumnInfo(name = "alarm_behavior") val alarmBehavior: String,
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

  @ColumnInfo(name = "relation1") val relation1: String,
  @ColumnInfo(name = "contact1") val contact1: String,
  @ColumnInfo(name = "relation2") val relation2: String,
  @ColumnInfo(name = "contact2") val contact2: String,
  @ColumnInfo(name = "relation3") val relation3: String,
  @ColumnInfo(name = "contact3") val contact3: String,
) {
  @ColumnInfo(name = "setting_id")
  @PrimaryKey(autoGenerate = true)
  var settingId: Int = 0
}