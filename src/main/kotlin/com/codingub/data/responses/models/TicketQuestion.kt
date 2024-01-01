package com.codingub.data.responses.models

import com.codingub.data.models.achieves.AchieveDto

data class TicketQuestion(
    val id: String,
    val name: String,
    val info: String,
    val practices: List<PracticeQuestion>,
    val achieve: AchieveDto?
    )
