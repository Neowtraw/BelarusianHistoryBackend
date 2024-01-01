package com.codingub.data.models.events

import com.codingub.sdk.EventType
import org.bson.types.ObjectId

data class EventDto(
    val id: String = ObjectId().toString(),
    val title: String,
    val description: String,
    val type: EventType
)
