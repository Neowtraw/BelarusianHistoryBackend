package com.codingub.data.models.users

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Group(
    @BsonId val id: ObjectId = ObjectId(),
    val name: String,
    val teacherId: User,
    val students: List<User>
)
