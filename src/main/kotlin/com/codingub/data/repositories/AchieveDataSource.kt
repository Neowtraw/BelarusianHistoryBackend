package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.responses.AchieveResponse
import com.codingub.sdk.AchieveType

interface AchieveDataRepository{
    /*
        User
     */
    suspend fun getAllAchieves() : AchieveResponse
    suspend fun getTypeAchieves(type: AchieveType) : AchieveResponse

}

class AchieveDataRepositoryImpl(
    val database: HistoryDatabase
) : AchieveDataRepository{

    override suspend fun getAllAchieves(): AchieveResponse {
        return database.getAllAchieves()
    }

    override suspend fun getTypeAchieves(type: AchieveType): AchieveResponse {
        return database.getTypeAchieves(type)
    }
}

