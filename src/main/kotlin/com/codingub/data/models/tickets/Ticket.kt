package com.codingub.data.models.tickets

import com.codingub.data.models.achieves.Achieve
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Ticket(
    @BsonId val id: ObjectId = ObjectId(),
    val name: String,
    val timer: Long, //for timer in ticket
    var isPassed: Boolean,

    var questions: List<TicketQuestion>,
    val achievement: Achieve
)
