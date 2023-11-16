package com.codingub.data.responses.models

import com.codingub.data.models.achieves.Achieve

data class Ticket(
    val id: String,
    val name: String,
    val timer: Long,
    val tqs: List<TicketQuestion>,
    val achieve: Achieve?
)
