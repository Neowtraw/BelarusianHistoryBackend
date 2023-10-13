package com.codingub.data

import com.codingub.data.models.achieves.Achieve
import com.codingub.data.models.tickets.PracticeQuestion
import com.codingub.data.models.tickets.Ticket
import com.codingub.data.models.tickets.TicketQuestion
import com.codingub.data.models.users.User
import com.codingub.data.requests.InsertPqRequest
import com.codingub.data.requests.InsertTicketRequest
import com.codingub.data.requests.InsertTqRequest
import com.codingub.data.responses.*
import com.codingub.utils.Constants
import com.codingub.utils.HistoryLogger
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.UpdateOptions
import io.ktor.http.*
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.pull
import org.litote.kmongo.push
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.setValue

class HistoryDatabase {

    private val database = KMongo.createClient(
        connectionString = "mongodb+srv://neowtraw:${Constants.MONGO_PW}@historycluster.xg5enip.mongodb.net/${Constants.MONGO_DB_NAME}?retryWrites=true&w=majority"
    ).coroutine
        .getDatabase(Constants.MONGO_DB_NAME)

    //tables of all data
    private val userCollection = database.getCollection<User>()
    private val ticketCollection = database.getCollection<Ticket>()
    private val tqCollection = database.getCollection<TicketQuestion>()
    private val achieveCollection = database.getCollection<Achieve>()

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

//    suspend fun resetAllTickets(): ServerResponse<Any> {
//        if (ticketCollection.find().toList().isEmpty()) {
//            return ServerResponse(
//                message = "Tickets are already empty",
//                status = HttpStatusCode.Conflict.value
//            )
//        }
//        ticketCollection.deleteMany(Document())
//        return ServerResponse(
//            status = HttpStatusCode.OK.value
//        )
//    }

    suspend fun deleteTicketByName(name: String): ServerResponse<Any> {
        return if (ticketCollection.findOne(Ticket::name eq name) != null) {
            ticketCollection.deleteOne(eq("name", name))
            ServerResponse(
                message = "Ticket deleted successfully",
                status = HttpStatusCode.OK.value
            )
        } else {
            ServerResponse(
                message = "Ticket is not found",
                status = HttpStatusCode.Conflict.value
            )
        }
    }

    suspend fun getAllTickets(): ServerResponse<TicketResponse> {
        return ServerResponse(
            data = TicketResponse(
                ticketList = ticketCollection.find().toList()
            ),
            status = HttpStatusCode.OK.value
        )
    }

    suspend fun insertOrUpdateTicket(request: InsertTicketRequest): ServerResponse<String> {
        val ticket = ticketCollection.findOne(Ticket::name eq request.name)

        if (ticket != null) {
            val updatedTicket = ticket.copy(
                name = request.name,
                timer = request.timer,
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

    /*
        TicketQuestion
     */
    suspend fun insertOrUpdateTq(ticketId: String, question: InsertTqRequest): ServerResponse<Any> {
        val tqList = ticketCollection.findOne(Ticket::id eq ticketId)?.questions
            ?: emptyList()

        val tq = tqList.find { it.name == question.name }

        if (tq != null) {
            val updatedList = tqList.map {
                if (it.id == question.ticketId) {
                    it.copy(
                        name = question.name,
                        info = question.info,
                        achieve = question.achieve
                    )
                } else {
                    it
                }
            }
            ticketCollection.updateOne(Ticket::id eq ticketId, setValue(Ticket::questions, updatedList))

            return ServerResponse(
                message = "Ticket Question updated",
                status = HttpStatusCode.OK.value
            )
        } else {
            val insertedTq = TicketQuestion(
                name = question.name,
                info = question.info,
                achieve = question.achieve
            )
            ticketCollection.updateOne(
                Ticket::id eq ticketId,
                push(Ticket::questions, insertedTq),
                UpdateOptions().upsert(true)
            )

            return ServerResponse(
                message = "Ticket Question inserted",
                status = HttpStatusCode.OK.value
            )
        }
    }

    suspend fun deleteTqById(ticketId: String, questionId: String): ServerResponse<Any> {
        val tqList = ticketCollection.findOne(Ticket::id eq ticketId)?.questions ?: emptyList()
        val tq = tqList.find { it.id == questionId }

        if(tq != null){
            ticketCollection.updateOne(Ticket::id eq ticketId, pull(Ticket::questions, tq))
            return ServerResponse(
                message = "Ticket deleted successfully",
                status = HttpStatusCode.OK.value
            )
        }
        return ServerResponse(
            message = "No ticket to delete",
            status = HttpStatusCode.Conflict.value
        )
    }

    suspend fun getAllTq(ticketId: String): ServerResponse<TqResponse> {

        return ServerResponse(
            data = TqResponse(
                ticketCollection.findOne(Ticket::id eq ticketId)?.questions
                    ?: emptyList()
            ),
            status = HttpStatusCode.OK.value
        )
    }

    /*
       PracticeQuestion
    */

    suspend fun insertPractice(tqId: String, question: InsertPqRequest) : ServerResponse<Any>{
        val pqList = tqCollection.findOne(TicketQuestion::id eq tqId)?.practices
            ?: emptyList()

        val pq = pqList.find { it.name == question.name}

        if (pq != null) {
            val updatedList = pqList.map {
                if (it.name == question.name) {
                    it.copy(
                        info = question.info,
                        taskType = question.taskType,
                        answers = question.answers
                    )
                } else {
                    it
                }
            }
            ticketCollection.updateOne(TicketQuestion::id eq tqId, setValue(TicketQuestion::practices, updatedList))

            return ServerResponse(
                message = "Practice Question updated",
                status = HttpStatusCode.OK.value
            )
        } else {
            val insertedPq = PracticeQuestion(
                name = question.name,
                info = question.info,
                taskType = question.taskType,
                answers = question.answers
            )
            ticketCollection.updateOne(
                TicketQuestion::id eq tqId,
                push(TicketQuestion::practices, insertedPq),
                UpdateOptions().upsert(true)
            )

            return ServerResponse(
                message = "Practice Question inserted",
                status = HttpStatusCode.OK.value
            )
        }
    }

    suspend fun deletePracticeById(tqId: String, questionId: String) : ServerResponse<Any>{
        val pqList = tqCollection.findOne(TicketQuestion::id eq tqId)?.practices ?: emptyList()
        val pq = pqList.find { it.id == questionId }

        if(pq != null){
            ticketCollection.updateOne(Ticket::id eq tqId, pull(TicketQuestion::practices, pq))
            return ServerResponse(
                message = "Ticket deleted successfully",
                status = HttpStatusCode.OK.value
            )
        }
        return ServerResponse(
            message = "No ticket to delete",
            status = HttpStatusCode.Conflict.value
        )
    }

    suspend fun getAllPracticeFromTq(tqId: String) : ServerResponse<PqResponse> {
        return ServerResponse(
           data = PqResponse(
                tqCollection.findOne(TicketQuestion::id eq tqId)?.practices ?: emptyList()
           ),
            status = HttpStatusCode.OK.value
        )
    }

    /*
        Achieve
     */

    suspend fun getAllAchieves() : ServerResponse<AchieveResponse>{
        return ServerResponse(
            data = AchieveResponse(
                achieveCollection.find().toList()
            ),
            status = HttpStatusCode.OK.value
        )
    }


}