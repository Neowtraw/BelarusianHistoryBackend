package com.codingub.data.requests

import com.codingub.data.models.achieves.Achieve

data class DeleteTqRequest (
    val ticketId: String,
    val questionId: String
)

data class GetTqRequest(
    val ticketId: String
)

data class InsertTqRequest(
    val name: String,
    val info: String,
    val achieve: Achieve? = null,

    val ticketId: String
    )