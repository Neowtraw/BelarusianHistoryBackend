package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.*
import com.codingub.data.responses.GroupResponse

interface GroupDataRepository{

    /*
        Teacher
     */
    suspend fun createGroup(request: CreateGroupRequest) : Boolean
    suspend fun deleteGroup(request: GroupRequest) : Boolean
    suspend fun inviteUserToGroup(request: GroupRequest) : Boolean
    suspend fun deleteUserFromGroup(request: GroupRequest) : Boolean

    /*
        User
     */
    suspend fun getAllGroups(login: String) : GroupResponse
}

class GroupDataRepositoryImpl(
    private val database: HistoryDatabase
) : GroupDataRepository{

    override suspend fun getAllGroups(login: String): GroupResponse{
        return database.getAllGroups(login)
    }

    override suspend fun createGroup(request: CreateGroupRequest): Boolean {
        return database.createGroup(request)
    }

    override suspend fun deleteGroup(request: GroupRequest): Boolean {
        return database.deleteGroup(request)
    }

    override suspend fun inviteUserToGroup(request: GroupRequest): Boolean {
        return database.inviteUserToGroup(request)
    }

    override suspend fun deleteUserFromGroup(request: GroupRequest): Boolean {
        return database.deleteUserFromGroup(request)
    }
}