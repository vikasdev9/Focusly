package com.focusly.app.domain.repository

import kotlinx.coroutines.flow.Flow

interface BillingRepository {
    fun isPremiumUnlocked(): Flow<Boolean>
    suspend fun purchasePremium(): Boolean
}
