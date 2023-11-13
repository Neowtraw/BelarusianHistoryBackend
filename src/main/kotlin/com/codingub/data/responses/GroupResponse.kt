package com.codingub.data.responses

import com.codingub.data.models.users.Group

data class TeacherGroupResponse(
    val groups: List<Group>
)

data class UserGroupResponse(
    val group: Group?
)