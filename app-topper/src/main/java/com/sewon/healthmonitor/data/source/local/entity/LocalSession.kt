package com.sewon.healthmonitor.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime


//INSERT INTO session (`created_at`,`finished_at`,`session_id`) VALUES ("20:21:46.571240","20:21:46.571240", 1)
@Entity(tableName = "session")
data class LocalSession(
  @ColumnInfo(name = "created_at") val createdAt: LocalTime,
  @ColumnInfo(name = "finished_at") val finishedAt: LocalTime,
) {
  @ColumnInfo(name = "session_id")
  @PrimaryKey(autoGenerate = true)
  var sessionId: Int = 0
}