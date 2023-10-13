package com.codingub.data.models.achieves

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Results(
    @BsonId val id : String = ObjectId().toString(),
    val type : Int, //type of result -> 1- Ticket 2- Practice
    val typeId: String //id of object (Ticket/Practice)
)
