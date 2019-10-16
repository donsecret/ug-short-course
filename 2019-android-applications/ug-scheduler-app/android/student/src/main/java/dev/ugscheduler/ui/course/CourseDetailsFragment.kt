package dev.ugscheduler.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.ugscheduler.databinding.FragmentCourseDetailsBinding
import dev.ugscheduler.util.MainNavigationFragment

class CourseDetailsFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentCourseDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseDetailsBinding.inflate(inflater)
        return binding.root
    }
}
