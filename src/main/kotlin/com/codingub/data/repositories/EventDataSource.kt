package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.responses.EventResponse

interface EventDataRepository {
    suspend fun getAllEvents(): EventResponse
}

class EventDataRepositoryImpl(
    private val database: HistoryDatabase,
) : EventDataRepository {
    override suspend fun getAllEvents(): EventResponse = database.getAllEvents()
}