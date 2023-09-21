package com.sewon.healthmonitor.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.sewon.healthmonitor.data.source.local.entity.LocalUser
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface LocalUserDao {
  @Query("SELECT * FROM user")
  fun getAll(): Flow<List<LocalUser>>

  @Query("SELECT * FROM user WHERE userId = :userId")
  fun loadAllByIds(userId: Int): LocalUser

  @Query("SELECT * FROM user WHERE birthday = :targetDate")
  fun findUsersBornOnDate(targetDate: Date): List<LocalUser>

  @Query("UPDATE user SET birthday = :birthday WHERE username = :username")
  suspend fun updateUserBirthday(username: String, birthday: Date)

  @Query("UPDATE user SET gender = :gender WHERE username = :username")
  suspend fun updateUserGender(username: String, gender: String)

  @Query("SELECT * FROM user where username = :username")
  fun getUserByUsername(username: String): Flow<LocalUser>

  @Update
  suspend fun updateUser(localUser: LocalUser)

  @Upsert
  suspend fun upsert(localUser: LocalUser)

  @Query("SELECT count(username) FROM user")
  fun countUser(): Flow<Int>

  @Insert
  fun insertAll(vararg localUsers: LocalUser)

  @Delete
  fun delete(localUser: LocalUser)

}