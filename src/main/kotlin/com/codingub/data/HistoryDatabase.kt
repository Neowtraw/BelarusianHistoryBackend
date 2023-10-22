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
import com.mongodb.client.model.UpdateOptions
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

    suspend fun changeRoleByLogin(login: String, accessLevel: Int): Boolean {
        val user = userCollection.findOne(User::login eq login)
        return if (user != null) {
            val updatedUser = user.copy(
                accessLevel = accessLevel
            )
            userCollection.updateOne(
                User::login eq login,
                updatedUser
            ).wasAcknowledged()
        } else {
            false
        }
    }

    /*
        Ticket
     */

    suspend fun deleteTicketByName(name: String): Boolean {
        val ticket = ticketCollection.findOne(Ticket::name eq name)
        return if (ticket != null) {
            ticketCollection.deleteOne(Ticket::name eq name).wasAcknowledged()
        } else {
            HistoryLogger.info("Ticket not found")
            false
        }
    }

    suspend fun getAllTickets(): TicketResponse {
        return TicketResponse(
            ticketList = ticketCollection.find().toList()
        )
    }

    suspend fun insertOrUpdateTicket(request: InsertTicketRequest): Boolean {
        val ticket = ticketCollection.findOne(Ticket::name eq request.name)

        if (ticket != null) {
            val updatedTicket = ticket.copy(
                name = request.name,
                timer = request.timer,
                achievement = request.achieve
            )
            if (ticket != updatedTicket)
                return ticketCollection.updateOne(
                    Ticket::id eq ticket.id,
                    updatedTicket
                ).wasAcknowledged()
        } else {
            val insertedTicket = Ticket(
                name = request.name,
                timer = request.timer,
                achievement = request.achieve
            )
            return ticketCollection.insertOne(insertedTicket).wasAcknowledged()
        }
        HistoryLogger.info("Ticket not found")
        return false
    }

    /*
        TicketQuestion
     */
    suspend fun insertOrUpdateTq(ticketId: String, question: InsertTqRequest): Boolean {
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
            return ticketCollection.updateOne(Ticket::id eq ticketId, setValue(Ticket::questions, updatedList))
                .wasAcknowledged()

        } else {
            val insertedTq = TicketQuestion(
                name = question.name,
                info = question.info,
                achieve = question.achieve
            )

            return ticketCollection.updateOne(
                Ticket::id eq ticketId,
                push(Ticket::questions, insertedTq),
                UpdateOptions().upsert(true)
            ).wasAcknowledged()
        }
    }

    suspend fun deleteTqById(ticketId: String, questionId: String): Boolean {
        val tqList = ticketCollection.findOne(Ticket::id eq ticketId)?.questions ?: emptyList()
        val tq = tqList.find { it.id == questionId }

        if (tq != null) {
            return ticketCollection.updateOne(Ticket::id eq ticketId, pull(Ticket::questions, tq))
                .wasAcknowledged()
        }
        HistoryLogger.info("No tq to delete")
        return false
    }

    suspend fun getAllTq(ticketId: String): TqResponse {
        return TqResponse(
            ticketCollection.findOne(Ticket::id eq ticketId)?.questions
                ?: emptyList()
        )
    }

    /*
       PracticeQuestion
    */

    suspend fun insertPractice(tqId: String, question: InsertPqRequest): Boolean {
        val pqList = tqCollection.findOne(TicketQuestion::id eq tqId)?.practices
            ?: emptyList()
        val pq = pqList.find { it.name == question.name }

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
            return ticketCollection.updateOne(
                TicketQuestion::id eq tqId,
                setValue(TicketQuestion::practices, updatedList)
            )
                .wasAcknowledged()
        } else {
            val insertedPq = PracticeQuestion(
                name = question.name,
                info = question.info,
                taskType = question.taskType,
                answers = question.answers
            )
            return ticketCollection.updateOne(
                TicketQuestion::id eq tqId,
                push(TicketQuestion::practices, insertedPq),
                UpdateOptions().upsert(true)
            ).wasAcknowledged()
        }
    }

    suspend fun deletePracticeById(tqId: String, questionId: String): Boolean {
        val pqList = tqCollection.findOne(TicketQuestion::id eq tqId)?.practices ?: emptyList()
        val pq = pqList.find { it.id == questionId }

        if (pq != null) {
            return ticketCollection.updateOne(Ticket::id eq tqId, pull(TicketQuestion::practices, pq))
                .wasAcknowledged()
        }
        HistoryLogger.info("No pq to delete")
        return false
    }

    suspend fun getAllPracticeFromTq(tqId: String): PqResponse {
        return PqResponse(
            tqCollection.findOne(TicketQuestion::id eq tqId)?.practices ?: emptyList()
        )

    }

    /*
        Achieve
     */

    suspend fun getAllAchieves(): AchieveResponse {
        return AchieveResponse(
            achieveCollection.find().toList()
        )
    }


}