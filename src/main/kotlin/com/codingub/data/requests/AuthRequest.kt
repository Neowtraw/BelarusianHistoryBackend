package com.codingub.data.requests

import com.codingub.sdk.AccessLevel

data class RegisterRequest(
    val login: String,
    val username: String,
    val password: String,
    val accessLevel: AccessLevel
)

data class LoginRequest(
    val login: String,
    val password: String
)
