package dev.ugscheduler.ui.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ugscheduler.R
import dev.ugscheduler.databinding.ItemCourseBinding
import dev.ugscheduler.shared.data.Course
import dev.ugscheduler.shared.util.clipToCircle
import dev.ugscheduler.shared.util.imageUrl
import io.codelabs.dateformatter.DateFormatter


class CourseViewHolder(
    private val binding: ItemCourseBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(course: Course) {
        clipToCircle(binding.icon, true)
        imageUrl(
            binding.icon,
            course.icon,
            binding.root.resources.getDrawable(R.drawable.ic_default_avatar_2, null)
        )
        binding.courseName.text = course.name
        binding.courseDuration.text =
            DateFormatter(binding.root.context).getTimestamp(System.currentTimeMillis())
    }
}

object CourseDiff : DiffUtil.ItemCallback<Course>() {
    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean = oldItem == newItem

    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.name == newItem.name && oldItem.id == newItem.id
    }
}

class CourseAdapter(private val listener: ItemClickListener) :
    ListAdapter<Course, CourseViewHolder>(CourseDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(
            ItemCourseBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listener.onClick(getItem(position))
        }
    }
}

interface ItemClickListener {
    fun onClick(course: Course)
}

@BindingAdapter("startTime", "endTime", requireAll = true)
fun setupCourseInfo(view: TextView, startTime: Long, endTime: Long) {
    if (startTime > -1L && endTime > -1L) {
        val start = DateFormatter(view.context).getTimestamp(startTime)
        val end = DateFormatter(view.context).getTimestamp(endTime)
        view.text = String.format("%s - %s", start, end)
    } else view.text = "12:00 AM - 3:00PM"
}