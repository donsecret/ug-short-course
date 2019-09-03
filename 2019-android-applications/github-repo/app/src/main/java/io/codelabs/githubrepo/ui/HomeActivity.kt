/*
 * Copyright (c) 2019. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package io.codelabs.githubrepo.ui

import android.os.Bundle
import io.codelabs.githubrepo.R
import io.codelabs.githubrepo.shared.core.base.BaseActivity

class HomeActivity : BaseActivity() {
    //private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

}