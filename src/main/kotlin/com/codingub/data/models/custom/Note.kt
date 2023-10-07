package com.codingub.data.models.custom

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Note(
    @BsonId val id: ObjectId = ObjectId(),
    val info: String,
    val ticketId: ObjectId
)
