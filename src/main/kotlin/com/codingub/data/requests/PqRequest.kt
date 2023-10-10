package com.codingub.data.requests

import com.codingub.data.models.tickets.Answer
import kotlinx.serialization.Serializable

data class DeletePqRequest (
    val tqId: String,
    val questionId: String
)

data class GetPqRequest(
    val tqId: String
)

@Serializable
data class InsertPqRequest(
    val taskType: Int,
    val name: String,
    val info: String,
    val answers: List<Answer>,

    val tqId: String
)