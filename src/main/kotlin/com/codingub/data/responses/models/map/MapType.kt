package com.codingub.data.responses.models.map

import com.codingub.sdk.MapCategory
import kotlinx.serialization.Serializable

@Serializable
data class MapType(
    val id: String,
    val title: String,
    val description: String,
    val type: MapCategory,
    val periods: List<MapPeriod>
)