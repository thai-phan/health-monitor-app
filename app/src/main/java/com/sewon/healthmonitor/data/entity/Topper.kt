package com.sewon.healthmonitor.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "topper")
data class Topper(
    @PrimaryKey(autoGenerate = true)
    var uid: Int,

    @ColumnInfo(name = "rb")
    val rb: Float,

    @ColumnInfo(name = "hr")
    val hr: Float,

    @ColumnInfo(name = "rri")
    val rri: Float,

    @ColumnInfo(name = "moving")
    val moving: String,

    @ColumnInfo(name = "detect")
    val detect: String,

    @ColumnInfo(name = "no_one")
    val noOne: String,

    @ColumnInfo(name = "stable")
    val stable: String,
) {


}