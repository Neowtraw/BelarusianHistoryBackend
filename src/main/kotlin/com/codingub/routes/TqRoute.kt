package com.codingub.routes

import com.codingub.data.repositories.TqDataRepository
import com.codingub.data.requests.InsertTqRequest
import com.codingub.data.requests.DeleteTqRequest
import com.codingub.data.requests.GetTqRequest
import com.codingub.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent

private val tqDataSource: TqDataRepository by KoinJavaComponent.inject(TqDataRepository::class.java)

fun Route.insertTq() {
    post(Constants.EndPoints.ROUTE_INSERT_TQ) {
        val request = kotlin.runCatching { call.receiveNullable<InsertTqRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val tqId = tqDataSource.insertTq(ticketId = request.ticketId, question = request)
        call.respond(tqId)
        return@post
    }
}

fun Route.deleteTqById() {
    post(Constants.EndPoints.ROUTE_RESET_TQ) {
        val request = kotlin.runCatching { call.receiveNullable<DeleteTqRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        call.respond(tqDataSource.deleteTqById(request.ticketId, request.questionId))
    }
}

fun Route.getAllTq() {
    get(Constants.EndPoints.ROUTE_TQ) {
        val request = kotlin.runCatching { call.receiveNullable<GetTqRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }
        call.respond(
            tqDataSource.getAllTq(request.ticketId)
        )
        return@get
    }
}