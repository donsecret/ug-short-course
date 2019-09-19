package dev.csshortcourse.shared.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Course data model
 */
@Parcelize
data class Course(
    val id: String,
    var name: String,
    var facilitator: String,
    var description: String,
    var icon: String? = null
) : Parcelable