package dev.ugscheduler.domain.model

import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * User object model
 * For both [UserType.FACILITATOR] and [UserType.STUDENT]
 */
@Parcelize
data class User(
    override val id: String = UUID.randomUUID().toString(),
    var name: String,
    var address: String? = null,
    var avatar: String? = null,
    var phone: String? = null,
    var type: String = UserType.FACILITATOR,
    var courses: MutableList<String> = mutableListOf(),
    var otherCourses: MutableList<String> = mutableListOf(),
    var createdAt: Long = System.currentTimeMillis()
) : AppDataModel {

    // No-arg constructor for deserialization
    constructor() : this(UUID.randomUUID().toString(), "")
}

/**
 * [User] type
 */
object UserType {
    const val FACILITATOR = "facilitator"
    const val STUDENT = "student"
}


fun User.isFacilitator(): Boolean = this.type == UserType.FACILITATOR
fun User.isStudent(): Boolean = this.type == UserType.STUDENT