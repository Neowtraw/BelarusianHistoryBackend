package com.codingub.data.requests

import com.codingub.data.models.achieves.Achieve
import kotlinx.serialization.Serializable

data class DeleteTqRequest (
    val questionId: String
)
data class InsertTqRequest(
    val name: String,
    val info: String,
    val achieve: Achieve? = null,
    val ticketId: String
    )