package com.focusly.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routines")
data class RoutineEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val timeFormatted: String,
    val totalMinutes: Int,
    val stepsRaw: String, // Comma separated steps
    val isActive: Boolean
)
