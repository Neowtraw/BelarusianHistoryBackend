package com.codingub.utils

object Constants {

    const val MONGO_DB_NAME = "belarusian-history"
    val MONGO_PW: String = System.getenv("MONGO_PW")

    const val USER_ID = "userId"
    object EndPoints{

        const val ROUTE_SIGNUP = "signup"
        const val ROUTE_SIGNIN = "signin"
        const val ROUTE_AUTHENTICATE = "authenticate"
        const val ROUTE_SECRET = "secret"

        const val ROUTE_RESET_TICKET = "resetticket"
        const val ROUTE_INSERT_TICKET = "insertticket"
        const val ROUTE_TICKETS = "getalltickets"

        const val ROUTE_INSERT_TQ = "inserttq"
        const val ROUTE_RESET_TQ = "resettq"
        const val ROUTE_TQ = "gettq"

    }
}