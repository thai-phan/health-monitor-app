package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.entity.UserSetting
import kotlinx.coroutines.flow.Flow


interface IUserSettingRepository {

    fun loadUserSetting(): Flow<List<UserSetting>>

    suspend fun updateUserSetting(userSetting: UserSetting): String
}