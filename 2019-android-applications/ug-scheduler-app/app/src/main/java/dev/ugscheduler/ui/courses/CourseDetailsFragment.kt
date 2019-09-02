package dev.ugscheduler.ui.courses


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.ugscheduler.databinding.FragmentCourseDetailsBinding
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.ui.settings.SettingsViewModel
import dev.ugscheduler.ui.settings.SettingsViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment


class CourseDetailsFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentCourseDetailsBinding
    private lateinit var userViewModel: SettingsViewModel

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

        // Get course from bundle data
        val course = arguments?.get("extra_course") as? Course
        binding.course = course
        debugger(course)

        // todo: Get facilitator information

        // todo: Get course sessions

        // todo: check if user is enrolled for course already
    }


}