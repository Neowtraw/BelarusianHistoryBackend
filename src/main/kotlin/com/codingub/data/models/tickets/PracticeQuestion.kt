package com.codingub.data.models.tickets

import com.codingub.data.models.achieves.Achieve
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class PracticeQuestion(
    @BsonId val id: String = ObjectId().toString(),
    val taskType: Int,
    val name: String,
    val info: String,

    val answers: List<Answer> = emptyList()
)

//internal collection
data class Answer(
    val info: String,
    val isTrue: Boolean
)
