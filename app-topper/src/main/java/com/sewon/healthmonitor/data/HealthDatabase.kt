package com.sewon.healthmonitor.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sewon.healthmonitor.data.source.local.dao.LocalRadarDao
import com.sewon.healthmonitor.data.source.local.dao.LocalUserDao
import com.sewon.healthmonitor.data.source.local.dao.LocalSettingDao
import com.sewon.healthmonitor.data.source.local.entity.converter.DateConverter
import com.sewon.healthmonitor.data.source.local.entity.LocalRadar
import com.sewon.healthmonitor.data.source.local.entity.LocalUser
import com.sewon.healthmonitor.data.source.local.entity.LocalSetting
import com.sewon.healthmonitor.data.source.local.entity.converter.TimeConverter


@Database(
  entities = [
    LocalUser::class,
    LocalRadar::class,
    LocalSetting::class

  ], version = 1
)
@TypeConverters(DateConverter::class, TimeConverter::class)
abstract class HealthDatabase : RoomDatabase() {
  abstract fun userInformationDao(): LocalUserDao
  abstract fun userSettingDao(): LocalSettingDao
  abstract fun radarDao(): LocalRadarDao
}
