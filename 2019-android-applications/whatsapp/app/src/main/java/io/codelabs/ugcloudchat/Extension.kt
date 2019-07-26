package io.codelabs.ugcloudchat

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast


fun Context.toast(msg: Any?) = Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show()

/**
 * Returns a layout inflater
 */
val Context.layoutInflater get() = LayoutInflater.from(this)

/**
 * For debugging
 */
fun Any?.debugThis(msg: Any?) = println("UGCloudChat: ${msg.toString()}")

/**
 *
 */
typealias Callback<TYPE> = (TYPE) -> Unit
