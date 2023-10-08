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

    }
}