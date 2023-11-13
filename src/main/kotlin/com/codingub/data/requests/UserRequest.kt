package com.codingub.data.requests

import com.codingub.sdk.AccessLevel

data class RoleRequest(
    val login: String,
    val accessLevel: AccessLevel
)