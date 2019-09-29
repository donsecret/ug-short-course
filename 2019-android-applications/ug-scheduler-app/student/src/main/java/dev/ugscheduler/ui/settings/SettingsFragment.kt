/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dev.ugscheduler.databinding.SettingsFragmentBinding
import dev.ugscheduler.shared.datasource.local.StudentDao
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class SettingsFragment : MainNavigationFragment() {
    private lateinit var binding: SettingsFragmentBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activityViewModelProvider(SettingsViewModelFactory())
        val appVM: AppViewModel = activityViewModelProvider(AppViewModelFactory(get()))

        binding.swipeRefresh.setOnRefreshListener {
            val currentStudent = appVM.getCurrentStudent(true)
            currentStudent.removeObservers(viewLifecycleOwner)
            currentStudent.observe(viewLifecycleOwner, Observer { student ->
                binding.student = student
                binding.viewModel = viewModel
                binding.swipeRefresh.isRefreshing = false
            })
        }

        // Get current user's information
        appVM.getCurrentStudent(false).observe(viewLifecycleOwner, Observer { student ->
            binding.student = student
            binding.viewModel = viewModel
        })
    }


}
