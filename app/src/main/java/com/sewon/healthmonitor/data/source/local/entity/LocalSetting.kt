package com.sewon.healthmonitor.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "setting")
data class LocalSetting(
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "alarm_time") val alarmTime: String,
    @ColumnInfo(name = "bed_time") val bedTime: Float,
    @ColumnInfo(name = "alarm_on") val alarmOn: Boolean,
    @ColumnInfo(name = "energy_on") val energyOn: String,
    @ColumnInfo(name = "sound_on") val soundOn: String,
    @ColumnInfo(name = "cache_on") val cacheOn: String,
    @ColumnInfo(name = "init_on") val initOn: String,
    @ColumnInfo(name = "sos_on") val sosOn: String,
    @ColumnInfo(name = "product_sn") val productSn: String,
    @ColumnInfo(name = "threshold") val threshold: String,
    @ColumnInfo(name = "created_at") val createdAt: Date,
    @ColumnInfo(name = "updated_at") val updatedAt: Date,

    ) {
    @PrimaryKey(autoGenerate = true)
    var setting_id: Int = 0


}