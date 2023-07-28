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
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<LocalUser>>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<LocalUser>

    @Query("SELECT * FROM user WHERE birthday = :targetDate")
    fun findUsersBornOnDate(targetDate: Date): List<LocalUser>

    @Query("UPDATE user SET birthday = :birthday WHERE name = :username")
    suspend fun updateUserBirthday(username: String, birthday: Date)

    @Query("UPDATE user SET gender = :gender WHERE name = :username")
    suspend fun updateUserGender(username: String, gender: String)

    @Query("SELECT * FROM user where name = :name")
    fun getCurrentUser(name: String): Flow<LocalUser>

    @Update
    suspend fun updateUser(localUser: LocalUser)

    @Upsert
    suspend fun upsert(localUser: LocalUser)

    @Query("SELECT count(username) FROM user")
    fun countUser(): Flow<Int>


//    @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 1")
//    fun findByName(name: String): UserInformation
//
//    @Query("SELECT * FROM user WHERE region IN (:regions)")
//    fun loadUsersFromRegions(regions: List<String>): List<UserInformation>

//    @Query(
//        "SELECT * FROM user JOIN book ON user.id = book.user_id"
//    )
//    fun loadUserAndBookNames(): Map<User, List<Book>>


    @Insert
    fun insertAll(vararg localUsers: LocalUser)

    @Delete
    fun delete(localUser: LocalUser)

}