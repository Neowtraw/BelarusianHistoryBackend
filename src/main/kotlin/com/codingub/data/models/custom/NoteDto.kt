package com.codingub.data.models.custom

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class NoteDto(
    @BsonId val id : String = ObjectId().toString(),
    val info: String,
    val ticketId: String
)
