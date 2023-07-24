package com.sewon.healthmonitor.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "radar")
data class Radar(
    @ColumnInfo(name = "rb") val rb: Float,
    @ColumnInfo(name = "hr") val hr: Float,
    @ColumnInfo(name = "rri") val rri: Float,
    @ColumnInfo(name = "moving") val moving: String,
    @ColumnInfo(name = "detect") val detect: String,
    @ColumnInfo(name = "no_one") val noOne: String,
    @ColumnInfo(name = "stable") val stable: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
//    constructor(
//        rb: Float,
//        hr: Float,
//        rri: Float,
//        moving: String,
//        detect: String,
//        noOne: String,
//        stable: String
//    ) : this(
//        rb,
//        hr,
//        rri,
//        moving,
//        detect,
//        noOne,
//        stable
//    )

}