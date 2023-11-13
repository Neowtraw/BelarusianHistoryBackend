package com.codingub.data.responses

import com.codingub.sdk.AccessLevel
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String,
    val login: String,
    val username: String,
    val accessLevel: AccessLevel
)
