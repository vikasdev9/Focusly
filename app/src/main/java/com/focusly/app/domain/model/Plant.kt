package com.focusly.app.domain.model

data class Plant(
    val id: String,
    val name: String,
    val description: String,
    val growthEnergyRequired: Int,
    val isSelected: Boolean = false,
    val isUnlocked: Boolean = true,
    val countGrown: Int = 0
)
