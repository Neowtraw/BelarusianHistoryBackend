package com.codingub.routes

import com.codingub.data.repositories.UserDataRepository
import com.codingub.data.requests.RoleRequest
import com.codingub.utils.Constants.EndPoints.ROUTE_ROLE_CHANGE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent

private val userDataSource: UserDataRepository by KoinJavaComponent.inject(UserDataRepository::class.java)
fun Route.changeRoleByLogin(){
    post(ROUTE_ROLE_CHANGE){
        val request = kotlin.runCatching { call.receiveNullable<RoleRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val response = userDataSource.changeRoleByLogin(login = request.login, accessLevel = request.accessLevel)
        call.respond(response)
    }
}