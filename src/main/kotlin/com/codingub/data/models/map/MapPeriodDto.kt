package com.codingub.data.models.map

import com.codingub.data.responses.models.map.MapPeriod
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class MapPeriodDto(
    val id: String = UUID.randomUUID().toString(),
    val period: Int, // can be -
    val mapTypeId: String
) {

    fun toMapPeriod() = MapPeriod(
        id = id,
        period = period,
        mapTypeId = mapTypeId
    )
}