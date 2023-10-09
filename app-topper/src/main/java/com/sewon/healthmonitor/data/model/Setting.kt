package com.sewon.healthmonitor.data.model

import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import java.time.LocalTime
import java.util.Date

data class Setting(
  val userId: Int,
  val bedTime: LocalTime,
  val alarmTime: LocalTime,
  val alarmOn: Boolean,
  val alarmSetting: String,
  val bedOn: Boolean,
  val energyOn: Boolean,
  val soundOn: Boolean,
  val cacheOn: Boolean,
  val initOn: Boolean,
  val sosOn: Boolean,
  val productSn: String,
  val threshold: String,
  val createdAt: Date,
  val updatedAt: Date,
)


// Model mapping
// extension function
fun Setting.toLocal() = LocalSetting(
  userId = userId,
  bedTime = bedTime,
  alarmTime = alarmTime,
  alarmOn = alarmOn,
  alarmSetting = alarmSetting,
  bedOn = bedOn,
  energyOn = energyOn,
  soundOn = soundOn,
  cacheOn = cacheOn,
  initOn = initOn,
  sosOn = sosOn,
  productSn = productSn,
  threshold = threshold,
  createdAt = createdAt,
  updatedAt = updatedAt,
)


fun List<Setting>.toLocal() = map(Setting::toLocal)

fun LocalSetting.toExternal() = Setting(
  userId = userId,
  bedTime = bedTime,
  alarmTime = alarmTime,
  alarmOn = alarmOn,
  alarmSetting = alarmSetting,
  bedOn = bedOn,
  energyOn = energyOn,
  soundOn = soundOn,
  cacheOn = cacheOn,
  initOn = initOn,
  sosOn = sosOn,
  productSn = productSn,
  threshold = threshold,
  createdAt = createdAt,
  updatedAt = updatedAt,
)

fun List<LocalSetting>.toExternal() = map(LocalSetting::toExternal)
