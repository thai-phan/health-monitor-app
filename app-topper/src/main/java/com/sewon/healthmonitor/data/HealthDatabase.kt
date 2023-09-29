package com.sewon.healthmonitor.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sewon.healthmonitor.data.source.local.dao.LocalSensorDao
import com.sewon.healthmonitor.data.source.local.dao.LocalSessionDao
import com.sewon.healthmonitor.data.source.local.dao.LocalUserDao
import com.sewon.healthmonitor.data.source.local.dao.LocalSettingDao
import com.sewon.healthmonitor.data.source.local.converter.DateConverter
import com.sewon.healthmonitor.data.source.local.entity.LocalSensor
import com.sewon.healthmonitor.data.source.local.entity.LocalSession
import com.sewon.healthmonitor.data.source.local.entity.LocalUser
import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import com.sewon.healthmonitor.data.source.local.converter.TimeConverter


@Database(
  entities = [
    LocalUser::class,
    LocalSensor::class,
    LocalSetting::class,
    LocalSession::class

  ], version = 1
)
@TypeConverters(
  DateConverter::class,
  TimeConverter::class
)
abstract class HealthDatabase : RoomDatabase() {
  abstract fun userDao(): LocalUserDao
  abstract fun settingDao(): LocalSettingDao
  abstract fun sensorDao(): LocalSensorDao
  abstract fun sessionDao(): LocalSessionDao

}
