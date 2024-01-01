package com.codingub.routes

import com.codingub.data.repositories.UserDataRepository
import com.codingub.data.requests.*
import com.codingub.routes.authenticate
import com.codingub.sdk.AchieveType
import com.codingub.utils.Constants
import com.codingub.utils.Constants.EndPoints.ROUTE_DELETE_RESULTS
import com.codingub.utils.Constants.EndPoints.ROUTE_INSERT_RESULTS
import com.codingub.utils.Constants.EndPoints.ROUTE_RESULTS
import com.codingub.utils.Constants.EndPoints.ROUTE_ROLE_CHANGE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.koin.java.KoinJavaComponent

private val userDataSource: UserDataRepository by KoinJavaComponent.inject(UserDataRepository::class.java)
fun Route.changeRoleByLogin() {
    post(ROUTE_ROLE_CHANGE) {
        val request = kotlin.runCatching { call.receiveNullable<RoleRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val response = userDataSource.changeRoleByLogin(login = request.login, accessLevel = request.accessLevel)
        call.respond(response)
    }
}

fun Route.setAchieveCompletedByUser() {
    post(ROUTE_INSERT_RESULTS) {
        val request = kotlin.runCatching { call.receiveNullable<AddResultRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val response = userDataSource.setAchieveCompletedByUser(request)
        call.respond(response)
        return@post

    }
}

fun Route.resetResultsWithType() {
    post(ROUTE_DELETE_RESULTS) {
        val request = kotlin.runCatching { call.receiveNullable<ResetResultsWithTypeRequest>() }
            .getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val response = userDataSource.resetResultsWithType(request)
        call.respond(response)
    }
}

fun Route.getResultsByType() {
    get("$ROUTE_RESULTS/") {
        val type =kotlin.runCatching {  call.request.queryParameters["type"] }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "Missing type request query")
            return@get
        }
        val login = kotlin.runCatching {  call.request.queryParameters["login"] }.getOrNull() ?: kotlin.run {
        call.respond(HttpStatusCode.BadRequest, "Missing login request query")
        return@get
    }
        val response = userDataSource.getResultsByType(login, AchieveType.valueOf(type))
        call.respond(response)
    }
}

fun Route.getAllResults() {
    post(ROUTE_RESULTS) {
        val request =
            kotlin.runCatching { call.receiveNullable<GetAllResultsRequest>() }.getOrNull() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, "Missing request parameter")
                return@post
            }
        val response = userDataSource.getAllResults(request)
        call.respond(response)
    }
}


// testing getting [userId] from token
private fun PipelineContext<Unit, ApplicationCall>.getUserId(): String? {
    val principal = call.principal<JWTPrincipal>()
    return principal?.getClaim(Constants.USER_ID, String::class)
}