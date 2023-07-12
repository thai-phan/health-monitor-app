package com.sewon.healthmonitor.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sewon.healthmonitor.data.dao.UserDao
import com.sewon.healthmonitor.data.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
