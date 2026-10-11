package com.focusly.app.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnboardingPage(
    val id: Int,
    @param:DrawableRes val circleBgRes: Int,
    @param:DrawableRes val iconRes: Int?,
    val isPlantHero: Boolean,
    @param:StringRes val tagRes: Int,
    @param:StringRes val titleRes: Int,
    @param:StringRes val subRes: Int,
    @param:StringRes val buttonTextRes: Int
)
