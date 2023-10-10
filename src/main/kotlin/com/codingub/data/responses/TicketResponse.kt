package com.codingub.data.responses

import com.codingub.data.models.tickets.Ticket

data class TicketResponse(
    val ticketList: List<Ticket>
)
