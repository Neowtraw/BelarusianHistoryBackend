package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.models.tickets.TicketQuestion
import com.codingub.data.requests.InsertTqRequest
import com.codingub.data.responses.ServerResponse
import com.codingub.data.responses.TqResponse
import org.bson.types.ObjectId

interface TqDataRepository {
    /*
       Administrator
    */
    suspend fun insertTq(ticketId: String, question: InsertTqRequest) : ServerResponse<Any>
    suspend fun deleteTqById(ticketId: String, questionId: String) : ServerResponse<Any>
   // suspend fun insertOrUpdateTqList(ticketId: ObjectId, practiceList : List<TicketQuestion>) : ServerResponse<Any>

    /*
        User
     */

    suspend fun getAllTq(ticketId: String) : ServerResponse<TqResponse>
}

class TqDataRepositoryImpl constructor(
   private val database: HistoryDatabase
) : TqDataRepository{
    override suspend fun insertTq(ticketId: String, question: InsertTqRequest): ServerResponse<Any> {
        return database.insertOrUpdateTq(ticketId, question)
    }

    override suspend fun deleteTqById(ticketId: String, questionId: String): ServerResponse<Any> {
        return database.deleteTqById(ticketId, questionId)
    }

    override suspend fun getAllTq(ticketId: String): ServerResponse<TqResponse> {
        return database.getAllTq(ticketId)
    }
}