package dev.csshortcourse.assignment.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import dev.csshortcourse.assignment.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    fun loginUser(view: View) {
        // todo: login user with Firebase
    }

    fun navCreateAccount(view: View) {
        with(Intent(this@LoginActivity, CreateAccountActivity::class.java)) {
            startActivity(this)
        }
    }
}
