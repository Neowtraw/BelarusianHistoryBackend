package com.codingub.data.responses.models

import com.codingub.data.models.tickets.Answer
import com.codingub.sdk.TaskType

data class PracticeQuestion(
    val id: String,
    val taskType: TaskType,
    val name: String,
    val info: String,
    val answers: List<Answer>
)