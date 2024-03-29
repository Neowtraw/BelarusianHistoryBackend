package com.codingub.plugins

import com.codingub.routes.*
import com.codingub.security.token.TokenConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    tokenConfig: TokenConfig,
) {
    routing {

        /*
            Authentication
         */

        signUp()
        signIn(tokenConfig)
        authenticate()
        getSecretInfo()

        /*
            Users
         */
        changeRoleByLogin()

        /*
            Groups
         */
        createGroup()
        deleteGroup()
        inviteUserToGroup()
        deleteUserFromGroup()
        getAllGroups()

        /*
            Tickets
         */

        getAllTickets()
        deleteTicketByName()
        deleteTicketsByIds()
        insertOrUpdateTicket()

        /*
            TicketQuestion
         */
        getAllTqFromTicket()
        getAllTq()
        deleteTqsByIds()
        deleteTqById()
        insertTq()

        /*
            PracticeQuestion
         */
        getAllPracticeFromTq()
        deletePracticeById()
        deletePracticesByIds()
        insertPractice()

        /*
            Achieve
         */
        getAllAchieves()
        getTypeAchieves()

        /*
            Results
         */
        setAchieveCompletedByUser()
        resetResultsWithType()
        getResultsByType()
        getAllResults()

        /*
            Event
         */
        getAllEvents()

        /*
            Map
         */
        addLabelOnMap()
        updateLabelOnMap()
        getMap()
        getMapTypes()
        deleteLabelFromMap()
    }
}
