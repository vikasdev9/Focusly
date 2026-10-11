package com.focusly.app.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.focusly.app.data.local.AppDatabase
import com.focusly.app.data.local.preferences.UserPreferences
import com.focusly.app.data.repository.AchievementRepositoryImpl
import com.focusly.app.data.repository.AppBlockingRepositoryImpl
import com.focusly.app.data.repository.BillingRepositoryImpl
import com.focusly.app.data.repository.FocusRepositoryImpl
import com.focusly.app.data.repository.HabitRepositoryImpl
import com.focusly.app.data.repository.PlantRepositoryImpl
import com.focusly.app.data.repository.RoutineRepositoryImpl
import com.focusly.app.presentation.achievements.AchievementViewModel
import com.focusly.app.presentation.blocking.BlockingViewModel
import com.focusly.app.presentation.focus.FocusViewModel
import com.focusly.app.presentation.habits.HabitViewModel
import com.focusly.app.presentation.home.HomeViewModel
import com.focusly.app.presentation.insights.InsightsViewModel
import com.focusly.app.presentation.onboarding.OnboardingViewModel
import com.focusly.app.presentation.profile.ProfileViewModel
import com.focusly.app.presentation.routines.RoutineViewModel
import com.focusly.app.presentation.splash.SplashViewModel
import com.focusly.app.presentation.subscription.SubscriptionViewModel
import com.focusly.app.presentation.world.WorldViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    private val db by lazy { AppDatabase.getDatabase(context) }
    private val userPrefs by lazy { UserPreferences(context) }

    private val focusRepo by lazy { FocusRepositoryImpl(db.focusSessionDao()) }
    private val plantRepo by lazy { PlantRepositoryImpl(db.plantDao()) }
    private val routineRepo by lazy { RoutineRepositoryImpl(db.routineDao()) }
    private val habitRepo by lazy { HabitRepositoryImpl(db.habitDao()) }
    private val appBlockingRepo by lazy { AppBlockingRepositoryImpl(db.blockedAppDao()) }
    private val achievementRepo by lazy { AchievementRepositoryImpl(db.achievementDao()) }
    private val billingRepo by lazy { BillingRepositoryImpl() }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(userPrefs) as T
            }
            modelClass.isAssignableFrom(OnboardingViewModel::class.java) -> {
                OnboardingViewModel(userPrefs) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(focusRepo, plantRepo, userPrefs) as T
            }
            modelClass.isAssignableFrom(FocusViewModel::class.java) -> {
                FocusViewModel(focusRepo, plantRepo, userPrefs) as T
            }
            modelClass.isAssignableFrom(InsightsViewModel::class.java) -> {
                InsightsViewModel(focusRepo, appBlockingRepo) as T
            }
            modelClass.isAssignableFrom(WorldViewModel::class.java) -> {
                WorldViewModel(plantRepo, userPrefs) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(focusRepo, plantRepo, achievementRepo, userPrefs) as T
            }
            modelClass.isAssignableFrom(BlockingViewModel::class.java) -> {
                BlockingViewModel(appBlockingRepo) as T
            }
            modelClass.isAssignableFrom(RoutineViewModel::class.java) -> {
                RoutineViewModel(routineRepo) as T
            }
            modelClass.isAssignableFrom(HabitViewModel::class.java) -> {
                HabitViewModel(habitRepo) as T
            }
            modelClass.isAssignableFrom(AchievementViewModel::class.java) -> {
                AchievementViewModel(achievementRepo) as T
            }
            modelClass.isAssignableFrom(SubscriptionViewModel::class.java) -> {
                SubscriptionViewModel(billingRepo) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
