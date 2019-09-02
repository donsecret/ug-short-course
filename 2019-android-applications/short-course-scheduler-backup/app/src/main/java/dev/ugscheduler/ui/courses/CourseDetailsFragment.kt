package dev.ugscheduler.ui.courses


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dev.ugscheduler.R
import dev.ugscheduler.util.MainNavigationFragment


class CourseDetailsFragment : MainNavigationFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_details, container, false)
    }


}