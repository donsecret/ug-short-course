package dev.ugscheduler.shared.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * For [Anonymous] users
 */
@Parcelize
@Entity(tableName = "anonymous_users")
data class Anonymous(
    @PrimaryKey
    override val id: String,
    override val timestamp: Long
) : BaseUser {

    @Ignore
    constructor() : this(UUID.randomUUID().toString(), System.currentTimeMillis())
}

/**
 * [Student]s
 */
@Entity(tableName = "students")
@Parcelize
data class Student(
    @PrimaryKey
    override val id: String,
    override val timestamp: Long,
    val email: String,
    var phone: String?,
    var avatar: String?,
    var dob: String?, var fullName: String,
    var residence: String?, var organisation: String?,
    var eduBackground: String?,
    var prevCourses: MutableList<String> = mutableListOf()
) : BaseUser {

    @Ignore
    constructor() : this(
        UUID.randomUUID().toString(),
        System.currentTimeMillis(),
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""
    )
}

/**
 * [Facilitator]s
 */
@Entity(tableName = "facilitators")
@Parcelize
data class Facilitator(
    @PrimaryKey
    override val id: String,
    override val timestamp: Long,
    var avatar: String?,
    var email: String, var fullName: String,
    var phone: String?, var courses: MutableList<String> = mutableListOf()
) : BaseUser {

    @Ignore
    constructor() : this(UUID.randomUUID().toString(), System.currentTimeMillis(), "", "", "", "")
}
