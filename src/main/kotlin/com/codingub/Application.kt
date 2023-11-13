package com.codingub

import com.codingub.di.koinModule
import com.codingub.plugins.*
import com.codingub.security.token.TokenConfig
import com.codingub.utils.Constants
import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.core.context.GlobalContext
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>): Unit =
    EngineMain.main(args)


@Suppress("unused")
fun Application.module() {

    val database = KMongo.createClient(
        connectionString = "mongodb+srv://neowtraw:${Constants.MONGO_PW}@historycluster.xg5enip.mongodb.net/${Constants.MONGO_DB_NAME}?retryWrites=true&w=majority"

    ).coroutine
        .getDatabase(Constants.MONGO_DB_NAME)

    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )

    //using koin
    GlobalContext.startKoin {
        modules(koinModule)
    }

    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
    configureRouting(tokenConfig)
}





