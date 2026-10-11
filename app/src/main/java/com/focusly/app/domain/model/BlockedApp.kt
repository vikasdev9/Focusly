package com.focusly.app.domain.model

data class BlockedApp(
    val packageName: String,
    val appName: String,
    val usageFormatted: String,
    val category: String,
    val isBlocked: Boolean
)
