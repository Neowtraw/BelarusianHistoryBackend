package com.codingub.data.repositories

import com.codingub.data.models.tickets.PracticeQuestion
import com.codingub.data.responses.ServerResponse
import org.bson.types.ObjectId

interface PqDataRepository{
    /*
        Administrator
     */
    suspend fun insertPractice(tqId: ObjectId, question: PracticeQuestion) : ServerResponse<Any>
    suspend fun insertPracticeList(tqId: ObjectId,practiceList : List<PracticeQuestion>) : ServerResponse<Any>
    suspend fun deletePracticeById(tqId: ObjectId, questionId: ObjectId) : ServerResponse<Any>
    suspend fun resetAllPracticeFromTq(tqId: ObjectId) : ServerResponse<Any>

    /*
        User
     */
    suspend fun getAllPracticeFromTq(tqId: ObjectId) : ServerResponse<List<PracticeQuestion>>

}

class PqDataRepositoryImpl{

}