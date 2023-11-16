package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.InsertTqRequest
import com.codingub.data.responses.TqResponse

interface TqDataRepository {
    /*
       Administrator
    */
    suspend fun insertTq(question: InsertTqRequest) : Boolean
    suspend fun deleteTqById(questionId: String) : Boolean

    /*
        User
     */
    suspend fun getAllTq() : TqResponse
    suspend fun getAllTqFromTicket(ticketId: String) : TqResponse
}

class TqDataRepositoryImpl constructor(
   private val database: HistoryDatabase
) : TqDataRepository{
    override suspend fun insertTq(question: InsertTqRequest): Boolean {
        return database.insertOrUpdateTq(question)
    }

    override suspend fun deleteTqById(questionId: String): Boolean {
        return database.deleteTqById(questionId)
    }

    override suspend fun getAllTqFromTicket(ticketId: String): TqResponse {
        return database.getAllTqFromTicket(ticketId)
    }

    override suspend fun getAllTq() : TqResponse {
        return database.getAllTq()
    }
}