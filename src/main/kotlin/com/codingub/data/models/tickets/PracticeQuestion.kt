package com.codingub.data.models.tickets

import com.codingub.data.models.achieves.Achieve
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class PracticeQuestion(
    @BsonId val id: Int,
    val taskType: Int,
    val name: String,
    val info: String,

    val answers: List<Answer>,
    val achievements: List<Achieve>
)

//internal collection
data class Answer(
    val info: String,
    val isTrue: Boolean
)
