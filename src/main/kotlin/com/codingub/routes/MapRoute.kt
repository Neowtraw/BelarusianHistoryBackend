package com.codingub.routes

import com.codingub.data.repositories.MapRepository
import com.codingub.data.requests.LabelRequest
import com.codingub.utils.Constants.EndPoints.ROUTE_ADD_LABEL
import com.codingub.utils.Constants.EndPoints.ROUTE_DELETE_MAP
import com.codingub.utils.Constants.EndPoints.ROUTE_MAP
import com.codingub.utils.Constants.EndPoints.ROUTE_MAP_TYPE
import com.codingub.utils.Constants.EndPoints.ROUTE_UPDATE_LABEL
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent

private val mapDataSource: MapRepository by KoinJavaComponent.inject(MapRepository::class.java)

fun Route.addLabelOnMap(){
    post(ROUTE_ADD_LABEL) {
        val request = kotlin.runCatching { call.receiveNullable<LabelRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        call.respond(
            mapDataSource.addLabelOnMap(request)
        )
    }
}

fun Route.updateLabelOnMap(){
    post(ROUTE_UPDATE_LABEL) {
        val request = kotlin.runCatching { call.receiveNullable<LabelRequest>() }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        call.respond(
            mapDataSource.updateLabelOnMap(request)
        )
    }
}

fun Route.deleteLabelFromMap(){
    delete("$ROUTE_DELETE_MAP/") {
        val id = kotlin.runCatching { call.request.queryParameters["id"] }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }

        call.respond(
            mapDataSource.deleteLabelFromMap(id)
        )
    }
}

fun Route.getMapTypes(){
    get(ROUTE_MAP_TYPE) {
        call.respond(
            mapDataSource.getMapTypes()
        )
    }
}

fun Route.getMap(){
    get("$ROUTE_MAP/") {
        val periodId = kotlin.runCatching { call.request.queryParameters["periodId"] }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }

        call.respond(
            mapDataSource.getMap(periodId)
        )
    }
}
