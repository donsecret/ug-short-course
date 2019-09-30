/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui.courses


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import dev.ugscheduler.databinding.FragmentCourseDetailsBinding
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.ui.settings.SettingsViewModel
import dev.ugscheduler.ui.settings.SettingsViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject


class CourseDetailsFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentCourseDetailsBinding
    private lateinit var userViewModel: SettingsViewModel
    private lateinit var viewModel: AppViewModel
    private val prefs by inject<UserSharedPreferences>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userViewModel = activityViewModelProvider(SettingsViewModelFactory())
        viewModel = activityViewModelProvider(AppViewModelFactory(get()))

        // Get course from bundle data
        val course = arguments?.get("extra_course") as? Course
        binding.course = course

        if (course != null && course.facilitator.isNotEmpty()) {
            val liveFacilitator = viewModel.getFacilitatorById(course.facilitator, true)
            // todo: Get facilitator information
            liveFacilitator.observe(viewLifecycleOwner, Observer { facilitator ->
                debugger("Course facilitator: $facilitator")
                binding.facilitator = facilitator
            })

            // todo: Get course sessions

            // todo: check if user is enrolled for course already
            if (prefs.isLoggedIn) {
                // todo: show user only info
            }

            binding.swipeRefresh.setOnRefreshListener {
                // Remove old observer and add new one
                liveFacilitator.removeObservers(viewLifecycleOwner)
                liveFacilitator.observe(viewLifecycleOwner, Observer { facilitator ->
                    debugger("Course facilitator refreshed: $facilitator")
                    binding.facilitator = facilitator
                    binding.swipeRefresh.isRefreshing = false
                })
            }
        }
    }


}