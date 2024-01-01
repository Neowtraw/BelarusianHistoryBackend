package com.codingub.routes

import com.codingub.data.repositories.AchieveDataRepository
import com.codingub.sdk.AchieveType
import com.codingub.utils.Constants.EndPoints.ROUTE_ACHIEVE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.java.KoinJavaComponent

private val achieveDataSource: AchieveDataRepository by KoinJavaComponent.inject(AchieveDataRepository::class.java)

fun Route.getAllAchieves(){
    get(ROUTE_ACHIEVE){
        call.respond(
            achieveDataSource.getAllAchieves()
        )
    }
}

fun Route.getTypeAchieves() {
    get("$ROUTE_ACHIEVE/") {
        val request = kotlin.runCatching {  call.request.queryParameters["type"] }.getOrNull() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "Missing achieves type parameter")
            return@get
        }
        call.respond(
            achieveDataSource.getTypeAchieves(AchieveType.valueOf(request))
        )
        return@get
    }
}