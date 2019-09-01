package dev.ugscheduler.shared.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "feedback")
data class Feedback(
    val id: String,
    var message: String,
    var sender: String
) : Parcelable {

    @Ignore
    constructor() : this(UUID.randomUUID().toString(), "", "")
}