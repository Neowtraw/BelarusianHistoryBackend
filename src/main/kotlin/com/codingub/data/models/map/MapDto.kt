package com.codingub.data.models.map

import com.codingub.data.responses.models.map.Map
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class MapDto(
    val id: String = UUID.randomUUID().toString(),
    val map: String, // uri
    val periodId: String
)