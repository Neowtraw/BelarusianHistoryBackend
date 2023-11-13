package com.codingub.sdk

import kotlinx.serialization.Serializable

@Serializable
enum class AccessLevel{
    User,
    Teacher,
    Admin
}