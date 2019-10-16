package dev.ugscheduler.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.api.load
import coil.transform.CircleCropTransformation
import dev.ugscheduler.R
import dev.ugscheduler.databinding.FragmentCourseDetailsBinding
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.util.deserializer.getCourses
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.util.MainNavigationFragment
import org.koin.android.ext.android.get

class CourseDetailsFragment : MainNavigationFragment() {
    private lateinit var binding: FragmentCourseDetailsBinding
    private val viewModel: AppViewModel by lazy {
        activityViewModelProvider<AppViewModel>(
            AppViewModelFactory(get())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val currentCourse = arguments?.get("extra_course") as? Course
        bindUI(currentCourse)
    }

    private fun bindUI(course: Course?) {
        binding.courseName.text = course?.name
        binding.courseDesc.text = course?.desc
        val facilitator = course?.facilitator
        getFacilitator(facilitator)
        binding.enrolCourse.setOnClickListener {
            findNavController().navigate(
                R.id.navigation_enrol, bundleOf(
                    Pair("extra_course", course)
                )
            )
        }

        binding.swipeRefresh.setOnRefreshListener {
            // Fetch live data from the remote data source
            val filter = viewModel.getCourses(requireContext()).filter { it.id == course?.id }
            if (filter.isNotEmpty() && filter.size == 1) {
                binding.courseName.text = filter[0].name
                binding.courseDesc.text = filter[0].desc
                getFacilitator(filter[0].facilitator, true)
                binding.swipeRefresh.isRefreshing = false
            } else {
                binding.swipeRefresh.isRefreshing = false
                debugger("Unable to fetch courses")
            }
        }
    }

    private fun getFacilitator(facilitator: String?, refresh: Boolean = false) {
        debugger("Facilitator's id: $facilitator")
        if (facilitator.isNullOrEmpty()) return
        viewModel.getFacilitatorById(facilitator, refresh)
            .observe(viewLifecycleOwner, Observer { person ->
                if (person != null) {
                    debugger(person)
                    binding.facilitatorImage.load(person.avatar) {
                        transformations(CircleCropTransformation())
                        placeholder(R.drawable.ic_default_avatar_3)
                    }
                    binding.facilitatorName.text = person.fullName
                    binding.courseFacilitatorDesc.text = person.email
                } else {
                    binding.swipeRefresh.isRefreshing = true
                }
            })
    }
}
