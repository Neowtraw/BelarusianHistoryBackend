package com.codingub.data.responses.models.map

import com.codingub.data.models.map.MapLabelDto
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class MapLabel(
    val id: String = UUID.randomUUID().toString(),
    val x: Float,
    val y: Float,
    val title: String,
    val description: String,
    val animation: String? = null,
    val image: String? = null,
    val creatorId: String,
    val mapId: String
) {
    fun toMapLabelDto() =  MapLabelDto(
        id = id,
        x = x,
        y = y,
        title = title,
        description = description,
        animation = animation,
        image = image,
        creatorId = creatorId,
        mapId = mapId
    )
}