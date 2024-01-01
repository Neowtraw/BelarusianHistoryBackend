package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.models.users.UserDto
import com.codingub.data.requests.AddResultRequest
import com.codingub.data.requests.GetAllResultsRequest
import com.codingub.data.requests.GetResultsByTypeRequest
import com.codingub.data.requests.ResetResultsWithTypeRequest
import com.codingub.data.responses.ResultResponse
import com.codingub.sdk.AccessLevel
import com.codingub.sdk.AchieveType

interface UserDataRepository {
    /*
        Authentication
     */
    suspend fun getUserByLogin(login: String): UserDto?
    suspend fun insertUser(user: UserDto): Boolean

    /*
        User
     */

    suspend fun changeRoleByLogin(login: String, accessLevel: AccessLevel) : Boolean

    /*
        Results
     */
    suspend fun setAchieveCompletedByUser(request: AddResultRequest) : Boolean
    suspend fun getResultsByType(login: String, type: AchieveType) : ResultResponse
    suspend fun getAllResults(request: GetAllResultsRequest) : ResultResponse
    suspend fun resetResultsWithType(request: ResetResultsWithTypeRequest) : Boolean
}

class UserDataRepositoryImpl (
    private val database: HistoryDatabase
) : UserDataRepository{
    override suspend fun getUserByLogin(login: String): UserDto? {
        return database.getUserByLogin(login)
    }

    override suspend fun insertUser(user: UserDto): Boolean {
        return database.insertUser(user)
    }

    override suspend fun changeRoleByLogin(login: String, accessLevel: AccessLevel): Boolean {
        return database.changeRoleByLogin(login, accessLevel)
    }

    override suspend fun setAchieveCompletedByUser(request: AddResultRequest): Boolean {
        return database.setAchieveCompletedByUser(request)
    }

    override suspend fun getResultsByType(login: String, type: AchieveType): ResultResponse {
        return database.getResultsByType(login, type)
    }

    override suspend fun getAllResults(request: GetAllResultsRequest): ResultResponse {
        return database.getAllResults(request)
    }

    override suspend fun resetResultsWithType(request: ResetResultsWithTypeRequest): Boolean {
        return database.resetResultsWithType(request)
    }
}


