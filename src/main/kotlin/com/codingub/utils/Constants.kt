package com.codingub.utils

object Constants {

    const val MONGO_DB_NAME = "belarusian-history"
    val MONGO_PW: String = System.getenv("MONGO_PW")

    const val USER_ID = "userId"
    object EndPoints{

        const val ROUTE_USER = "users"
        const val ROUTE_SIGNUP = "users/signup"
        const val ROUTE_SIGNIN = "users/signin"
        const val ROUTE_AUTHENTICATE = "users/authenticate"
        const val ROUTE_SECRET = "users/secret"

        const val ROUTE_ROLE_CHANGE = "users/changerole"

        const val ROUTE_TICKET = "tickets"
        const val ROUTE_INSERT_TICKET = "tickets/insert"
        const val ROUTE_RESET_TICKET = "tickets/reset"

        const val ROUTE_TQ = "tq"
        const val ROUTE_INSERT_TQ = "tq/insert"
        const val ROUTE_RESET_TQ = "tq/reset"

        const val ROUTE_PQ = "pq"
        const val ROUTE_INSERT_PQ = "pq/insert"
        const val ROUTE_RESET_PQ = "pq/reset"

        const val ROUTE_ACHIEVE = "achieves"

        const val ROUTE_GROUP = "groups"
        const val ROUTE_INSERT_GROUP = "groups/insert"
        const val ROUTE_RESET_GROUP = "groups/reset"
        const val ROUTE_INVITE_USER_GROUP = "groups/users/invite"
        const val ROUTE_DELETE_USER_GROUP = "groups/users/reset"

        const val ROUTE_EVENTS = "events"
    }
}