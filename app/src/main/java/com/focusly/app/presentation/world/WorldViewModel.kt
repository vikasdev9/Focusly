package com.focusly.app.presentation.world

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusly.app.data.local.preferences.UserPreferences
import com.focusly.app.domain.model.Plant
import com.focusly.app.domain.repository.PlantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WorldUiState(
    val focusEnergy: Int = 25,
    val plants: List<Plant> = emptyList(),
    val totalGrown: Int = 0
)

class WorldViewModel(
    private val plantRepository: PlantRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorldUiState())
    val uiState: StateFlow<WorldUiState> = _uiState.asStateFlow()

    init {
        loadWorldData()
    }

    private fun loadWorldData() {
        viewModelScope.launch {
            combine(
                userPreferences.focusEnergy,
                plantRepository.getAllPlants()
            ) { energy, plants ->
                _uiState.update {
                    it.copy(
                        focusEnergy = energy,
                        plants = plants,
                        totalGrown = plants.sumOf { p -> p.countGrown }
                    )
                }
            }.collect {}
        }
    }

    fun selectPlant(plantId: String) {
        viewModelScope.launch {
            plantRepository.selectPlant(plantId)
        }
    }
}
