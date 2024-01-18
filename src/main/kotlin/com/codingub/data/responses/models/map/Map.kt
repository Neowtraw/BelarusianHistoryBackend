package com.codingub.data.responses.models.map

import com.codingub.data.models.map.MapLabelDto
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Map(
    val id: String = UUID.randomUUID().toString(),
    val map: String,
    val labels: List<MapLabel>,
    val periodId: String
)