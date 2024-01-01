package com.codingub.routes

import com.codingub.data.repositories.EventDataRepository
import com.codingub.utils.Constants.EndPoints.ROUTE_EVENTS
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent

private val eventDataSource: EventDataRepository by KoinJavaComponent.inject(EventDataRepository::class.java)

fun Route.getAllEvents() {
    get(ROUTE_EVENTS) {
        call.respond(
            eventDataSource.getAllEvents()
        )
    }
}