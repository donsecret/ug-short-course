package io.codelabs.ugcloudchat

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import com.google.android.gms.tasks.Tasks
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity() {
    private var code: String? = ""


    private val snackbar: Snackbar by lazy {
        Snackbar.make(
            container,
            "Signing in with phone number",
            Snackbar.LENGTH_INDEFINITE
        )
    }

    private val callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential?) {
                debugThis(p0?.smsCode)
                code = p0?.smsCode
                signInWithCredential(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException?) {
                toast(p0?.localizedMessage)
                debugThis(p0?.localizedMessage)
                hideLoading()
            }

            override fun onCodeSent(
                verificationId: String?,
                token: PhoneAuthProvider.ForceResendingToken?
            ) {
                super.onCodeSent(verificationId, token)
                debugThis("Code has been sent with verification ID: $verificationId")

                if (!verificationId.isNullOrEmpty() && !code.isNullOrEmpty()) {
                    val credential = PhoneAuthProvider.getCredential(verificationId, code!!)
                    signInWithCredential(credential)
                } else debugThis("Verification ID is null")
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String?) {
                super.onCodeAutoRetrievalTimeOut(p0)

                debugThis("Time is out. Please try again later")
                toast("Time is out. Please try again later")
                hideLoading()
            }
        }

    private fun signInWithCredential(credential: PhoneAuthCredential?) {
        if (credential == null) {
            debugThis("We cannot verify an account that is null")
            toast("We cannot verify an account that is null")
            hideLoading()
            return
        }

        // Sign in with Firebase
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    debugThis("signInWithCredential:success")


                    // Get user from result
                    val user = task.result?.user
                    debugThis(user)
                    toast("Signed in successfully")

                    // Save user in database
                    saveUserInformation(user)

                    // Navigate to the home screen
                    startActivity(Intent(applicationContext, HomeActivity::class.java))
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    hideLoading()
                    debugThis("signInWithCredential:failure")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        debugThis(task.exception?.localizedMessage)
                        toast(task.exception?.localizedMessage)
                    }
                }
            }
    }

    private fun saveUserInformation(user: FirebaseUser?) {
        if (user == null) {
            debugThis("User is null")
            toast("No logged in user found")
            hideLoading()
            return
        }

        // Send user's information to the database
        ioScope.launch {
            Tasks.await(
                userCollection.document(user.uid).set(
                    WhatsappUser(
                        user.uid,
                        user.phoneNumber!!
                    )
                )
            )
        }

    }

    private fun showLoading() {
        phone_number.isEnabled = false
        sign_in_button.isEnabled = false
        snackbar.show()
    }

    private fun hideLoading() {
        phone_number.isEnabled = true
        sign_in_button.isEnabled = true
        snackbar.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * Make sure that the user enters the required phone number format
         */
        phone_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {

                // 1: Make sure that the input is not empty
                if (input.isNullOrEmpty()) return

                // 2: Make sure that the input matches the required phone number format +2335545464564
                sign_in_button.isEnabled = Patterns.PHONE.matcher(input).matches()

            }
        })
    }

    fun signIn(view: View?) {
        showLoading()
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phone_number.text.toString(), // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            callbacks
        ) // OnVerificationStateChangedCallbacksPhoneAuthActivity.kt
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser != null) {
            debugThis("Logged in as : ${auth.currentUser?.uid}")
            startActivity(Intent(applicationContext, HomeActivity::class.java))
            finishAfterTransition()
        }

    }
}
