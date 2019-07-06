package developer.quicknotes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import developer.quicknotes.R
import developer.quicknotes.core.NoteDAO
import developer.quicknotes.core.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class DetailsActivity : AppCompatActivity() {
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)
    private val dao: NoteDAO by lazy { NoteDatabase.getDatabaseInstance(this).dao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
    }


    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
