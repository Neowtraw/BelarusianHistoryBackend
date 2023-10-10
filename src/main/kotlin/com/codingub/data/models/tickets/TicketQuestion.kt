package com.codingub.data.models.tickets

import com.codingub.data.models.achieves.Achieve
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class TicketQuestion(
    @BsonId val id : String = ObjectId().toString(),
    val name: String,
    val info: String,

    val practices: List<PracticeQuestion> = emptyList(),
    val achieve: Achieve? = null
)
