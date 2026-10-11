package com.focusly.app.service

import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.Calendar

class UsageStatsManagerWrapper(private val context: Context) {

    @Suppress("MissingPermission")
    fun getTodayAppUsageMinutes(packageName: String): Int {
        val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager
            ?: return 0

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            calendar.timeInMillis,
            System.currentTimeMillis()
        )

        val appStats = stats.find { it.packageName == packageName }
        return appStats?.let { (it.totalTimeInForeground / 1000 / 60).toInt() } ?: 0
    }
}
