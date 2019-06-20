package developer.quicknotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import developer.quicknotes.R

class NotesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
    }
}
