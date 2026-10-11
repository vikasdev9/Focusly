package com.focusly.app.presentation.blocking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusly.app.domain.model.BlockedApp
import com.focusly.app.domain.repository.AppBlockingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BlockingUiState(
    val apps: List<BlockedApp> = emptyList(),
    val totalBlocked: Int = 0
)

class BlockingViewModel(
    private val appBlockingRepository: AppBlockingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BlockingUiState())
    val uiState: StateFlow<BlockingUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            appBlockingRepository.getAllBlockedApps().collect { apps ->
                _uiState.update {
                    it.copy(
                        apps = apps,
                        totalBlocked = apps.count { app -> app.isBlocked }
                    )
                }
            }
        }
    }

    fun toggleAppBlock(packageName: String, isBlocked: Boolean) {
        viewModelScope.launch {
            appBlockingRepository.updateBlockedStatus(packageName, isBlocked)
        }
    }
}
