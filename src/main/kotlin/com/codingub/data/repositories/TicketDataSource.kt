package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.InsertTicketRequest
import com.codingub.data.responses.ServerResponse
import com.codingub.data.responses.TicketResponse

interface TicketDataRepository {

 /*
   Only Administrator
  */
    suspend fun deleteTicketByName(name: String)  : ServerResponse<Any>
    suspend fun insertOrUpdateTicket(request: InsertTicketRequest)  : ServerResponse<String>
 /*
   User
  */
    suspend fun getAllTickets() : ServerResponse<TicketResponse>

 // suspend fun updateTicketPassedFromUser()
}


class TicketDataRepositoryImpl constructor(
    private val database: HistoryDatabase
) : TicketDataRepository{

    override suspend fun deleteTicketByName(name: String) : ServerResponse<Any> {
        return database.deleteTicketByName(name)
    }

    override suspend fun insertOrUpdateTicket(request: InsertTicketRequest) : ServerResponse<String> {
        return database.insertOrUpdateTicket(request)
    }

    override suspend fun getAllTickets() : ServerResponse<TicketResponse>{
       return database.getAllTickets()
    }
}