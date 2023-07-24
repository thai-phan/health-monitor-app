package com.sewon.healthmonitor.data.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.dao.UserDao
import com.sewon.healthmonitor.data.entity.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope,
) {

    suspend fun addUser(user: User) {
        return userDao.upsert(user)
    }

    fun getCurrentUser(username: String): Flow<User> {
        return userDao.getCurrentUser(username)
    }

    suspend fun updateUserSetting(user: User): String {
        var aaa = userDao.updateUser(user)
        return "Done"
    }
}