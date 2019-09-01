package dev.ugscheduler.shared.util

import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf

fun debugger(msg: Any?) = println("UGScheduler ==> ${msg.toString()}")

fun BaseActivity.intentTo(
    target: Class<out BaseActivity>,
    isFinished: Boolean = false,
    data: Bundle = bundleOf()
) {
    startActivity(Intent(this, target).apply { putExtras(data) })
    if (isFinished) {
        finishAfterTransition()
    }
}