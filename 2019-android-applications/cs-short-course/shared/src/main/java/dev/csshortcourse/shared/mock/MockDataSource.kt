package dev.csshortcourse.shared.mock

import dev.csshortcourse.shared.model.Course
import java.util.*

/**
 * Fake data source for the application
 */
//@VisibleForTesting
object MockDataSource {

    fun getCourses(): MutableList<Course> {
        val courseList = mutableListOf<Course>()

        // We create some dummy courses
        for (i in 0 until 200) {
            // Create a single course object
            val course = Course(
                UUID.randomUUID().toString(),
                "Name for course: $i",
                UUID.randomUUID().toString(),
                dummyText,
                ""
            )

            // Add that course to the list
            courseList.add(course)
        }

        // Return a list of courses
        return courseList
    }

    private const val dummyText =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
}