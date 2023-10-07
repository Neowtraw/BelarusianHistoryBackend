package com.codingub

import com.codingub.di.koinModule
import com.codingub.plugins.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.core.context.GlobalContext

fun main(args: Array<String>): Unit =
    EngineMain.main(args)


@Suppress("unused")
fun Application.module() {


    //using koin
    GlobalContext.startKoin {
        modules(koinModule)
    }


    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}






