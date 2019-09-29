/*
 * Copyright (c) 2019.. Designed & developed by Quabynah Codelabs(c). For the love of Android development.
 */

package dev.ugscheduler.ui.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Tasks
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import dev.ugscheduler.R
import dev.ugscheduler.databinding.SignInFragmentBinding
import dev.ugscheduler.shared.data.Facilitator
import dev.ugscheduler.shared.data.Student
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.viewmodel.AppViewModel
import dev.ugscheduler.shared.viewmodel.AppViewModelFactory
import kotlinx.coroutines.*
import org.koin.android.ext.android.get

class SignInFragment : DialogFragment() {
    private lateinit var binding: SignInFragmentBinding

    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private val snackbar by lazy {
        Snackbar.make(
            binding.root,
            "Signing you into your account",
            Snackbar.LENGTH_INDEFINITE
        )
    }

    private lateinit var viewModel: AppViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignInFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Google sign in options
    private val gso by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    // Google Sign in client
    private val googleClient by lazy { GoogleSignIn.getClient(requireActivity(), gso) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activityViewModelProvider(AppViewModelFactory(get()))
        binding.signIn.setOnClickListener {
            with(googleClient) {
                startActivityForResult(signInIntent, RC_AUTH)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    override fun onResume() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_AUTH) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    try {
                        val account = GoogleSignIn.getSignedInAccountFromIntent(data)
                            .getResult(ApiException::class.java)
                        val auth: FirebaseAuth = get()

                        // Login with credentials on Firebase
                        snackbar.show()
                        auth.signInWithCredential(
                            GoogleAuthProvider.getCredential(
                                account?.idToken,
                                null
                            )
                        )
                            .addOnFailureListener(requireActivity()) { ex ->
                                debugger(ex.localizedMessage)
                                snackbar.setText("Login failed. Please check your internet connection")
                                    .addCallback(object :
                                        BaseTransientBottomBar.BaseCallback<Snackbar?>() {
                                        override fun onDismissed(
                                            transientBottomBar: Snackbar?,
                                            event: Int
                                        ) {
                                            dismiss()
                                        }
                                    }).show()
                            }
                            .addOnCompleteListener(requireActivity()) { task ->
                                if (task.isSuccessful) {
                                    val firebaseUser = task.result?.user
                                    val student = firebaseUser?.toStudent()
                                    viewModel.addStudent(student)
                                    dismiss()
                                } else {
                                    snackbar.setText("Login failed. ${task.exception?.localizedMessage}")
                                        .addCallback(object :
                                            BaseTransientBottomBar.BaseCallback<Snackbar?>() {
                                            override fun onDismissed(
                                                transientBottomBar: Snackbar?,
                                                event: Int
                                            ) {
                                                dismiss()
                                            }
                                        }).show()
                                }
                            }
                    } catch (ex: Exception) {
                        debugger(ex.localizedMessage)
                    }
                }

                else -> {
                    debugger("Login was cancelled")
                    // performTestLogin()
                }
            }
        }
    }

    private fun performTestLogin() {
        val auth: FirebaseAuth = get()
        ioScope.launch {
            try {
                val student = Tasks.await(auth.signInAnonymously()).user?.toStudent()
                debugger("Logged in as: $student")

                uiScope.launch {
                    if (student != null) {
                        viewModel.addStudent(student)
                        dismiss()
                    } else {
                        debugger("Student is not logged in")
                        snackbar.setText("Login failed")
                        delay(1850)
                        dismiss()
                    }
                }
            } catch (e: Exception) {
                debugger(e.localizedMessage)
                dismiss()
            }
        }
    }

    companion object {
        private const val RC_AUTH = 9
    }

}

fun FirebaseUser.toStudent() = Student(
    uid,
    System.currentTimeMillis(),
    email!!,
    phoneNumber,
    photoUrl.toString(),
    null,
    displayName,
    null,
    null,
    null,
    null,
    null
)

fun FirebaseUser.toFacilitator() = Facilitator(
    uid,
    System.currentTimeMillis(),
    photoUrl.toString(),
    email!!,
    displayName,
    2.50,
    phoneNumber
)
