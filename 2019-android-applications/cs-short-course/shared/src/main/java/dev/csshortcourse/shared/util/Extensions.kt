package dev.csshortcourse.shared.util

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.Toast
import dev.csshortcourse.shared.view.BaseActivity

// This is a simple function to log messages to the console
fun debugger(msg: Any?) = println("Debugger => $msg")

// My first extension function
// This adds additional property to the BaseActivity class
val BaseActivity.currentContext: String get() = this::class.java.simpleName

// Create an intent to navigate to other activities
fun BaseActivity.intentTo(target: Class<out BaseActivity>, isFinished: Boolean = false) {
    // Create the intent object
    val intent = Intent(this, target)

    // Start activity
    startActivity(intent)

    // Finish if needed
    if (isFinished) finish()
}

// Shows a simple toast message to the screen
fun Context.toast(msg: Any?) = Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show()

// Layout inflater
val Context.layoutInflater: LayoutInflater get() = LayoutInflater.from(this)

