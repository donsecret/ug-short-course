package dev.ugscheduler.shared.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "courses")
data class Course(
    @PrimaryKey
    val id: String,
    var name: String,
    var icon: String?,
    var session: String,
    var maxStudents: Int,
    var facilitator: String,
    var startDate: Long,
    var endDate: Long
) : Parcelable {
    @Ignore
    constructor() : this(
        UUID.randomUUID().toString(), "", "", "", 10,"", System.currentTimeMillis(),
        System.currentTimeMillis()
    )
}

fun Course.isInProgress(): Boolean = System.currentTimeMillis() in (startDate + 1) until endDate