package com.codingub.data.repositories

import com.codingub.data.HistoryDatabase
import com.codingub.data.requests.LabelRequest
import com.codingub.data.responses.models.map.Map
import com.codingub.data.responses.models.map.MapType

interface MapRepository {

    suspend fun addLabelOnMap(label: LabelRequest) : Boolean
    suspend fun updateLabelOnMap(label: LabelRequest) : Boolean
    suspend fun deleteLabelFromMap(id: String) : Boolean

    suspend fun getMapTypes(): List<MapType>
    suspend fun getMap(periodId: String) : Map

    /*
        Admin
     */

//    suspend fun addPeriod(request: AddPeriodRequest): Boolean
//    suspend fun deletePeriod(id: String): Boolean
}

class MapRepositoryImpl(
    private val database: HistoryDatabase
): MapRepository {

    override suspend fun addLabelOnMap(label: LabelRequest): Boolean {
        return database.addLabelOnMap(label)
    }

    override suspend fun updateLabelOnMap(label: LabelRequest): Boolean {
        return database.updateLabelOnMap(label)
    }

    override suspend fun deleteLabelFromMap(id: String): Boolean {
        return database.deleteLabelFromMap(id)
    }

    override suspend fun getMapTypes(): List<MapType> {
        return database.getMapTypes()
    }

    override suspend fun getMap(periodId: String): Map {
        return database.getMap(periodId)
    }

//    override suspend fun addPeriod(request: AddPeriodRequest): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun deletePeriod(id: String): Boolean {
//        TODO("Not yet implemented")
//    }
}