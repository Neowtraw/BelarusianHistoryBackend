package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.responses.AchieveResponse

interface AchieveDataRepository{
    /*
        User
     */
    suspend fun getAllAchieves() : AchieveResponse

}

class AchieveDataRepositoryImpl constructor(
    val database: HistoryDatabase
) : AchieveDataRepository{

    override suspend fun getAllAchieves(): AchieveResponse {
        return database.getAllAchieves()
    }
}