package com.codingub.routes

import com.codingub.data.repositories.AchieveDataRepository
import com.codingub.data.requests.GetTqRequest
import com.codingub.utils.Constants
import com.codingub.utils.Constants.EndPoints.ROUTE_ACHIEVE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
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