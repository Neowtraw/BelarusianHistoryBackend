package com.codingub.data.responses.models

import com.codingub.data.models.achieves.Achieve

data class TicketQuestion(
    val id: String,
    val name: String,
    val info: String,
    val practices: List<PracticeQuestion>,
    val achieve: Achieve?
    )
