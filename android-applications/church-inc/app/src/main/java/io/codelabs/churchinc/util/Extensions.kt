package io.codelabs.churchinc.util

import android.content.Context
import android.widget.Toast

/**
 * For debugging only
 */
fun debugLog(msg: Any?) = println("ChurchInc -> ${msg.toString()}")

fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()