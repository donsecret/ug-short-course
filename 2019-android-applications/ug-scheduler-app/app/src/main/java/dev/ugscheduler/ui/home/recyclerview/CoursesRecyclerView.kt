package dev.ugscheduler.ui.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ugscheduler.BR
import dev.ugscheduler.R
import dev.ugscheduler.shared.data.Course
import io.codelabs.dateformatter.DateFormatter

class CourseViewHolder(
    private val binding: ViewDataBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(course: Course) {
        binding.setVariable(BR.course, course)
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
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_course,
                parent,
                false
            )
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
    if (startTime != -1L && endTime != -1L) {
        val start = DateFormatter(view.context).getTimestamp(startTime)
        val end = DateFormatter(view.context).getTimestamp(endTime)
        view.text = String.format("%s - %s", start, end)
    }
}