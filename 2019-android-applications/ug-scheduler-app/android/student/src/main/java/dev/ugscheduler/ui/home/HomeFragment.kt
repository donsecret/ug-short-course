package dev.ugscheduler.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.updatePaddingRelative
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import dev.ugscheduler.R
import dev.ugscheduler.databinding.FragmentHomeBinding
import dev.ugscheduler.databinding.ItemCourseBinding
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.doOnApplyWindowInsets
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import dev.ugscheduler.ui.auth.AuthViewModelFactory
import dev.ugscheduler.ui.home.recyclerview.CourseViewHolder
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
        binding = FragmentHomeBinding.inflate(inflater)
        // Pad the bottom of the RecyclerView so that the content scrolls up above the nav bar
        binding.recyclerView.doOnApplyWindowInsets { v, insets, padding ->
            v.updatePaddingRelative(bottom = padding.bottom + insets.systemWindowInsetBottom)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Setup toolbar
        binding.toolbar.setupProfileMenuItem(
            activityViewModelProvider(AuthViewModelFactory()),
            childFragmentManager, get(),
            viewLifecycleOwner
        )

        // Create adapter
        /*val adapter = CourseAdapter(object : ItemClickListener {
            override fun onClick(course: Course) {
                findNavController().navigate(
                    R.id.navigation_course_details, bundleOf(
                        Pair("extra_course", course)
                    )
                )
            }
        })*/

        val adapter = CourseListAdapter(requireContext(), object : ItemClickListener {
            override fun onClick(course: Course) {
                findNavController().navigate(
                    R.id.navigation_course_details, bundleOf(
                        Pair("extra_course", course)
                    )
                )
            }
        })

        // Get courses and add to adapter
        binding.recyclerView.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.itemAnimator = DefaultItemAnimator()
            this.setHasFixedSize(false)
            this.adapter = adapter
        }
        adapter.submitList(viewModel.getAllCourses(requireContext(), false))

        // Swipe to refresh feature
        binding.swipeRefresh.setOnRefreshListener {
            adapter.submitList(viewModel.getAllCourses(requireContext(), true))
            binding.swipeRefresh.isRefreshing = false
        }

    }

    class CourseListAdapter(private val context: Context, private val listener: ItemClickListener) :
        ListAdapter<Course, CourseViewHolder>(COURSE_DIFF) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
            return CourseViewHolder(ItemCourseBinding.inflate(LayoutInflater.from(context)))
        }

        override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
            holder.bind(getItem(position))
            holder.itemView.setOnClickListener {
                listener.onClick(getItem(position))
            }
        }


        companion object {
            private val COURSE_DIFF: DiffUtil.ItemCallback<Course> =
                object : DiffUtil.ItemCallback<Course>() {
                    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean =
                        oldItem.id == newItem.id

                    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean =
                        oldItem == newItem
                }
        }
    }
}