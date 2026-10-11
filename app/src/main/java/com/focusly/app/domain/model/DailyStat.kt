package com.focusly.app.domain.model

data class DailyStat(
    val dayOfWeek: String, // Mon, Tue, Wed...
    val focusMinutes: Int,
    val normalizedRatio: Float // 0.0 to 1.0 for chart display
)
