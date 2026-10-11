package com.focusly.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val streakDays: Int,
    val weeklyGoal: Int,
    val completedThisWeek: Int,
    val isCompletedToday: Boolean
)
