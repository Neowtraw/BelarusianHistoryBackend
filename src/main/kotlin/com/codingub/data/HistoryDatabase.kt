package com.codingub.data

import com.codingub.data.models.tickets.Ticket
import com.codingub.data.models.tickets.TicketQuestion
import com.codingub.data.models.users.User
import com.codingub.data.requests.InsertTicketRequest
import com.codingub.data.responses.ServerResponse
import com.codingub.utils.Constants
import com.codingub.utils.HistoryLogger
import com.mongodb.client.model.Filters.eq
import io.ktor.http.*
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class HistoryDatabase {

    private val database = KMongo.createClient(
        connectionString = "mongodb+srv://neowtraw:${Constants.MONGO_PW}@historycluster.xg5enip.mongodb.net/${Constants.MONGO_DB_NAME}?retryWrites=true&w=majority"
    ).coroutine
        .getDatabase(Constants.MONGO_DB_NAME)

    //tables of all data
    private val userCollection = database.getCollection<User>()
    private val ticketCollection = database.getCollection<Ticket>()
    private val tqCollection = database.getCollection<TicketQuestion>()

    /*
        User
     */
    suspend fun getUserByLogin(login: String): User? {
        return userCollection.findOne(User::login eq login)
    }

    suspend fun insertUser(user: User): Boolean {
        val foundUser = userCollection.findOne(User::login eq user.login)

        if (foundUser != null) {
            HistoryLogger.info("Such login is already used")
            return false
        }

        HistoryLogger.info("New user created with login: ${user.login}")
        return userCollection.insertOne(user).wasAcknowledged()
    }

    /*
        Ticket
     */

    suspend fun resetAllTickets(): ServerResponse<Any> {
        if (ticketCollection.find().toList().isEmpty()) {
            return ServerResponse(
                message = "Tickets are already empty",
                status = HttpStatusCode.Conflict.value
            )
        }
        ticketCollection.deleteMany(Document())
        return ServerResponse(
            status = HttpStatusCode.OK.value
        )
    }

    suspend fun deleteTicketByName(name: String) : ServerResponse<Any> {
        return if (ticketCollection.findOne(Ticket::name eq name) != null) {
            ticketCollection.deleteOne(eq("name", name))
            ServerResponse(
                message = "Ticket deleted successfully",
                status = HttpStatusCode.OK.value
            )
        } else{
            ServerResponse(
                message = "Ticket is not found",
                status = HttpStatusCode.Conflict.value
            )
        }
    }

    suspend fun getAllTickets(): ServerResponse<List<Ticket>> {
        return ServerResponse(
            data = ticketCollection.find().toList(),
            status = HttpStatusCode.OK.value
        )
    }

    suspend fun insertOrUpdateTicket(request: InsertTicketRequest): ServerResponse<ObjectId> {
        val ticket = ticketCollection.findOne(Ticket::name eq request.name)
        if (ticket != null) {
            val updatedTicket = ticket.copy(
                name = request.name,
                timer = request.timer,
                questions = request.questions,
                achievement = request.achieve
            )
            if (ticket != updatedTicket)
                ticketCollection.updateOne(
                    Ticket::id eq ticket.id,
                    updatedTicket
                )
            return ServerResponse(
                data = updatedTicket.id,
                message = "Ticket updated",
                status = HttpStatusCode.OK.value
            )
        } else {
            val insertedTicket = Ticket(
                name = request.name,
                timer = request.timer,
                questions = request.questions,
                achievement = request.achieve
            )
            ticketCollection.insertOne(insertedTicket)
            return ServerResponse(
                data = insertedTicket.id,
                message = "Ticket inserted",
                status = HttpStatusCode.OK.value
            )
        }
    }

}