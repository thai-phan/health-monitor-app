package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.dao.UserSettingDao
import com.sewon.healthmonitor.data.entity.UserSetting
import com.sewon.healthmonitor.data.repository.repointerface.IUserSettingRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserSettingRepository @Inject constructor(
    private val userSettingDao: UserSettingDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
): IUserSettingRepository {

    override fun loadUserSetting(): Flow<List<UserSetting>> {
        return userSettingDao.queryUserSetting()
    }


    override suspend fun updateUserSetting(userSetting: UserSetting): String {
        var aaa = this.userSettingDao.upsert(userSetting)
        return "Done"
    }


}