package com.sewon.healthmonitor.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sewon.healthmonitor.data.dao.RadarDao
import com.sewon.healthmonitor.data.dao.UserDao
import com.sewon.healthmonitor.data.dao.UserSettingDao
import com.sewon.healthmonitor.data.entity.DateConverter
import com.sewon.healthmonitor.data.entity.Radar
import com.sewon.healthmonitor.data.entity.LocalUser
import com.sewon.healthmonitor.data.entity.UserSetting


@Database(
    entities = [
        LocalUser::class,
        Radar::class,
        UserSetting::class

    ], version = 1
)
@TypeConverters(DateConverter::class)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun userInformationDao(): UserDao
    abstract fun userSettingDao(): UserSettingDao
    abstract fun topperDao(): RadarDao
}
