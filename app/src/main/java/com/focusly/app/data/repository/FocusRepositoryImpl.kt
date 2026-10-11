package com.focusly.app.data.repository

import com.focusly.app.data.local.dao.FocusSessionDao
import com.focusly.app.data.local.entity.FocusSessionEntity
import com.focusly.app.domain.model.DailyStat
import com.focusly.app.domain.model.FocusSession
import com.focusly.app.domain.repository.FocusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FocusRepositoryImpl(
    private val focusSessionDao: FocusSessionDao
) : FocusRepository {

    override fun getAllSessions(): Flow<List<FocusSession>> {
        return focusSessionDao.getAllSessions().map { entities ->
            entities.map { entity ->
                FocusSession(
                    id = entity.id,
                    durationMinutes = entity.durationMinutes,
                    energyEarned = entity.energyEarned,
                    plantName = entity.plantName,
                    timestamp = entity.timestamp,
                    isCompleted = entity.isCompleted,
                    mode = entity.mode
                )
            }
        }
    }

    override fun getTotalFocusMinutes(): Flow<Int> {
        return focusSessionDao.getTotalFocusMinutes().map { it ?: 0 }
    }

    override fun getCompletedSessionCount(): Flow<Int> {
        return focusSessionDao.getCompletedSessionCount()
    }

    override fun getWeeklyStats(): Flow<List<DailyStat>> {
        val stats = listOf(
            DailyStat("MON", 80, 0.45f),
            DailyStat("TUE", 120, 0.85f),
            DailyStat("WED", 105, 0.35f),
            DailyStat("THU", 180, 0.65f),
            DailyStat("FRI", 135, 0.95f),
            DailyStat("SAT", 90, 0.70f),
            DailyStat("SUN", 30, 0.20f)
        )
        return flowOf(stats)
    }

    override suspend fun saveSession(session: FocusSession): Long {
        val entity = FocusSessionEntity(
            id = session.id,
            durationMinutes = session.durationMinutes,
            energyEarned = session.energyEarned,
            plantName = session.plantName,
            timestamp = session.timestamp,
            isCompleted = session.isCompleted,
            mode = session.mode
        )
        return focusSessionDao.insertSession(entity)
    }
}
