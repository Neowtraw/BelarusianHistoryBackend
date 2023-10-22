package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.InsertTqRequest
import com.codingub.data.responses.TqResponse

interface TqDataRepository {
    /*
       Administrator
    */
    suspend fun insertTq(ticketId: String, question: InsertTqRequest) : Boolean
    suspend fun deleteTqById(ticketId: String, questionId: String) : Boolean

    /*
        User
     */

    suspend fun getAllTq(ticketId: String) : TqResponse
}

class TqDataRepositoryImpl constructor(
   private val database: HistoryDatabase
) : TqDataRepository{
    override suspend fun insertTq(ticketId: String, question: InsertTqRequest): Boolean {
        return database.insertOrUpdateTq(ticketId, question)
    }

    override suspend fun deleteTqById(ticketId: String, questionId: String): Boolean {
        return database.deleteTqById(ticketId, questionId)
    }

    override suspend fun getAllTq(ticketId: String): TqResponse {
        return database.getAllTq(ticketId)
    }
}