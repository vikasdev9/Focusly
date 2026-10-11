package com.focusly.app.presentation.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusly.app.data.local.preferences.UserPreferences
import com.focusly.app.domain.model.FocusSession
import com.focusly.app.domain.repository.FocusRepository
import com.focusly.app.domain.repository.PlantRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FocusViewModel(
    private val focusRepository: FocusRepository,
    private val plantRepository: PlantRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(FocusUiState())
    val uiState: StateFlow<FocusUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null

    fun setFocusMinutes(minutes: Int) {
        val totalSec = minutes * 60
        _uiState.update {
            it.copy(
                focusMinutes = minutes,
                totalSeconds = totalSec,
                remainingSeconds = totalSec,
                isRunning = false,
                isPaused = false
            )
        }
    }

    fun setBreakMinutes(minutes: Int) {
        _uiState.update {
            it.copy(breakMinutes = minutes)
        }
    }

    fun setActivityName(name: String) {
        _uiState.update {
            it.copy(activityName = name)
        }
    }

    fun togglePlayPause() {
        val state = _uiState.value
        if (state.isRunning) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    fun startTimer() {
        if (_uiState.value.isRunning) return
        _uiState.update { it.copy(isRunning = true, isPaused = false) }

        timerJob = viewModelScope.launch {
            while (_uiState.value.remainingSeconds > 0 && _uiState.value.isRunning) {
                delay(1000)
                _uiState.update {
                    val next = it.remainingSeconds - 1
                    if (next <= 0) {
                        it.copy(remainingSeconds = 0, isRunning = false, isCompleted = true)
                    } else {
                        it.copy(remainingSeconds = next)
                    }
                }
            }
            if (_uiState.value.isCompleted) {
                onSessionCompleted()
            }
        }
    }

    fun pauseTimer() {
        timerJob?.cancel()
        _uiState.update { it.copy(isRunning = false, isPaused = true) }
    }

    fun addFiveMinutes() {
        _uiState.update {
            val updated = it.remainingSeconds + 300
            val updatedTotal = it.totalSeconds + 300
            it.copy(remainingSeconds = updated, totalSeconds = updatedTotal)
        }
    }

    fun finishSession() {
        timerJob?.cancel()
        onSessionCompleted()
    }

    private fun onSessionCompleted() {
        viewModelScope.launch {
            val minutesFocused = (_uiState.value.totalSeconds - _uiState.value.remainingSeconds) / 60
            if (minutesFocused > 0) {
                val energyEarned = minutesFocused
                val session = FocusSession(
                    durationMinutes = minutesFocused,
                    energyEarned = energyEarned,
                    plantName = _uiState.value.selectedPlantName,
                    mode = _uiState.value.mode
                )
                focusRepository.saveSession(session)
                userPreferences.updateFocusEnergy(energyEarned)
            }
            _uiState.update { it.copy(isCompleted = true, isRunning = false) }
        }
    }
}
