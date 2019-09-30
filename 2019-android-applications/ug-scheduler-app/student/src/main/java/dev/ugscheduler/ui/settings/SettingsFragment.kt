/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dev.ugscheduler.R
import dev.ugscheduler.databinding.SettingsFragmentBinding
import dev.ugscheduler.shared.prefs.AppPreferences
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.shared.widgets.PreferenceView
import dev.ugscheduler.util.MainNavigationFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

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

        val prefs by inject<AppPreferences>()
        binding.root.findViewById<PreferenceView>(R.id.prefs_theme).apply {
            setOnClickListener {
                val options =
                    arrayOf<CharSequence>("Light", "Dark", "Set by Battery Saver", "System Default")
                MaterialAlertDialogBuilder(this@SettingsFragment.requireContext()).apply {
                    setTitle("Select theme")
                    setSingleChoiceItems(options, if (prefs.currentMode == -1) 3 else prefs.currentMode.minus(1)) { dialog, which ->
                        dialog.dismiss()
                        uiScope.launch {
                            delay(550)
                            when (which) {
                                0 -> prefs.setDarkMode(AppCompatDelegate.MODE_NIGHT_NO)
                                1 -> prefs.setDarkMode(AppCompatDelegate.MODE_NIGHT_YES)
                                2 -> prefs.setDarkMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                                else -> prefs.setDarkMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                            }
                        }
                    }
                    setNegativeButton("Dismiss") { dialog, _ ->
                        dialog.cancel()
                    }
                    show()

                    debugger(prefs.currentMode)
                }
            }
        }
    }
}
