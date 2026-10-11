package com.focusly.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.focusly.app.data.local.dao.AchievementDao
import com.focusly.app.data.local.dao.BlockedAppDao
import com.focusly.app.data.local.dao.FocusSessionDao
import com.focusly.app.data.local.dao.HabitDao
import com.focusly.app.data.local.dao.PlantDao
import com.focusly.app.data.local.dao.RoutineDao
import com.focusly.app.data.local.entity.AchievementEntity
import com.focusly.app.data.local.entity.BlockedAppEntity
import com.focusly.app.data.local.entity.FocusSessionEntity
import com.focusly.app.data.local.entity.HabitEntity
import com.focusly.app.data.local.entity.PlantEntity
import com.focusly.app.data.local.entity.RoutineEntity

@Database(
    entities = [
        FocusSessionEntity::class,
        PlantEntity::class,
        RoutineEntity::class,
        HabitEntity::class,
        BlockedAppEntity::class,
        AchievementEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun focusSessionDao(): FocusSessionDao
    abstract fun plantDao(): PlantDao
    abstract fun routineDao(): RoutineDao
    abstract fun habitDao(): HabitDao
    abstract fun blockedAppDao(): BlockedAppDao
    abstract fun achievementDao(): AchievementDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "focusly_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
