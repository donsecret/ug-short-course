package dev.ugscheduler.ui.courses


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dev.ugscheduler.R
import dev.ugscheduler.databinding.FragmentCourseDetailsBinding
import dev.ugscheduler.util.MainNavigationFragment


class CourseDetailsFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentCourseDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        arguments?.get("extra_course") as? Course
    }


}
