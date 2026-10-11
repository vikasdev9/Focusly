package com.focusly.app.presentation.focus

data class FocusUiState(
    val focusMinutes: Int = 25,
    val breakMinutes: Int = 5,
    val totalSeconds: Int = 1500,
    val remainingSeconds: Int = 1500,
    val isRunning: Boolean = false,
    val isPaused: Boolean = false,
    val mode: String = "Focus",
    val selectedPlantName: String = "Cedar",
    val activityName: String = "Grow your Cedar",
    val isCompleted: Boolean = false
)
