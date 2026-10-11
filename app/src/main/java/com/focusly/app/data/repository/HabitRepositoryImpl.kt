package com.focusly.app.data.repository

import com.focusly.app.data.local.dao.HabitDao
import com.focusly.app.data.local.entity.HabitEntity
import com.focusly.app.domain.model.Habit
import com.focusly.app.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HabitRepositoryImpl(
    private val habitDao: HabitDao
) : HabitRepository {

    override fun getAllHabits(): Flow<List<Habit>> {
        return habitDao.getAllHabits().map { entities ->
            entities.map {
                Habit(
                    id = it.id,
                    title = it.title,
                    streakDays = it.streakDays,
                    weeklyGoal = it.weeklyGoal,
                    completedThisWeek = it.completedThisWeek,
                    isCompletedToday = it.isCompletedToday
                )
            }
        }
    }

    override suspend fun saveHabit(habit: Habit) {
        val entity = HabitEntity(
            id = habit.id,
            title = habit.title,
            streakDays = habit.streakDays,
            weeklyGoal = habit.weeklyGoal,
            completedThisWeek = habit.completedThisWeek,
            isCompletedToday = habit.isCompletedToday
        )
        habitDao.insertHabits(listOf(entity))
    }
}
