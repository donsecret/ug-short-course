package dev.csshortcourse.student.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.csshortcourse.shared.mock.MockDataSource
import dev.csshortcourse.student.R
import dev.csshortcourse.student.util.recyclerview.adapter.CourseAdapter
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Get the recyclerview from the layout
        courses_list.apply {
            // Attach adapter
            adapter = CourseAdapter(this@HomeFragment.requireActivity()).apply {
                // Submit a list of courses to the adapter
                submitList(MockDataSource.getCourses())
            }

            // Set layout manager
            layoutManager = LinearLayoutManager(this@HomeFragment.requireContext())
        }
    }

}
