package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.DeleteTqsRequest
import com.codingub.data.requests.InsertTqRequest
import com.codingub.data.responses.TqResponse

interface TqDataRepository {
    /*
       Administrator
    */
    suspend fun insertTq(request: InsertTqRequest): Boolean
    suspend fun deleteTqById(questionId: String): Boolean
    suspend fun deleteTqsByIds(request: DeleteTqsRequest): Boolean

    /*
        User
     */
    suspend fun getAllTq(): TqResponse
    suspend fun getAllTqFromTicket(ticketId: String): TqResponse
}

class TqDataRepositoryImpl (
    private val database: HistoryDatabase,
) : TqDataRepository {
    override suspend fun insertTq(request: InsertTqRequest): Boolean {
        return database.insertOrUpdateTq(request)
    }

    override suspend fun deleteTqById(questionId: String): Boolean {
        return database.deleteTqById(questionId)
    }

    override suspend fun deleteTqsByIds(request: DeleteTqsRequest): Boolean {
        return database.deleteTqsByIds(request.ids)
    }

    override suspend fun getAllTqFromTicket(ticketId: String): TqResponse {
        return database.getAllTqFromTicket(ticketId)
    }

    override suspend fun getAllTq(): TqResponse {
        return database.getAllTq()
    }
}