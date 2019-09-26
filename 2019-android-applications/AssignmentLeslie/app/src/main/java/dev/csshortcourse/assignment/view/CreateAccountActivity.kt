package dev.csshortcourse.assignment.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dev.csshortcourse.assignment.R
import kotlinx.android.synthetic.main.activity_create_account.*

class CreateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun createUser(view: View) {}
}
