package com.sewon.healthmonitor.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.sewon.healthmonitor.data.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>
//

    @Query("SELECT * FROM user where name = :name")
    fun getCurrentUser(name: String): Flow<User>

    @Upsert
    suspend fun upsert(user: User)


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
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

}