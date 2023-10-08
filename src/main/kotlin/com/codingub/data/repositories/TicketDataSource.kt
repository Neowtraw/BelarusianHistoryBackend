package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.models.tickets.Ticket
import com.codingub.data.requests.InsertTicketRequest
import com.codingub.data.responses.ServerResponse
import org.bson.types.ObjectId

interface TicketDataRepository {

 /*
   Only Administrator
  */
    suspend fun resetAllTickets() : ServerResponse<Any>
    suspend fun deleteTicketByName(name: String)  : ServerResponse<Any>
    suspend fun insertOrUpdateTicket(request: InsertTicketRequest)  : ServerResponse<ObjectId>
 /*
   User
  */
    suspend fun getAllTickets() : ServerResponse<List<Ticket>>

 // suspend fun updateTicketPassedFromUser()
}


class TicketDataRepositoryImpl constructor(
    private val database: HistoryDatabase
) : TicketDataRepository{
    override suspend fun resetAllTickets() : ServerResponse<Any>{
        return database.resetAllTickets()
    }

    override suspend fun deleteTicketByName(name: String) : ServerResponse<Any> {
        return database.deleteTicketByName(name)
    }

    override suspend fun insertOrUpdateTicket(request: InsertTicketRequest) : ServerResponse<ObjectId> {
        return database.insertOrUpdateTicket(request)
    }

    override suspend fun getAllTickets() : ServerResponse<List<Ticket>>{
       return database.getAllTickets()
    }
}