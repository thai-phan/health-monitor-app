package com.sewon.healthmonitor.data.source.model

import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import java.util.Date

data class Setting(
    val userId: Int,
    val alarmTime: String,
    val bedTime: String,
    val alarmOn: Boolean,
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

fun Setting.toLocal() = LocalSetting(
    userId = userId,
    alarmTime = alarmTime,
    bedTime = bedTime,
    alarmOn = alarmOn,
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
    alarmTime = alarmTime,
    bedTime = bedTime,
    alarmOn = alarmOn,
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
