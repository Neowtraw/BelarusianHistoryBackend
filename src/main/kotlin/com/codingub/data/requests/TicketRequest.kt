package com.codingub.data.requests

import com.codingub.data.models.achieves.Achieve

data class InsertTicketRequest(
    val name: String,
    val timer : Long,
    val achieve: Achieve? = null
)

data class DeleteTicketRequest(
    val name: String
)