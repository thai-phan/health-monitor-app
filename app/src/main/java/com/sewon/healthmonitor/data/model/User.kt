package com.sewon.healthmonitor.data.model

import com.sewon.healthmonitor.data.source.local.entity.LocalUser
import java.util.Date


data class User(
    val username: String,
    val name: String,
    var gender: String,
    val birthday: Date,
    val signupDate: String,
    val createdAt: String,
    val updatedAt: String,
)

// Model mapping
// extension function
fun User.toLocal() = LocalUser(
    username = username,
    name = name,
    gender = gender,
    birthday = birthday,
    signupDate = signupDate,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun List<User>.toLocal() = map(User::toLocal)

fun LocalUser.toExternal() = User(
    username = username,
    name = name,
    gender = gender,
    birthday = birthday,
    signupDate = signupDate,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun List<LocalUser>.toExternal() = map(LocalUser::toExternal)


