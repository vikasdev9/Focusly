package com.focusly.app.data.repository

import com.focusly.app.data.local.dao.PlantDao
import com.focusly.app.domain.model.Plant
import com.focusly.app.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PlantRepositoryImpl(
    private val plantDao: PlantDao
) : PlantRepository {

    override fun getAllPlants(): Flow<List<Plant>> {
        return plantDao.getAllPlants().map { entities ->
            entities.map {
                Plant(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    growthEnergyRequired = it.growthEnergyRequired,
                    isSelected = it.isSelected,
                    isUnlocked = it.isUnlocked,
                    countGrown = it.countGrown
                )
            }
        }
    }

    override suspend fun selectPlant(plantId: String) {
        plantDao.selectPlant(plantId)
    }
}
