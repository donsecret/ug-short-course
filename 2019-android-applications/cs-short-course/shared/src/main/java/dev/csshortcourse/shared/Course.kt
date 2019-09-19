package dev.csshortcourse.shared

/**
 * Course data model
 */
data class Course(
    val id: String,
    var name: String,
    var facilitator: String,
    var description: String,
    var icon: String? = null
)