/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// todo: setup repositories
class SettingsViewModelFactory :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SettingsViewModel(/*localRepository,remoteRepository*/) as T
    }
}

class SettingsViewModel :
    ViewModel()