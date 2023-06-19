package com.sewon.healthmonitor.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sewon.healthmonitor.dao.UserDao
import com.sewon.healthmonitor.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
