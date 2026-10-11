package com.focusly.app.domain.repository

import com.focusly.app.domain.model.DailyStat
import com.focusly.app.domain.model.FocusSession
import kotlinx.coroutines.flow.Flow

interface FocusRepository {
    fun getAllSessions(): Flow<List<FocusSession>>
    fun getTotalFocusMinutes(): Flow<Int>
    fun getCompletedSessionCount(): Flow<Int>
    fun getWeeklyStats(): Flow<List<DailyStat>>
    suspend fun saveSession(session: FocusSession): Long
}
