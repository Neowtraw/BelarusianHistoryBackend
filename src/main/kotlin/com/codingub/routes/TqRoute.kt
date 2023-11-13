package com.codingub.routes

import com.codingub.data.repositories.TqDataRepository
import com.codingub.data.requests.InsertTqRequest
import com.codingub.data.requests.DeleteTqRequest
import com.codingub.utils.Constants.EndPoints.ROUTE_INSERT_TQ
import com.codingub.utils.Constants.EndPoints.ROUTE_RESET_TQ
import com.codingub.utils.Constants.EndPoints.ROUTE_TQ
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent

private val tqDataSource: TqDataRepository by KoinJavaComponent.inject(TqDataRepository::class.java)

fun Route.insertTq() {
    post(ROUTE_INSERT_TQ) {
        val request = kotlin.runCatching { call.receiveNullable<InsertTqRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val wasAcknowledged = tqDataSource.insertTq(ticketId = request.ticketId, question = request)
        if(!wasAcknowledged){
            call.respond(HttpStatusCode.Conflict, "tq not found")
        }
        call.respond(HttpStatusCode.OK, "tq inserted successfully")
        return@post
    }
}

fun Route.deleteTqById() {
    post(ROUTE_RESET_TQ) {
        val request = kotlin.runCatching { call.receiveNullable<DeleteTqRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val wasAcknowledged = tqDataSource.deleteTqById(request.ticketId, request.questionId)
        if(!wasAcknowledged){
            call.respond(HttpStatusCode.Conflict, "tq not found")
        }
        call.respond(HttpStatusCode.OK, "tq deleted successfully")
    }
}
fun Route.getAllTqFromTicket() {
    get("$ROUTE_TQ/") {
        val request = kotlin.runCatching {  call.request.queryParameters["id"] }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "Missing ticket request parameter")
            return@get
        }
        call.respond(
            tqDataSource.getAllTqFromTicket(request)
        )
        return@get
    }
}

fun Route.getAllTq() {
    get(ROUTE_TQ){
        call.respond(
            tqDataSource.getAllTq()
        )
    }
}