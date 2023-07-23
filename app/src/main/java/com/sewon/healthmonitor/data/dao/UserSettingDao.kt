package com.sewon.healthmonitor.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.entity.UserSetting
import kotlinx.coroutines.flow.Flow


@Dao
interface UserSettingDao {

    @Query("SELECT * FROM setting")
    fun queryUserSetting(): Flow<List<UserSetting>>


    @Upsert
    suspend fun upsert(userSetting: UserSetting)

}