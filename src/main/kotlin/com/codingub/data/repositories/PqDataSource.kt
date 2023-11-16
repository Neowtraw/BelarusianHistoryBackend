package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.InsertPqRequest
import com.codingub.data.responses.PqResponse

interface PqDataRepository{
    /*
        Administrator
     */
    suspend fun insertPractice(question: InsertPqRequest) : Boolean
    suspend fun deletePracticeById(questionId: String) : Boolean

    /*
        User
     */
    suspend fun getAllPracticeFromTq(tqId: String) : PqResponse

}

class PqDataRepositoryImpl constructor(
    private val database: HistoryDatabase
) : PqDataRepository{

    override suspend fun insertPractice(question: InsertPqRequest): Boolean {
        return database.insertPractice(question)
    }

    override suspend fun deletePracticeById(questionId: String): Boolean {
        return database.deletePracticeById(questionId)
    }

    override suspend fun getAllPracticeFromTq(tqId: String): PqResponse {
        return database.getAllPracticeFromTq(tqId)
    }
}