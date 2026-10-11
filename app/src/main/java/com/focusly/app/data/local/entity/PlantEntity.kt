package com.focusly.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val growthEnergyRequired: Int,
    val isSelected: Boolean,
    val isUnlocked: Boolean,
    val countGrown: Int
)
