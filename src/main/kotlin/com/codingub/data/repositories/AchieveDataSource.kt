package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.responses.AchieveResponse
import com.codingub.data.responses.ServerResponse

interface AchieveDataRepository{
    /*
        User
     */
    suspend fun getAllAchieves() : ServerResponse<AchieveResponse>

}

class AchieveDataRepositoryImpl constructor(
    val database: HistoryDatabase
) : AchieveDataRepository{

    override suspend fun getAllAchieves(): ServerResponse<AchieveResponse> {
        return database.getAllAchieves()
    }
}