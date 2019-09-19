package dev.csshortcourse.student.view

import android.os.Bundle
import dev.csshortcourse.shared.view.BaseActivity
import dev.csshortcourse.shared.util.intentTo
import dev.csshortcourse.student.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add click action for the skip button
        skip_button.setOnClickListener {
            // Create intent
            //val intent = Intent(this@MainActivity, HomeActivity::class.java)

            // Start activity using intent
            //startActivity(intent)

            // Finish this activity
            //finish()

            intentTo(HomeActivity::class.java, true)
        }
    }
}
