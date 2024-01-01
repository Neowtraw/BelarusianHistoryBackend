package com.codingub.data.models.users

import com.codingub.sdk.AccessLevel
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class UserDto(
    @BsonId val id: String = ObjectId().toString(),
    val login: String,
    val password: String,
    val username: String,
    val accessLevel: AccessLevel,

    val salt: String, //hashing
)
