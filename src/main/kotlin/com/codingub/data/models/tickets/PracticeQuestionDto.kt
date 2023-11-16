package com.codingub.data.models.tickets

import com.codingub.sdk.TaskType
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class PracticeQuestionDto(
    @BsonId val id: String = ObjectId().toString(),
    val taskType: TaskType,
    val name: String,
    val info: String,
    val answers: List<Answer> = emptyList(),

    val tqId: String
)

//internal collection
@Serializable
data class Answer(
    val info: String,
    val isTrue: Boolean
)
