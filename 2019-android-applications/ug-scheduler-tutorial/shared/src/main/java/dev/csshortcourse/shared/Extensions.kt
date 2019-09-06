package dev.csshortcourse.shared

// This is a simple function to log messages to the console
fun debugger(msg: Any?) = println("Debugger => $msg")

// My first extension function
// This adds additional property to the BaseActivity class
val BaseActivity.currentContext: String get() = this::class.java.simpleName