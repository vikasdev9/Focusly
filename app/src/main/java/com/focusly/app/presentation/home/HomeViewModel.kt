package com.focusly.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusly.app.data.local.preferences.UserPreferences
import com.focusly.app.domain.model.PaceType
import com.focusly.app.domain.repository.FocusRepository
import com.focusly.app.domain.repository.PlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val focusRepository: FocusRepository,
    private val plantRepository: PlantRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            combine(
                userPreferences.userName,
                userPreferences.focusEnergy,
                plantRepository.getAllPlants(),
                focusRepository.getWeeklyStats()
            ) { name, energy, plants, weeklyStats ->
                val selectedPlant = plants.find { it.isSelected } ?: plants.firstOrNull()
                _uiState.update { currentState ->
                    currentState.copy(
                        userName = name,
                        focusEnergy = energy,
                        plants = plants,
                        selectedPlant = selectedPlant,
                        totalGrownPlants = plants.sumOf { it.countGrown },
                        weeklyStats = weeklyStats
                    )
                }
            }.collect {}
        }
    }

    fun selectFeelingPace(paceType: PaceType) {
        _uiState.update { currentState ->
            val updatedPaces = currentState.feelingPaces.map { pace ->
                pace.copy(isSelected = pace.type == paceType)
            }
            val selectedPace = updatedPaces.find { it.isSelected }
            val newMinutes = selectedPace?.defaultMinutes ?: 25
            currentState.copy(feelingPaces = updatedPaces, calmMinutes = newMinutes)
        }
    }

    fun adjustCalmMinutes(delta: Int) {
        _uiState.update { currentState ->
            val updatedMin = (currentState.calmMinutes + delta).coerceIn(5, 120)
            currentState.copy(calmMinutes = updatedMin)
        }
    }

    fun selectPlant(plantId: String) {
        viewModelScope.launch {
            plantRepository.selectPlant(plantId)
        }
    }
}
