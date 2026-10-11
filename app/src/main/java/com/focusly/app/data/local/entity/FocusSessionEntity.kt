package com.focusly.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val durationMinutes: Int,
    val energyEarned: Int,
    val plantName: String,
    val timestamp: Long,
    val isCompleted: Boolean,
    val mode: String
)
