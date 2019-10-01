package dev.csshortcourse.assignmenttwo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * User data model
 */
@Parcelize
data class User(
    val id: String,
    val name: String,
    val avatar: Int? = null,
    var timestamp: Long = System.currentTimeMillis()
) : Parcelable {
    constructor(name: String) : this(UUID.randomUUID().toString(), name)
}