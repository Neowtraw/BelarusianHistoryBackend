package com.codingub.data.requests

import com.codingub.data.models.tickets.Answer
import com.codingub.sdk.TaskType
import kotlinx.serialization.Serializable

data class DeletePqRequest (
    val questionId: String
)

@Serializable
data class InsertPqRequest(
    val taskType: TaskType,
    val name: String,
    val info: String,
    val answers: List<Answer>,
    val tqId: String
)