package com.sewon.healthmonitor.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "setting")
data class UserSetting(
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "birthday") val birthday: String,
//    @ColumnInfo(name = "rri") val rri: Float,
//    @ColumnInfo(name = "moving") val moving: String,
//    @ColumnInfo(name = "detect") val detect: String,
//    @ColumnInfo(name = "no_one") val noOne: String,
//    @ColumnInfo(name = "stable") val stable: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0


}