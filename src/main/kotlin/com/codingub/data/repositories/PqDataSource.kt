package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.InsertPqRequest
import com.codingub.data.responses.PqResponse

interface PqDataRepository{
    /*
        Administrator
     */
    suspend fun insertPractice(tqId: String, question: InsertPqRequest) : Boolean
    suspend fun deletePracticeById(tqId: String, questionId: String) : Boolean

    /*
        User
     */
    suspend fun getAllPracticeFromTq(tqId: String) : PqResponse

}

class PqDataRepositoryImpl constructor(
    private val database: HistoryDatabase
) : PqDataRepository{

    override suspend fun insertPractice(tqId: String, question: InsertPqRequest): Boolean {
        return database.insertPractice(tqId, question)
    }

    override suspend fun deletePracticeById(tqId: String, questionId: String): Boolean {
        return database.deletePracticeById(tqId, questionId)
    }

    override suspend fun getAllPracticeFromTq(tqId: String): PqResponse {
        return database.getAllPracticeFromTq(tqId)
    }
}