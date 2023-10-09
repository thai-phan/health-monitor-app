package com.sewon.healthmonitor.data.source.local.repository

import com.example.android.architecture.blueprints.todoapp.di.ApplicationScope
import com.example.android.architecture.blueprints.todoapp.di.DefaultDispatcher
import com.sewon.healthmonitor.data.model.User
import com.sewon.healthmonitor.data.model.toExternal
import com.sewon.healthmonitor.data.model.toLocal
import com.sewon.healthmonitor.data.irepository.IUserRepository
import com.sewon.healthmonitor.data.source.local.dao.LocalUserDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import java.util.Date
import javax.inject.Inject

class UserRepository @Inject constructor(
  private val localUserDao: LocalUserDao,
  @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
  @ApplicationScope private val scope: CoroutineScope,
) : IUserRepository {

  override suspend fun addUser(user: User) {
    return localUserDao.upsert(user.toLocal())
  }

  override suspend fun getUserByUsername(username: String): User {
    return localUserDao.getUserByUsername(username).toExternal()
  }

  override suspend fun updateUserBirthday(username: String, birthday: Date) {
    return localUserDao.updateUserBirthday(username, birthday)
  }

  override suspend fun updateUserGender(username: String, gender: String) {
    localUserDao.updateUserGender(username, gender)
  }

  override suspend fun countUser(): Int {
    return localUserDao.countUser()
  }

  override suspend fun updateUserSetting(user: User): String {
    var aaa = localUserDao.updateUser(user.toLocal())
    return "Done"
  }
}