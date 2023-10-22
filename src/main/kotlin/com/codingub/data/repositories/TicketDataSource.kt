package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.DeleteTicketRequest
import com.codingub.data.requests.InsertTicketRequest
import com.codingub.data.responses.TicketResponse

interface TicketDataRepository {

 /*
   Only Administrator
  */
    suspend fun deleteTicketByName(request: DeleteTicketRequest)  : Boolean
    suspend fun insertOrUpdateTicket(request: InsertTicketRequest)  : Boolean
 /*
   User
  */
    suspend fun getAllTickets() : TicketResponse
}


class TicketDataRepositoryImpl constructor(
    private val database: HistoryDatabase
) : TicketDataRepository{

    override suspend fun deleteTicketByName(request: DeleteTicketRequest) : Boolean {
        return database.deleteTicketByName(request.name)
    }

    override suspend fun insertOrUpdateTicket(request: InsertTicketRequest) : Boolean {
        return database.insertOrUpdateTicket(request)
    }

    override suspend fun getAllTickets() : TicketResponse{
       return database.getAllTickets()
    }
}