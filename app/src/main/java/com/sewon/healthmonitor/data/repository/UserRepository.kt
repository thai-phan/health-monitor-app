package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.model.User
import com.sewon.healthmonitor.data.model.toExternal
import com.sewon.healthmonitor.data.model.toLocal
import com.sewon.healthmonitor.data.source.local.dao.UserDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) {

    suspend fun addUser(user: User) {
        return userDao.upsert(user.toLocal())
    }

    fun getCurrentUser(username: String): Flow<User> {
        return userDao.getCurrentUser(username).map { it.toExternal() }
    }


    suspend fun updateUserBirthday(username: String, birthday: Date) {
        userDao.updateUserBirthday(username, birthday)
    }

    suspend fun updateUserGender(username: String, gender: String) {
        userDao.updateUserGender(username, gender)
    }

    fun countUser(): Flow<Int> {
        return userDao.countUser()
    }

    suspend fun updateUserSetting(user: User): String {
        var aaa = userDao.updateUser(user.toLocal())
        return "Done"
    }
}