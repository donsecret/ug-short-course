package developer.quicknotes.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import developer.quicknotes.R
import kotlinx.android.synthetic.main.activity_notes.*

class NotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        add_new_note_button.setOnClickListener {
            startActivity(Intent(this@NotesActivity, DetailsActivity::class.java))
        }

        // Get the username from the intent
        val username = intent.getStringExtra("login_username")

        // Change the text of the header username
        header_username.text = username ?: "Justice Khenyo"
    }
}
