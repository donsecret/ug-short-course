/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.shared.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "enrolments")
data class Enrolment(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val student: String,
    val course: String,
    val session: String = Session.EVENING.sessionName(),
    var hasPaid: Boolean,
    val timestamp: Long
) : Parcelable {
    @Ignore
    constructor() : this("", "", "", "", false, System.currentTimeMillis())
}

/**
 * Sessions for course
 */
enum class Session {
    MORNING, EVENING, WEEKEND
}

fun Session.sessionName(): String = when {
    this == Session.MORNING -> "Morning"
    this == Session.EVENING -> "Evening"
    this == Session.WEEKEND -> "Weekend"
    else -> "None"
}