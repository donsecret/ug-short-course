package dev.csshortcourse.student.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.csshortcourse.shared.model.Course
import dev.csshortcourse.shared.util.debugger
import dev.csshortcourse.shared.util.load
import dev.csshortcourse.student.R
import kotlinx.android.synthetic.main.activity_course_details.*

class CourseDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)

        // Add action to toolbar navigation icon
        toolbar.setNavigationOnClickListener { onBackPressed() }

        // Get the course from our intent
        if (intent.hasExtra(EXTRA_COURSE)) {
            bindCourse(intent.getParcelableExtra<Course>(EXTRA_COURSE))
        }
    }


    // Setup course details
    private fun bindCourse(course: Course?) {
        if (course == null) {
            debugger("Course is null")
            return
        }

        course_icon.load(course.icon)
        course_name.text = course.name
        course_desc.text = course.description
    }

    companion object {
        const val EXTRA_COURSE = "EXTRA_COURSE"
    }
}
