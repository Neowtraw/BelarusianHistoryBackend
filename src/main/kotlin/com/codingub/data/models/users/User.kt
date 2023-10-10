package com.codingub.data.models.users

import com.codingub.data.models.achieves.Results
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    @BsonId val id: String = ObjectId().toString(),
    val login: String,
    val password: String,
    val username: String,
    val UId: String, //for groups
    val accessLevel: Int, //permissions

    val salt: String, //hashing

    val result: Results? = null //result of tickets/practice
)
