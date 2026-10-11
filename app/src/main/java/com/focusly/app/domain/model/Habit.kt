package com.focusly.app.domain.model

data class Habit(
    val id: Long = 0,
    val title: String,
    val streakDays: Int,
    val weeklyGoal: Int,
    val completedThisWeek: Int,
    val isCompletedToday: Boolean
)
