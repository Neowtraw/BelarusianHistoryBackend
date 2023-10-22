package com.codingub.data.requests

data class RegisterRequest(
    val login: String,
    val username: String,
    val password: String,
    val accessLevel: Int, //AccessLevel
)

data class LoginRequest(
    val login: String,
    val password: String
)
