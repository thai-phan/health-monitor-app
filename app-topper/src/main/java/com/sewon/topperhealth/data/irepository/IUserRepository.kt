package com.sewon.topperhealth.data.irepository

import com.sewon.topperhealth.data.model.User
import java.util.Date

interface IUserRepository {
  suspend fun addUser(user: User)

  suspend fun getUserByUsername(username: String): User?

  suspend fun updateUserBirthday(username: String, birthday: Date)

  suspend fun updateUserGender(username: String, gender: String)

  suspend fun countUser(): Int

  suspend fun updateUserSetting(user: User): String
}