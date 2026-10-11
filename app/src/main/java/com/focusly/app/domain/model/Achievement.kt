package com.focusly.app.domain.model

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    val progress: Int,
    val maxProgress: Int,
    val iconName: String
)
