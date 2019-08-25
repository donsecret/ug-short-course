package dev.ugscheduler.domain.model

import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Schedule for each course for student
 */
@Parcelize
data class Schedule(
    override val id: String = UUID.randomUUID().toString(),
    var course: String,
    var facilitator: String,
    var student: String,
    var venue: String,
    var startTime: Long = System.currentTimeMillis(),
    var endTime: Long = System.currentTimeMillis(),
    var isCanceledOrPostponed: Boolean = false
) : AppDataModel {
    // No-Arg constructor
    constructor() : this(UUID.randomUUID().toString(), "", "", "", "")
}