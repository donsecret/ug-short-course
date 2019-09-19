package dev.csshortcourse.student.util.recyclerview.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.csshortcourse.shared.Course
import dev.csshortcourse.shared.layoutInflater
import dev.csshortcourse.shared.load
import dev.csshortcourse.student.R
import dev.csshortcourse.student.util.recyclerview.viewholder.CourseViewHolder
import kotlinx.android.synthetic.main.item_course.view.*

/**
 * Adapter for our [RecyclerView]
 */
class CourseAdapter constructor(private val context: Context) :
    ListAdapter<Course, CourseViewHolder>(
        DIFF_UTIL
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        // The view we want to inflate an number of times in our recyclerview
        val courseView: View = context.layoutInflater.inflate(R.layout.item_course, parent, false)

        // We create an instance of our view holder based on the view we have created above
        return CourseViewHolder(courseView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        // This will get the course at each position in the list
        val course = getItem(position)

        // Bind properties to the layout
        holder.v.course_name.text = course.name
        holder.v.course_desc.text = course.description
        holder.v.course_icon.load(course.icon)

        holder.v.setOnClickListener {
            // todo: navigate to the details page for this course using intents
        }
    }


    companion object {
        private val DIFF_UTIL: DiffUtil.ItemCallback<Course> =
            object : DiffUtil.ItemCallback<Course>() {
                override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean =
                    oldItem == newItem
            }
    }
}