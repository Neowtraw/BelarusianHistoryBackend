package com.codingub.data.responses

data class ServerResponse<T>(
    var data: T? = null,
    var message: String = "",
    var status: Int
)

data class ErrorResponse(
    var message: String = "",
    var status: Int
)