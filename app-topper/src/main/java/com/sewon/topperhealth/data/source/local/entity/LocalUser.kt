package com.sewon.topperhealth.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user")
data class LocalUser(
  @ColumnInfo(name = "username") val username: String,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "gender") var gender: String,
  @ColumnInfo(name = "birthday") val birthday: Date,
  @ColumnInfo(name = "signup_date") val signupDate: String,
  @ColumnInfo(name = "created_at") val createdAt: Date,
  @ColumnInfo(name = "updated_at") val updatedAt: Date,
) {
  @PrimaryKey(autoGenerate = true)
  var userId: Int = 0
}