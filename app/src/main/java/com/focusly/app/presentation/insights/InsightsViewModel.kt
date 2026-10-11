package com.focusly.app.presentation.insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusly.app.domain.repository.AppBlockingRepository
import com.focusly.app.domain.repository.FocusRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class InsightsUiState(
    val totalFocusMinutes: Int = 260,
    val totalSessions: Int = 8,
    val averageSessionMinutes: Int = 32,
    val aiInsight: String = "Your focus is strongest between 9 AM and 11 AM.",
    val topBlockedApp: String = "Instagram (42m blocked)"
)

class InsightsViewModel(
    private val focusRepository: FocusRepository,
    private val appBlockingRepository: AppBlockingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InsightsUiState())
    val uiState: StateFlow<InsightsUiState> = _uiState.asStateFlow()

    init {
        loadInsights()
    }

    private fun loadInsights() {
        viewModelScope.launch {
            combine(
                focusRepository.getTotalFocusMinutes(),
                focusRepository.getCompletedSessionCount(),
                appBlockingRepository.getAllBlockedApps()
            ) { minutes, count, apps ->
                val avg = if (count > 0) minutes / count else 0
                val blockedCount = apps.count { it.isBlocked }
                _uiState.update {
                    it.copy(
                        totalFocusMinutes = minutes,
                        totalSessions = count,
                        averageSessionMinutes = avg,
                        topBlockedApp = "$blockedCount apps blocked today"
                    )
                }
            }.collect {}
        }
    }
}
