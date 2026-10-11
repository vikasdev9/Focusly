package com.focusly.app.data.local

import com.focusly.app.data.local.entity.AchievementEntity
import com.focusly.app.data.local.entity.BlockedAppEntity
import com.focusly.app.data.local.entity.FocusSessionEntity
import com.focusly.app.data.local.entity.HabitEntity
import com.focusly.app.data.local.entity.PlantEntity
import com.focusly.app.data.local.entity.RoutineEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DemoDataInitializer {

    suspend fun populateInitialData(db: AppDatabase) = withContext(Dispatchers.IO) {
        // Populate Plants
        val plants = listOf(
            PlantEntity("cedar", "Cedar", "Strong and resilient evergreen tree", 25, isSelected = true, isUnlocked = true, countGrown = 3),
            PlantEntity("plumeria", "Plumeria", "Fragrant tropical flower", 30, isSelected = false, isUnlocked = true, countGrown = 1),
            PlantEntity("chili", "Chili Pepper", "Spicy and vibrant focus booster", 20, isSelected = false, isUnlocked = true, countGrown = 2),
            PlantEntity("dusty", "Dusty Miller", "Soft silvery foliage plant", 40, isSelected = false, isUnlocked = true, countGrown = 0)
        )
        db.plantDao().insertPlants(plants)

        // Populate Blocked Apps
        val apps = listOf(
            BlockedAppEntity("com.instagram.android", "Instagram", "42m today", "Social", isBlocked = true),
            BlockedAppEntity("com.google.android.youtube", "YouTube", "1h 12m today", "Entertainment", isBlocked = true),
            BlockedAppEntity("com.whatsapp", "WhatsApp", "38m today", "Communication", isBlocked = false),
            BlockedAppEntity("com.twitter.android", "X / Twitter", "25m today", "News", isBlocked = true),
            BlockedAppEntity("com.zhiliaoapp.musically", "TikTok", "50m today", "Entertainment", isBlocked = true)
        )
        db.blockedAppDao().insertBlockedApps(apps)

        // Populate Routines
        val routines = listOf(
            RoutineEntity(1, "Morning Focus", "07:30 AM", 60, "Meditation, Deep Focus, Short Break", isActive = true),
            RoutineEntity(2, "Afternoon Deep Work", "02:00 PM", 90, "Code Review, Feature Build, Chill", isActive = true),
            RoutineEntity(3, "Evening Wind-down", "09:00 PM", 30, "Reading, Journaling, Reflection", isActive = false)
        )
        db.routineDao().insertRoutines(routines)

        // Populate Habits
        val habits = listOf(
            HabitEntity(1, "Read 20 minutes", 5, 7, 5, isCompletedToday = true),
            HabitEntity(2, "Study Kotlin / Android", 12, 5, 4, isCompletedToday = true),
            HabitEntity(3, "No social media before 10 AM", 8, 7, 6, isCompletedToday = true),
            HabitEntity(4, "Daily Meditation", 3, 7, 3, isCompletedToday = false)
        )
        db.habitDao().insertHabits(habits)

        // Populate Achievements
        val achievements = listOf(
            AchievementEntity("first_focus", "First Focus", "Completed your very first 25-minute focus session", isUnlocked = true, 1, 1, "ic_leaf"),
            AchievementEntity("hour_focus", "Deep Thinker", "Focused for over 10 hours total", isUnlocked = true, 10, 10, "ic_timer"),
            AchievementEntity("streak_7", "7 Day Streak", "Maintained focus for 7 consecutive days", isUnlocked = true, 7, 7, "ic_sun"),
            AchievementEntity("green_thumb", "Botanic Master", "Grown 5 plants in your focus garden", isUnlocked = false, 3, 5, "ic_plant")
        )
        db.achievementDao().insertAchievements(achievements)

        // Populate Recent Sessions
        val now = System.currentTimeMillis()
        val sessions = listOf(
            FocusSessionEntity(1, 25, 25, "Cedar", now - 3600000, isCompleted = true, mode = "Focus"),
            FocusSessionEntity(2, 45, 45, "Plumeria", now - 86400000, isCompleted = true, mode = "Deep Focus"),
            FocusSessionEntity(3, 25, 25, "Chili Pepper", now - 172800000, isCompleted = true, mode = "Pomodoro")
        )
        for (session in sessions) {
            db.focusSessionDao().insertSession(session)
        }
    }
}
