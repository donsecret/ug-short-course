package dev.ugscheduler.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import dev.ugscheduler.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getStarted(view: View) {
        // todo: Start the application in guest mode
    }
}
