package com.codingub.routes

import com.codingub.data.repositories.PqDataRepository
import com.codingub.data.requests.DeletePqRequest
import com.codingub.data.requests.GetPqRequest
import com.codingub.data.requests.InsertPqRequest
import com.codingub.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent


private val pqDataSource: PqDataRepository by KoinJavaComponent.inject(PqDataRepository::class.java)

fun Route.insertPractice(){
    post(Constants.EndPoints.ROUTE_INSERT_PQ) {
        val request = kotlin.runCatching { call.receiveNullable<InsertPqRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val pqId = pqDataSource.insertPractice(tqId = request.tqId, question = request)
        call.respond(pqId)
        return@post
    }
}

fun Route.deletePracticeById(){
    post(Constants.EndPoints.ROUTE_RESET_PQ) {
        val request = kotlin.runCatching { call.receiveNullable<DeletePqRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        call.respond(pqDataSource.deletePracticeById(request.tqId, request.questionId))
    }
}

fun Route.getAllPractice(){
    get(Constants.EndPoints.ROUTE_PQ) {
        val request = kotlin.runCatching { call.receiveNullable<GetPqRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }
        call.respond(
            pqDataSource.getAllPracticeFromTq(request.tqId)
        )
        return@get
    }
}