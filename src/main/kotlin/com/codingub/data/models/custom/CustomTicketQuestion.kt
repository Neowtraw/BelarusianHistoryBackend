package com.codingub.data.models.custom

import com.codingub.data.models.tickets.Answer
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class CustomTicketQuestion(
    @BsonId val id: Int,
    val name: String,
    val info: String,

    var practices: List<CustomPracticeQuestion>? = null
)

data class CustomPracticeQuestion(
    val name : String,
    val taskType: Int, //TaskType
    val answers: List<Answer>
)


