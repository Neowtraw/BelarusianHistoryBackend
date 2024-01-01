package com.codingub.data.models.tickets

import com.codingub.data.models.achieves.AchieveDto
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class TicketQuestionDto(
    @BsonId val id : String = ObjectId().toString(),
    val name: String,
    val info: String,
    val ticketId: String,
    val achieve: AchieveDto? = null
)
