package developer.quicknotes.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import developer.quicknotes.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Send the user to the login screen when the Get Started button is clicked
        // We will make use of intents in this case
        get_started_button.setOnClickListener {
            startActivity(
                Intent(
                    /*From*/ this@MainActivity,/*To*/ LoginActivity::class.java
                )
            )
            finish()
        }
    }
}
