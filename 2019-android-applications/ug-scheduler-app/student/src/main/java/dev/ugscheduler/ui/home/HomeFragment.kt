/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.updatePaddingRelative
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import dev.ugscheduler.R
import dev.ugscheduler.databinding.FragmentHomeBinding
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.doOnApplyWindowInsets
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.ui.auth.AuthViewModel
import dev.ugscheduler.ui.auth.AuthViewModelFactory
import dev.ugscheduler.ui.home.recyclerview.CourseAdapter
import dev.ugscheduler.ui.home.recyclerview.ItemClickListener
import dev.ugscheduler.util.MainNavigationFragment
import dev.ugscheduler.util.setupProfileMenuItem
import org.koin.android.ext.android.get

class HomeFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: AppViewModel by lazy {
        activityViewModelProvider<AppViewModel>(
            AppViewModelFactory(get())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Pad the bottom of the RecyclerView so that the content scrolls up above the nav bar
        binding.recyclerView.doOnApplyWindowInsets { v, insets, padding ->
            v.updatePaddingRelative(bottom = padding.bottom + insets.systemWindowInsetBottom)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.toolbar.setupProfileMenuItem(
            activityViewModelProvider<AuthViewModel>(AuthViewModelFactory()),
            childFragmentManager, get<UserSharedPreferences>(),
            viewLifecycleOwner
        )

        // Create adapter
        val adapter = CourseAdapter(object : ItemClickListener {
            override fun onClick(course: Course) {
                findNavController().navigate(
                    R.id.navigation_course_details, bundleOf(
                        Pair("extra_course", course)
                    )
                )
            }
        })

        binding.swipeRefresh.setOnRefreshListener {
            adapter.submitList(viewModel.getAllCourses(requireContext(), true))
            binding.swipeRefresh.isRefreshing = false
        }

        binding.recyclerView.apply {
            setHasFixedSize(false)
            this.adapter = adapter
            this.itemAnimator = DefaultItemAnimator()

            // Get courses and add to adapter
            adapter.submitList(viewModel.getAllCourses(requireContext(), false))
        }
    }

}