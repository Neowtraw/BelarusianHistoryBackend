package com.codingub.routes

import com.codingub.data.repositories.PqDataRepository
import com.codingub.data.requests.DeletePqRequest
import com.codingub.data.requests.InsertPqRequest
import com.codingub.utils.Constants.EndPoints.ROUTE_INSERT_PQ
import com.codingub.utils.Constants.EndPoints.ROUTE_PQ
import com.codingub.utils.Constants.EndPoints.ROUTE_RESET_PQ
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent


private val pqDataSource: PqDataRepository by KoinJavaComponent.inject(PqDataRepository::class.java)

fun Route.insertPractice(){
    post(ROUTE_INSERT_PQ) {
        val request = kotlin.runCatching { call.receiveNullable<InsertPqRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val wasAcknowledged = pqDataSource.insertPractice(question = request)
        if(!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, "Practice not found")
            return@post
        }

        call.respond(HttpStatusCode.OK, "Practice inserted successfully")
        return@post
    }
}

fun Route.deletePracticeById(){
    post(ROUTE_RESET_PQ) {
        val request = kotlin.runCatching { call.receiveNullable<DeletePqRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val wasAcknowledged =pqDataSource.deletePracticeById(request.questionId)

        if(!wasAcknowledged){
            call.respond(HttpStatusCode.Conflict, "Practice not found")
            return@post

        }
        call.respond(HttpStatusCode.OK, "Practice deleted successfully")
        return@post
    }
}

fun Route.getAllPracticeFromTq(){
    get("$ROUTE_PQ/") {
        val request = kotlin.runCatching { call.request.queryParameters["id"] }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }
        call.respond(
            pqDataSource.getAllPracticeFromTq(request)
        )
        return@get
    }
}