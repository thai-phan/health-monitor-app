package com.sewon.topperhealth.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sewon.topperhealth.data.source.local.entity.LocalUser
import java.util.Date

@Dao
interface LocalUserDao {
  @Query("SELECT * FROM user")
  suspend fun getAll(): List<LocalUser>

  @Query("SELECT * FROM user WHERE userId = :userId")
  suspend fun loadAllByIds(userId: Int): LocalUser

  @Query("SELECT * FROM user WHERE birthday = :targetDate")
  suspend fun findUsersBornOnDate(targetDate: Date): List<LocalUser>

  @Query("UPDATE user SET birthday = :birthday WHERE username = :username")
  suspend fun updateUserBirthday(username: String, birthday: Date)

  @Query("UPDATE user SET gender = :gender WHERE username = :username")
  suspend fun updateUserGender(username: String, gender: String)

  @Query("SELECT * FROM user where username = :username")
  suspend fun getUserByUsername(username: String): LocalUser?

  @Update
  suspend fun updateUser(localUser: LocalUser)

  @Upsert
  suspend fun upsert(localUser: LocalUser)

  @Query("SELECT count(username) FROM user")
  suspend fun countUser(): Int

  @Insert
  fun insertAll(vararg localUsers: LocalUser)

  @Delete
  fun delete(localUser: LocalUser)

}