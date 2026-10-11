package com.focusly.app.presentation.subscription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.focusly.app.domain.repository.BillingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SubscriptionUiState(
    val isPremium: Boolean = false,
    val selectedPlan: String = "Yearly"
)

class SubscriptionViewModel(
    private val billingRepository: BillingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionUiState())
    val uiState: StateFlow<SubscriptionUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            billingRepository.isPremiumUnlocked().collect { unlocked ->
                _uiState.update { it.copy(isPremium = unlocked) }
            }
        }
    }

    fun purchase() {
        viewModelScope.launch {
            billingRepository.purchasePremium()
        }
    }
}
