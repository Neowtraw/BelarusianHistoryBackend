package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.*
import com.codingub.data.responses.TeacherGroupResponse
import com.codingub.data.responses.UserGroupResponse

interface GroupDataRepository{

    /*
        Teacher
     */
    suspend fun getAllGroupsFromTeacher(login: String): TeacherGroupResponse
    suspend fun createGroup(request: CreateGroupRequest) : Boolean
    suspend fun deleteGroup(request: GroupRequest) : Boolean
    suspend fun inviteUserToGroup(request: GroupRequest) : Boolean
    suspend fun deleteUserFromGroup(request: GroupRequest) : Boolean

    /*
        User
     */
    suspend fun getUserGroupInfo(login: String) : UserGroupResponse
}

class GroupDataRepositoryImpl(
    private val database: HistoryDatabase
) : GroupDataRepository{

    override suspend fun getAllGroupsFromTeacher(login: String): TeacherGroupResponse{
        return database.getAllGroupsFromTeacher(login)
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

    override suspend fun getUserGroupInfo(login: String): UserGroupResponse {
        return database.getUserGroupInfo(login)
    }
}