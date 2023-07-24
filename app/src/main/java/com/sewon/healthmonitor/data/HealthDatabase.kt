package com.sewon.healthmonitor.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sewon.healthmonitor.data.dao.RadarDao
import com.sewon.healthmonitor.data.dao.UserDao
import com.sewon.healthmonitor.data.dao.UserSettingDao
import com.sewon.healthmonitor.data.entity.Radar
import com.sewon.healthmonitor.data.entity.User
import com.sewon.healthmonitor.data.entity.UserSetting


@Database(
    entities = [
        User::class,
        Radar::class,
        UserSetting::class

    ], version = 1
)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun userInformationDao(): UserDao
    abstract fun userSettingDao(): UserSettingDao
    abstract fun topperDao(): RadarDao
}
