package com.codingub.data.requests

import com.codingub.data.models.achieves.Achieve
import com.codingub.data.models.tickets.TicketQuestion

data class InsertTicketRequest(
    val name: String,
    val timer : Long,
    val questions: List<TicketQuestion>? = null,
    val achieve: Achieve? = null
)
