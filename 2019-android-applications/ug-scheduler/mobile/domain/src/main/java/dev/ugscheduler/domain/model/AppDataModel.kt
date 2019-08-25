package dev.ugscheduler.domain.model

import android.os.Parcelable

/**
 * Base implementation of all data models
 * This allows subclasses to implement the parcelable interface
 * making them shareable across activities and fragments
 */
interface AppDataModel : Parcelable {
    val id: String
}