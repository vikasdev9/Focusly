package com.focusly.app.data.repository

import com.focusly.app.data.local.dao.RoutineDao
import com.focusly.app.data.local.entity.RoutineEntity
import com.focusly.app.domain.model.Routine
import com.focusly.app.domain.repository.RoutineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoutineRepositoryImpl(
    private val routineDao: RoutineDao
) : RoutineRepository {

    override fun getAllRoutines(): Flow<List<Routine>> {
        return routineDao.getAllRoutines().map { entities ->
            entities.map {
                Routine(
                    id = it.id,
                    name = it.name,
                    timeFormatted = it.timeFormatted,
                    totalMinutes = it.totalMinutes,
                    steps = it.stepsRaw.split(",").map { s -> s.trim() },
                    isActive = it.isActive
                )
            }
        }
    }

    override suspend fun saveRoutine(routine: Routine) {
        val entity = RoutineEntity(
            id = routine.id,
            name = routine.name,
            timeFormatted = routine.timeFormatted,
            totalMinutes = routine.totalMinutes,
            stepsRaw = routine.steps.joinToString(","),
            isActive = routine.isActive
        )
        routineDao.insertRoutines(listOf(entity))
    }
}
