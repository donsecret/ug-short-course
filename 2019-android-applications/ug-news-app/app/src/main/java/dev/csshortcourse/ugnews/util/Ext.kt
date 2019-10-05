package dev.csshortcourse.ugnews.util

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf

fun AppCompatActivity.moveTo(
    target: Class<out AppCompatActivity>,
    finish: Boolean = false,
    data: Bundle = bundleOf()
) {
    startActivity(Intent(this, target).apply { putExtras(data) })
    if (finish) finishAfterTransition()
}

fun debugger(msg: Any?) = println("NewsApp => ${msg.toString()}")