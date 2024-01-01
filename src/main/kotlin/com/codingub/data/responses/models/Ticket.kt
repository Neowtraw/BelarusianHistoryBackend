package com.codingub.data.responses.models

import com.codingub.data.models.achieves.AchieveDto

data class Ticket(
    val id: String,
    val name: String,
    val timer: Long,
    val tqs: List<TicketQuestion>,
    val achieve: AchieveDto?
)
