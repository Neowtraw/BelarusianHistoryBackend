package com.codingub.data.models.achieves

import com.codingub.sdk.AchieveType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class AchieveDto(
    @BsonId val id : String = ObjectId().toString(),
    val name : String,
    val info : String,
    val type: AchieveType,
    val ownerId: String
)
