package com.codingub.data.models.tickets

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class TicketQuestion(
    @BsonId val id: Int,
    val name: String,
    val info: String,
    var isPassed: Boolean,

    val practices: List<PracticeQuestion>
)
