package dev.ugscheduler.ui.enrol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dev.ugscheduler.BuildConfig.FLAVOR
import dev.ugscheduler.databinding.FragmentEnrolBinding
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.data.Student
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.util.deserializer.getCourses
import dev.ugscheduler.shared.util.toast
import dev.ugscheduler.util.MainNavigationFragment

class EnrolFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentEnrolBinding
    private lateinit var viewModel: EnrolViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnrolBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EnrolViewModel::class.java)

        val course = arguments?.get("extra_course") as? Course
        val student = arguments?.get("extra_student") as? Student
        if (course != null && student != null) {
            /*val enrolment =
                Enrolment(student.id, course.id, Session.WEEKEND.sessionName())
            debugger(enrolment)*/

            debugger(FLAVOR)
        } else {
            toast("Cannot setup enrolment. An error occurred")
            findNavController().popBackStack()
        }

        getCourses(requireContext())
    }

}
