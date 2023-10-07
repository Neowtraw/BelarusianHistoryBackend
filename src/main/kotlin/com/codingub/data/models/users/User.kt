package com.codingub.data.models.users

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    @BsonId val id: ObjectId = ObjectId(),
    val login: String,
    val password: String,
    val username: String,
    val UId: String, //for groups
    val accessLevel: Int, //permissions

    val salt: String //hashing
)
