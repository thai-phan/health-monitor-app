package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.dao.UserDao
import com.sewon.healthmonitor.data.entity.LocalUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) {

    suspend fun addUser(localUser: LocalUser) {
        return userDao.upsert(localUser)
    }

    fun getCurrentUser(username: String): Flow<LocalUser> {
        return userDao.getCurrentUser(username)
    }


    suspend fun updateUserBirthday(username: String, birthday: Date) {
        userDao.updateUserBirthday(username, birthday)
    }

    suspend fun updateUserGender(username: String, gender: String) {
        userDao.updateUserGender(username, gender)
    }

    suspend fun updateUserSetting(localUser: LocalUser): String {
        var aaa = userDao.updateUser(localUser)
        return "Done"
    }
}