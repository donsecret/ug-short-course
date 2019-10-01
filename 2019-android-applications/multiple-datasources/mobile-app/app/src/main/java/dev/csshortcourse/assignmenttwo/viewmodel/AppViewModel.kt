package dev.csshortcourse.assignmenttwo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.csshortcourse.assignmenttwo.viewmodel.repository.AppRepository

/**
 * ViewModel instance
 */
class AppViewModel(application: Application) : AndroidViewModel(application) {
    // Create repository instance
    private val repo: AppRepository by lazy { AppRepository.get(application) }

}