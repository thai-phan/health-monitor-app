package com.sewon.healthmonitor.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//  Stable 3
//  HR 111
//  BR 11
//  HRV 0.5
//  HR-wfm -28.262
//  BR-wfm 358.991

@Entity(tableName = "radar")
data class LocalRadar(
  @ColumnInfo(name = "stable") val stable: Int,
  @ColumnInfo(name = "hr") val hr: Int,
  @ColumnInfo(name = "rb") val rb: Int,
  @ColumnInfo(name = "hrv") val hrv: Double,
  @ColumnInfo(name = "hr_wfm") val hrWfm: Double,
  @ColumnInfo(name = "br_wfm") val brWfm: Double,
) {
  @PrimaryKey(autoGenerate = true)
  var uid: Int = 0

}