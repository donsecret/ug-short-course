/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui.courses


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.ugscheduler.databinding.FragmentMyCoursesBinding
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment
import org.koin.android.ext.android.get


class MyCoursesFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentMyCoursesBinding
    private val viewModel: AppViewModel by lazy {
        activityViewModelProvider<AppViewModel>(
            AppViewModelFactory(get())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val myCourses = viewModel.getMyCourses(false)
        debugger("My courses: $myCourses")
        // todo: add to recyclerview

    }
}
