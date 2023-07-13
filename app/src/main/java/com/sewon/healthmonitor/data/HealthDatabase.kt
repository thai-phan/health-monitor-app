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
abstract class HealthDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun topperDao(): TopperDao


    companion object { @Volatile
        private var instance: HealthDatabase? = null

        fun getInstance(context: Context?): HealthDatabase? {
            if (instance == null) {
                instance = databaseBuilder(
                    context!!,
                    HealthDatabase::class.java,
                    "healthMonitor.db"
                )
//                .createFromAsset("database/myapp.db")
                    .build()
            }
            return instance
        }
    }
}
