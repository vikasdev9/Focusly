package com.focusly.app.domain.model

enum class PaceType {
    LOW_ENERGY,
    STEADY,
    FEELING_BRIGHT
}

data class FeelingPace(
    val type: PaceType,
    val title: String,
    val description: String,
    val defaultMinutes: Int,
    val isSelected: Boolean = false
)
