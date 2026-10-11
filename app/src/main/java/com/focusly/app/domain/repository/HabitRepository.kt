package com.focusly.app.domain.repository

import com.focusly.app.domain.model.Habit
import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getAllHabits(): Flow<List<Habit>>
    suspend fun saveHabit(habit: Habit)
}
