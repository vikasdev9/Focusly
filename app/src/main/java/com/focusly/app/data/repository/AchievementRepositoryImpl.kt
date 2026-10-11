package com.focusly.app.data.repository

import com.focusly.app.data.local.dao.AchievementDao
import com.focusly.app.domain.model.Achievement
import com.focusly.app.domain.repository.AchievementRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AchievementRepositoryImpl(
    private val achievementDao: AchievementDao
) : AchievementRepository {

    override fun getAllAchievements(): Flow<List<Achievement>> {
        return achievementDao.getAllAchievements().map { entities ->
            entities.map {
                Achievement(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    isUnlocked = it.isUnlocked,
                    progress = it.progress,
                    maxProgress = it.maxProgress,
                    iconName = it.iconName
                )
            }
        }
    }
}
