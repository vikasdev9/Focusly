package com.focusly.app.presentation.home

import com.focusly.app.domain.model.DailyStat
import com.focusly.app.domain.model.FeelingPace
import com.focusly.app.domain.model.PaceType
import com.focusly.app.domain.model.Plant

data class HomeUiState(
    val userName: String = "Vikas",
    val focusEnergy: Int = 25,
    val dateFormatted: String = "TUE • 15 SEP",
    val selectedPlant: Plant? = Plant("cedar", "Cedar", "Strong evergreen tree", 25, true, true, 0),
    val plants: List<Plant> = emptyList(),
    val feelingPaces: List<FeelingPace> = listOf(
        FeelingPace(PaceType.LOW_ENERGY, "Low energy", "10 min • A gentle start", 10, isSelected = false),
        FeelingPace(PaceType.STEADY, "Steady", "25 min • A balanced rhythm", 25, isSelected = true),
        FeelingPace(PaceType.FEELING_BRIGHT, "Feeling bright", "45 min • Ready to stretch", 45, isSelected = false)
    ),
    val calmMinutes: Int = 25,
    val totalGrownPlants: Int = 0,
    val weeklyStats: List<DailyStat> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
