package dev.ugscheduler.ui.onboarding

import androidx.lifecycle.ViewModel
import dev.ugscheduler.shared.util.BaseActivity
import dev.ugscheduler.shared.util.intentTo
import dev.ugscheduler.ui.HomeActivity

class OnboardingViewModel : ViewModel() {

    fun performOnboardingCompletion(host: BaseActivity) {
        val prefs = OnboardingPreferences(host)
        host.intentTo(HomeActivity::class.java, true)
        // todo: perform onboarding completion so that user is not sent to this page again
    }
}
