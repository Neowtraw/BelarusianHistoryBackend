package com.codingub.data.requests

import com.codingub.data.models.achieves.AchieveDto

data class InsertTicketRequest(
    val name: String,
    val timer : Long,
    val achieve: AchieveDto? = null
)

data class DeleteTicketRequest(
    val name: String
)