package io.codelabs.whatsappclone

import android.content.Context
import android.widget.Toast


fun Context.toast(msg: Any?) = Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show()

/**
 * For debugging
 */
fun Any?.debugThis(msg: Any?) = println("WhatsappClone: ${msg.toString()}")