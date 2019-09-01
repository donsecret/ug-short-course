package dev.ugscheduler.shared.data

import android.os.Parcelable

/**
 * Base interface for all users
 */
interface BaseUser : Parcelable {
    val id: String
    val timestamp: Long
}