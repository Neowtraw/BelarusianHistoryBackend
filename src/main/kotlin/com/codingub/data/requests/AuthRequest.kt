package com.codingub.data.requests

data class AuthRequest(
    val login: String,
    val username: String,
    val password: String,
    val accessLevel: Int, //AccessLevel
)
