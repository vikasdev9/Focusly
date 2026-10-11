package com.focusly.app.domain.model

data class FocusSession(
    val id: Long = 0,
    val durationMinutes: Int,
    val energyEarned: Int,
    val plantName: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isCompleted: Boolean = true,
    val mode: String = "Focus"
)
