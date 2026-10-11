package com.focusly.app.data.repository

import com.focusly.app.domain.repository.BillingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class BillingRepositoryImpl : BillingRepository {

    private val _isUnlocked = MutableStateFlow(false)

    override fun isPremiumUnlocked(): Flow<Boolean> = _isUnlocked

    override suspend fun purchasePremium(): Boolean {
        _isUnlocked.value = true
        return true
    }
}
