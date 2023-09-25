package com.sewon.healthmonitor.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user")
data class LocalSession(
  @ColumnInfo(name = "created_at") val createdAt: Date,
  @ColumnInfo(name = "finished_at") val finishedAt: Date,
) {
  @PrimaryKey(autoGenerate = true)
  var sessionId: Int = 0
}