package com.codingub.data

import com.codingub.data.models.tickets.PracticeQuestion
import com.codingub.data.models.tickets.Ticket
import com.codingub.data.models.tickets.TicketQuestion
import com.codingub.data.models.users.User
import com.codingub.utils.Constants
import com.codingub.utils.HistoryLogger
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.eq
import org.litote.kmongo.reactivestreams.KMongo

class HistoryDatabase {

    val database = KMongo.createClient(
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
    suspend fun getUserByLogin(login : String) : User?{
        return userCollection.findOne(User::login eq login)
    }

    suspend fun insertUser(user: User): Boolean{
        val foundUser = userCollection.findOne(User::login eq user.login)

        if(foundUser != null){
            HistoryLogger.info("Such login is already used")
            return false
        }

        HistoryLogger.info("New user created with login: ${user.login}")
        return userCollection.insertOne(user).wasAcknowledged()
    }

    /*
        Ticket
     */

}