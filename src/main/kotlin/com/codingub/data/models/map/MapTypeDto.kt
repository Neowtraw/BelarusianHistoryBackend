package com.codingub.data.models.map

import com.codingub.sdk.MapCategory
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class MapTypeDto(
    val id: String,
    val title: String,
    val type: MapCategory, // unique
    val description: String
)