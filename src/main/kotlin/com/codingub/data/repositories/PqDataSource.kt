package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.InsertPqRequest
import com.codingub.data.responses.PqResponse
import com.codingub.data.responses.ServerResponse

interface PqDataRepository{
    /*
        Administrator
     */
    suspend fun insertPractice(tqId: String, question: InsertPqRequest) : ServerResponse<Any>
    suspend fun deletePracticeById(tqId: String, questionId: String) : ServerResponse<Any>

    /*
        User
     */
    suspend fun getAllPracticeFromTq(tqId: String) : ServerResponse<PqResponse>

}

class PqDataRepositoryImpl constructor(
    private val database: HistoryDatabase
) : PqDataRepository{

    override suspend fun insertPractice(tqId: String, question: InsertPqRequest): ServerResponse<Any> {
        return database.insertPractice(tqId, question)
    }

    override suspend fun deletePracticeById(tqId: String, questionId: String): ServerResponse<Any> {
        return database.deletePracticeById(tqId, questionId)
    }

    override suspend fun getAllPracticeFromTq(tqId: String): ServerResponse<PqResponse> {
        return database.getAllPracticeFromTq(tqId)
    }
}