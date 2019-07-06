package developer.quicknotes.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import developer.quicknotes.R
import developer.quicknotes.core.NoteDAO
import developer.quicknotes.core.NoteDatabase
import developer.quicknotes.data.Note
import developer.quicknotes.extensions.toast
import kotlinx.android.synthetic.main.activity_add_note.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddNoteActivity : AppCompatActivity() {
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)
    private val dao: NoteDAO by lazy { NoteDatabase.getDatabaseInstance(this).dao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        add_note_button.setOnClickListener {
            // Check the input field
            val message = note_input_field.text.toString()

            if (message.isEmpty()) {
                toast("Please enter a message first")
            } else addNote()
        }
    }

    /**
     * Create a new [Note]
     */
    private fun addNote() {
        // Extract message from the input field
        val message = note_input_field.text.toString()

        // Create a new note object
        val note = Note(message)

        ioScope.launch {
            // Add note to the database
            dao.createNote(note)
        }

        // Show a simple toast to notify the user of the process
        toast("Added note successfully")
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
