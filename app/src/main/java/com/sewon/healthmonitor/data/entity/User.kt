package com.sewon.healthmonitor.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "birthday") val birthday: String,
    @ColumnInfo(name = "signup_date") val signupDate: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}