package com.focusly.app.data.repository

import com.focusly.app.data.local.dao.BlockedAppDao
import com.focusly.app.domain.model.BlockedApp
import com.focusly.app.domain.repository.AppBlockingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppBlockingRepositoryImpl(
    private val blockedAppDao: BlockedAppDao
) : AppBlockingRepository {

    override fun getAllBlockedApps(): Flow<List<BlockedApp>> {
        return blockedAppDao.getAllBlockedApps().map { entities ->
            entities.map {
                BlockedApp(
                    packageName = it.packageName,
                    appName = it.appName,
                    usageFormatted = it.usageFormatted,
                    category = it.category,
                    isBlocked = it.isBlocked
                )
            }
        }
    }

    override suspend fun updateBlockedStatus(packageName: String, isBlocked: Boolean) {
        blockedAppDao.updateBlockedStatus(packageName, isBlocked)
    }
}
