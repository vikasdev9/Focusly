package com.focusly.app.domain.repository

import com.focusly.app.domain.model.BlockedApp
import kotlinx.coroutines.flow.Flow

interface AppBlockingRepository {
    fun getAllBlockedApps(): Flow<List<BlockedApp>>
    suspend fun updateBlockedStatus(packageName: String, isBlocked: Boolean)
}
