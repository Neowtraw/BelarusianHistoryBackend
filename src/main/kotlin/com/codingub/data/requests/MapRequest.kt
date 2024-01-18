package com.codingub.data.requests

import com.codingub.data.responses.models.map.MapLabel
import com.codingub.data.responses.models.map.MapType

data class LabelRequest(
    val label: MapLabel
)
data class AddPeriodRequest(
    val mapType: MapType,
    val map: String,
    val period: Int
)