package com.codingub.data.models.users

import com.codingub.sdk.AchieveType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class ResultDto(
    @BsonId val id : String = ObjectId().toString(),
    val type : AchieveType, //type of result -> 1- Ticket 2- Practice
    val answer: Any?,
    val achieveId: String, //id of object (Ticket/Practice)
    val userLogin: String
)


