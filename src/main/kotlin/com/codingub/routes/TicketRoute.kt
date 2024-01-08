package com.codingub.routes

import com.codingub.data.repositories.TicketDataRepository
import com.codingub.data.requests.DeleteTicketRequest
import com.codingub.data.requests.DeleteTicketsRequest
import com.codingub.data.requests.InsertTicketRequest
import com.codingub.utils.Constants.EndPoints.ROUTE_INSERT_TICKET
import com.codingub.utils.Constants.EndPoints.ROUTE_RESET_TICKET
import com.codingub.utils.Constants.EndPoints.ROUTE_RESET_TICKETS
import com.codingub.utils.Constants.EndPoints.ROUTE_TICKET
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val ticketDataSource: TicketDataRepository by inject(TicketDataRepository::class.java)

fun Route.insertOrUpdateTicket() {
    post(ROUTE_INSERT_TICKET) {
        val request = kotlin.runCatching { call.receiveNullable<InsertTicketRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val wasAcknowledged = ticketDataSource.insertOrUpdateTicket(request)
        if (!wasAcknowledged) {
            call.respond(
                HttpStatusCode.Conflict,
                "Ticket not found"
            )
            return@post
        }
        call.respond(HttpStatusCode.OK, "Ticket inserted successfully")
        return@post
    }
}

fun Route.deleteTicketByName() {
    post(ROUTE_RESET_TICKET) {
        val request = kotlin.runCatching { call.receiveNullable<DeleteTicketRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val wasAcknowledged = ticketDataSource.deleteTicketByName(request)
        if (!wasAcknowledged) {
            call.respond(
                HttpStatusCode.Conflict,
                "Ticket not found"
            )
            return@post
        }

        call.respond(HttpStatusCode.OK, "Ticket deleted successfully")
        return@post
    }
}

fun Route.deleteTicketsByIds() {
    post(ROUTE_RESET_TICKETS) {
        val request = kotlin.runCatching { call.receiveNullable<DeleteTicketsRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val wasAcknowledged = ticketDataSource.deleteTicketsByIds(request)
        if (!wasAcknowledged) {
            call.respond(
                HttpStatusCode.Conflict,
                "Tickets not found"
            )
            return@post
        }

        call.respond(HttpStatusCode.OK, "Tickets deleted successfully")
        return@post
    }
}

fun Route.getAllTickets() {
    get(ROUTE_TICKET) {
        call.respond(
            ticketDataSource.getAllTickets()
        )
    }
}