package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.models.users.User
import com.codingub.sdk.AccessLevel

interface UserDataRepository {
    /*
        Authentication
     */
    suspend fun getUserByLogin(login: String): User?
    suspend fun insertUser(user: User): Boolean

    /*
        User
     */

    suspend fun changeRoleByLogin(login: String, accessLevel: AccessLevel) : Boolean
}

class UserDataRepositoryImpl constructor(
    private val database: HistoryDatabase
) : UserDataRepository{
    override suspend fun getUserByLogin(login: String): User? {
        return database.getUserByLogin(login)
    }

    override suspend fun insertUser(user: User): Boolean {
        return database.insertUser(user)
    }

    override suspend fun changeRoleByLogin(login: String, accessLevel: AccessLevel): Boolean {
        return database.changeRoleByLogin(login, accessLevel)
    }
}


