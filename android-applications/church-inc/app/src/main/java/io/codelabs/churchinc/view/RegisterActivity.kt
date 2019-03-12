package io.codelabs.churchinc.view

import android.os.Bundle
import android.view.View
import io.codelabs.churchinc.R
import io.codelabs.churchinc.core.RootActivity

class RegisterActivity : RootActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun doRegister(v: View) {

    }
}
