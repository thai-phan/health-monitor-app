package com.sewon.healthmonitor.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.sewon.healthmonitor.data.dao.TopperDao
import com.sewon.healthmonitor.data.dao.UserDao
import com.sewon.healthmonitor.data.entity.Topper
import com.sewon.healthmonitor.data.entity.User


@Database(entities = [User::class, Topper::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun topperDao(): TopperDao


    companion object { @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context?): AppDatabase? {
            if (instance == null) {
                instance = databaseBuilder(
                    context!!,
                    AppDatabase::class.java,
                    "healthMonitor.db"
                )
//                .createFromAsset("database/myapp.db")
                    .build()
            }
            return instance
        }
    }
}
