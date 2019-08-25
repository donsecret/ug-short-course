package dev.ugscheduler.domain.model

import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.util.*

/**
 * [Course] object data model
 */
@Parcelize
data class Course(
    override val id: String = UUID.randomUUID().toString(),
    val name: String,
    val fee: BigDecimal,
    var facilitator: String? = null
) : AppDataModel {
    // No-Arg constructor
    constructor() : this(UUID.randomUUID().toString(), "", BigDecimal.ZERO)
}

fun Course.hasFacilitator(): Boolean = !this.facilitator.isNullOrEmpty()