package com.codingub.data.models.users

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Group(
    @BsonId val id: String = ObjectId().toString(),
    val name: String,
    val teacher: String, //login
    val users: List<String> = emptyList() //login
)
