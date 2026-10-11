package com.focusly.app.domain.repository

import com.focusly.app.domain.model.Routine
import kotlinx.coroutines.flow.Flow

interface RoutineRepository {
    fun getAllRoutines(): Flow<List<Routine>>
    suspend fun saveRoutine(routine: Routine)
}
