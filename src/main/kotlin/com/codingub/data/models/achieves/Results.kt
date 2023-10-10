package com.codingub.data.models.achieves

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Results(
    @BsonId val id : String = ObjectId().toString(),
    val tickets: HashMap<Int, Int> //tickets passed
)
