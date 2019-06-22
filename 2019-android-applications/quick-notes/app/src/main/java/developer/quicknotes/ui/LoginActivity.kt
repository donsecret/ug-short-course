package developer.quicknotes.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import developer.quicknotes.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        save_username.setOnClickListener {
            // Get thet text entered in the input layout
            val name = username.text.toString()

            if (name.isNotEmpty()) {// Navigate to the Notes Activity
                startActivity(
                    Intent(
                        this@LoginActivity, NotesActivity::class.java
                    ).apply {
                        putExtra("login_username", name)
                    }
                )
                finish()
            } else {
                val text = "Please enter a username first..."
                Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
//                Snackbar.make(container,text,Snackbar.LENGTH_SHORT).show()
            }


        }
    }
}
