package dev.codelabs.firebasetestapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Firebase application initialization
        FirebaseApp.initializeApp(this).apply {
            debugger("Firebase initialized as : ${this?.name}")
        }


        // Login to an existing account
        login_button.setOnClickListener {
            val emailField = email.text.toString()
            val pwdField = password.text.toString()

            // Create account with firebase
            auth.signInWithEmailAndPassword(emailField, pwdField)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Toast.makeText(
                            this@MainActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }.addOnFailureListener(this) { ex ->
                    debugger("An error occurred while creating a new user. ${ex.localizedMessage}")
                }
        }


        // Create a new account for the user
        create_account_button.setOnClickListener {
            val emailField = email.text.toString()
            val pwdField = password.text.toString()

            // Create account with firebase
            auth.createUserWithEmailAndPassword(emailField, pwdField)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        Toast.makeText(
                            this@MainActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }.addOnFailureListener(this) { ex ->
                    debugger("An error occurred while creating a new user. ${ex.localizedMessage}")
                }
        }

        sign_out_button.apply {
            setOnClickListener {
                auth.signOut()
                updateUI(null)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        debugger("User is: $user")

        // Toggle button state
        with(user != null) {
            sign_out_button.isEnabled = this
            login_button.isEnabled = !this
            create_account_button.isEnabled = !this
        }
    }
}

fun debugger(msg: Any?) = println("FirebaseTestApp =>  ${msg.toString()}")
