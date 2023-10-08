package com.codingub.routes

import com.codingub.data.repositories.TicketDataRepository
import com.codingub.data.requests.InsertTicketRequest
import com.codingub.data.requests.TicketNameRequest
import com.codingub.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent.inject

private val ticketDataSource: TicketDataRepository by inject(TicketDataRepository::class.java)

fun Route.resetAllTickets() {
    post(Constants.EndPoints.ROUTE_RESET_ALL_TICKETS) {
        //test
        call.respond(ticketDataSource.resetAllTickets())
        return@post
    }
}

fun Route.insertOrUpdateTicket() {
    post(Constants.EndPoints.ROUTE_INSERT_TICKET) {
        val request = kotlin.runCatching { call.receiveNullable<InsertTicketRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val ticketId = ticketDataSource.insertOrUpdateTicket(request)

        call.respond(ticketId)
        return@post
    }
}

fun Route.deleteTicketByName() {
    post(Constants.EndPoints.ROUTE_RESET_TICKET) {
        val request = kotlin.runCatching { call.receiveNullable<TicketNameRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        call.respond(ticketDataSource.deleteTicketByName(request.name))
    }
}

fun Route.getAllTickets() {
    get(Constants.EndPoints.ROUTE_TICKETS) {
        call.respond(
            ticketDataSource.getAllTickets()
        )
    }
}