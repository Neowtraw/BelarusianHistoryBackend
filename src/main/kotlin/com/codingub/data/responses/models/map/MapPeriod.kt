package com.codingub.data.responses.models.map

import com.codingub.data.responses.models.map.Map
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class MapPeriod(
    val id: String = UUID.randomUUID().toString(),
    val period: Int, // can be -
    val mapTypeId: String
)