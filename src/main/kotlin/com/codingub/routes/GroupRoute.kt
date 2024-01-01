package com.codingub.routes

import com.codingub.data.repositories.GroupDataRepository
import com.codingub.data.requests.*
import com.codingub.utils.Constants.EndPoints.ROUTE_DELETE_USER_GROUP
import com.codingub.utils.Constants.EndPoints.ROUTE_GROUP
import com.codingub.utils.Constants.EndPoints.ROUTE_INSERT_GROUP
import com.codingub.utils.Constants.EndPoints.ROUTE_INVITE_USER_GROUP
import com.codingub.utils.Constants.EndPoints.ROUTE_RESET_GROUP
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent


private val groupDataSource: GroupDataRepository by KoinJavaComponent.inject(GroupDataRepository::class.java)

fun Route.createGroup() {
    post(ROUTE_INSERT_GROUP) {
        val request = kotlin.runCatching { call.receiveNullable<CreateGroupRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val wasAcknowledged = groupDataSource.createGroup(request)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, "Group was not created")
            return@post
        } else {
            call.respond(HttpStatusCode.OK, "Group was created")
            return@post
        }
    }

}

fun Route.deleteGroup() {
    post(ROUTE_RESET_GROUP) {
        val request = kotlin.runCatching { call.receiveNullable<GroupRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val wasAcknowledged = groupDataSource.deleteGroup(request)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, "Group was not deleted")
            return@post
        } else {
            call.respond(HttpStatusCode.OK, "Group was deleted")
            return@post
        }
    }

}

fun Route.inviteUserToGroup() {
    post(ROUTE_INVITE_USER_GROUP) {
        val request = kotlin.runCatching { call.receiveNullable<GroupRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val wasAcknowledged = groupDataSource.inviteUserToGroup(request)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, "User was not added")
            return@post
        } else {
            call.respond(HttpStatusCode.OK, "User was added")
            return@post
        }
    }

}

fun Route.deleteUserFromGroup() {
    post(ROUTE_DELETE_USER_GROUP) {
        val request = kotlin.runCatching { call.receiveNullable<GroupRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val wasAcknowledged = groupDataSource.deleteUserFromGroup(request)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, "User was not deleted")
            return@post
        } else {
            call.respond(HttpStatusCode.OK, "User was deleted")
            return@post
        }
    }

}

fun Route.getAllGroups() {
    get("$ROUTE_GROUP/") {
        val request = kotlin.runCatching { call.request.queryParameters["user"] }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "Missing ticket request parameter")
            return@get
        }
        call.respond(
            groupDataSource.getAllGroups(request)
        )
    }

}