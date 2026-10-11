package com.focusly.app.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusly.app.data.local.preferences.UserPreferences
import com.focusly.app.domain.model.Achievement
import com.focusly.app.domain.repository.AchievementRepository
import com.focusly.app.domain.repository.FocusRepository
import com.focusly.app.domain.repository.PlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val userName: String = "Vikas",
    val focusEnergy: Int = 25,
    val totalFocusMinutes: Int = 260,
    val streakDays: Int = 7,
    val level: Int = 3,
    val achievements: List<Achievement> = emptyList()
)

class ProfileViewModel(
    private val focusRepository: FocusRepository,
    private val plantRepository: PlantRepository,
    private val achievementRepository: AchievementRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            combine(
                userPreferences.userName,
                userPreferences.focusEnergy,
                focusRepository.getTotalFocusMinutes(),
                achievementRepository.getAllAchievements()
            ) { name, energy, minutes, achievements ->
                _uiState.update {
                    it.copy(
                        userName = name,
                        focusEnergy = energy,
                        totalFocusMinutes = minutes,
                        achievements = achievements
                    )
                }
            }.collect {}
        }
    }
}
