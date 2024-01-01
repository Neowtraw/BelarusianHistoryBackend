package com.codingub.data.requests

import com.codingub.data.models.achieves.AchieveDto
import com.codingub.sdk.AchieveType

/** Adding new result to user **/
data class AddResultRequest(
    val login: String,
    val answer: Any?,
    val achieve: AchieveDto
)

/** Get all completed results by type **/
data class GetResultsByTypeRequest(
    val login: String,
    val type: AchieveType
)

/** Get all completed results **/
data class GetAllResultsRequest(
    val login: String
)

/** Reset all results for user with this type **/
data class ResetResultsWithTypeRequest(
    val login: String,
    val type: AchieveType
)
