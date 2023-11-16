package com.codingub.data.models.tickets

import com.codingub.data.models.achieves.Achieve
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class TicketDto(
    @BsonId val id: String = ObjectId().toString(),
    val name: String,
    val timer: Long, //for timer in ticket
    val achievement: Achieve? = null
)