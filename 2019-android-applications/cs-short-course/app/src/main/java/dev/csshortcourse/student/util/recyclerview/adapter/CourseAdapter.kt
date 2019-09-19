package dev.csshortcourse.student.util.recyclerview.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.csshortcourse.shared.model.Course
import dev.csshortcourse.shared.util.layoutInflater
import dev.csshortcourse.shared.util.load
import dev.csshortcourse.student.R
import dev.csshortcourse.student.util.recyclerview.viewholder.CourseViewHolder
import dev.csshortcourse.student.view.CourseDetailsActivity
import kotlinx.android.synthetic.main.item_course.view.*

/**
 * Adapter for our [RecyclerView]
 */
class CourseAdapter constructor(private val context: Activity) :
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
            // Create intent
            val courseIntent = Intent(context, CourseDetailsActivity::class.java)

            // Send course to details page
            courseIntent.putExtras(bundleOf(Pair(CourseDetailsActivity.EXTRA_COURSE, course)))

            // Start activity
            context.startActivity(courseIntent)
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