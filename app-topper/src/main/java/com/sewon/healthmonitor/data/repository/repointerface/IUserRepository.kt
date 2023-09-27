package com.sewon.healthmonitor.data.repository.repointerface

import com.sewon.healthmonitor.data.model.User
import com.sewon.healthmonitor.data.model.toExternal
import com.sewon.healthmonitor.data.model.toLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date

interface IUserRepository {
  suspend fun addUser(user: User)

  fun getUserByUsername(username: String): Flow<User>

  suspend fun updateUserBirthday(username: String, birthday: Date)

  suspend fun updateUserGender(username: String, gender: String)

  suspend fun countUser(): Int

  suspend fun updateUserSetting(user: User): String
}