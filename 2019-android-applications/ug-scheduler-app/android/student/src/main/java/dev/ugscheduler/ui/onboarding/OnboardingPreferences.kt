package dev.ugscheduler.ui.onboarding

import android.content.Context

class OnboardingPreferences(context: Context) {
    private val prefs by lazy { context.getSharedPreferences("", Context.MODE_PRIVATE) }
}