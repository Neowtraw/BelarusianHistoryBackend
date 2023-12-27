package com.codingub.data.models

import com.codingub.sdk.EventType

data class Event(
    val id: String,
    val title: String,
    val description: String,
    val type: EventType
)
