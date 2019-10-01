package dev.csshortcourse.assignmenttwo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Chat data model
 */
@Parcelize
data class Chat(
    val id: String,
    val sender: String,
    val recipient: String,
    var message: String,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable