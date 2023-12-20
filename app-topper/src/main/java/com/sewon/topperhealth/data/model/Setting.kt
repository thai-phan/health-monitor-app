package com.sewon.topperhealth.data.model

import com.sewon.topperhealth.data.source.local.entity.LocalSetting
import java.time.LocalTime
import java.util.Date

data class Setting(
  val userId: Int = 0,
  val sleepTime: LocalTime,
  val wakeupTime: LocalTime,
  val alarmOn: Boolean = false,
  val alarmBehavior: String = "벨소리",
  val bedOn: Boolean = false,
  val energyOn: Boolean = false,
  val soundOn: Boolean = false,
  val cacheOn: Boolean = false,
  val initOn: Boolean = false,
  val sosOn: Boolean = false,
  val productSn: String = "aa",
  val threshold: String = "threshold",
  val createdAt: Date = Date(),
  val updatedAt: Date = Date(),

  var relation1: String = "",
  var relation2: String = "",
  var relation3: String = "",
  var contact1: String = "",
  var contact2: String = "",
  var contact3: String = "",
)


// Model mapping
// extension function
fun Setting.toLocal() = LocalSetting(
  userId = userId,
  sleepTime = sleepTime,
  wakeupTime = wakeupTime,
  alarmOn = alarmOn,
  alarmBehavior = alarmBehavior,
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

  relation1 = relation1,
  relation2 = relation2,
  relation3 = relation3,
  contact1 = contact1,
  contact2 = contact2,
  contact3 = contact3,
)


fun List<Setting>.toLocal() = map(Setting::toLocal)

fun LocalSetting.toExternal() = Setting(
  userId = userId,
  sleepTime = sleepTime,
  wakeupTime = wakeupTime,
  alarmOn = alarmOn,
  alarmBehavior = alarmBehavior,
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

  relation1 = relation1,
  relation2 = relation2,
  relation3 = relation3,
  contact1 = contact1,
  contact2 = contact2,
  contact3 = contact3,
)

fun List<LocalSetting>.toExternal() = map(LocalSetting::toExternal)
