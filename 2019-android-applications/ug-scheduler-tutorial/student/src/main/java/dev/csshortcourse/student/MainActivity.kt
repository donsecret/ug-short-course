package dev.csshortcourse.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.csshortcourse.shared.debugger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        debugger("Hello world")
        debugger("Anything")
        debugger(null)
    }
}
