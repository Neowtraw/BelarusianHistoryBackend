package com.codingub.plugins

import com.codingub.data.repositories.UserDataRepository
import com.codingub.routes.*
import com.codingub.security.hashing.HashingService
import com.codingub.security.token.TokenConfig
import com.codingub.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureRouting(tokenConfig : TokenConfig) {
    routing {

        /*
            Authentication
         */

        signUp()
        signIn(tokenConfig)
        authenticate()
        getSecretInfo()

        /*
            Tickets
         */

        getAllTickets()
        deleteTicketByName()
        insertOrUpdateTicket()

        /*
            TicketQuestion
         */
        getAllTq()
        deleteTqById()
        insertTq()
    }
}
