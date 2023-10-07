package com.codingub.data.models.achieves

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Achieve(
    @BsonId val id : ObjectId = ObjectId(),
    val name : String,
    val info : String,
    val isPassed: Boolean,
    val type: Int // AchieveType
)
