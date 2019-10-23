package dev.ugscheduler.ui.onboarding

import androidx.lifecycle.ViewModel
import dev.ugscheduler.shared.util.BaseActivity
import dev.ugscheduler.shared.util.intentTo
import dev.ugscheduler.ui.HomeActivity

class OnboardingViewModel : ViewModel() {

    fun showOnboarding(host: BaseActivity): Boolean =
        OnboardingPreferences(host).getOnboardingState()

    fun performOnboardingCompletion(host: BaseActivity) {
        val prefs = OnboardingPreferences(host)
        prefs.completeOnboarding()
        host.intentTo(HomeActivity::class.java, true)
    }
}
