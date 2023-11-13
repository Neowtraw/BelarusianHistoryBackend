package com.codingub.data.requests


data class GroupRequest(
    val login: String,
    val groupId: String
)


data class CreateGroupRequest(
    val teacher: String, //login
    val groupName: String
)

