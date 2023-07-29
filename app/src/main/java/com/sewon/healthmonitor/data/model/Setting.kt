package com.sewon.healthmonitor.data.model

import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import java.util.Date

data class Setting(
    val userId: Int,
    val alarmTime: String,
    val bedTime: Float,
    val alarmOn: Boolean,
    val energyOn: String,
    val soundOn: String,
    val cacheOn: String,
    val initOn: String,
    val sosOn: String,
    val productSn: String,
    val threshold: String,
    val createdAt: Date,
    val updatedAt: Date,
)

fun Setting.toLocal() = LocalSetting(
    userId = userId,
    alarmTime = alarmTime,
    bedTime = bedTime,
    alarmOn = alarmOn,
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
    alarmTime = alarmTime,
    bedTime = bedTime,
    alarmOn = alarmOn,
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
