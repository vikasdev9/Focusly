package com.focusly.app.domain.repository

import com.focusly.app.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    fun getAllPlants(): Flow<List<Plant>>
    suspend fun selectPlant(plantId: String)
}
