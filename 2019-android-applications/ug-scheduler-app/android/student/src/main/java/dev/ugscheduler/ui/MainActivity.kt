/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui

import android.os.Bundle
import android.view.View
import dev.ugscheduler.databinding.ActivityMainBinding
import dev.ugscheduler.shared.util.BaseActivity
import dev.ugscheduler.shared.util.intentTo

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun getStarted(view: View) = intentTo(HomeActivity::class.java, true)
}
