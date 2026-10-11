package com.focusly.app.domain.model

data class Routine(
    val id: Long = 0,
    val name: String,
    val timeFormatted: String,
    val totalMinutes: Int,
    val steps: List<String>,
    val isActive: Boolean = true
)
