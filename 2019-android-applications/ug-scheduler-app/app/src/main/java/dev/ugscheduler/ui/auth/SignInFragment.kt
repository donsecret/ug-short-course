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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import dev.ugscheduler.R
import dev.ugscheduler.databinding.SignInFragmentBinding
import dev.ugscheduler.shared.data.Student
import dev.ugscheduler.shared.datasource.local.StudentDao
import dev.ugscheduler.shared.datasource.remote.studentDocument
import dev.ugscheduler.shared.util.activityViewModelProvider
import dev.ugscheduler.shared.util.debugger
import dev.ugscheduler.shared.util.prefs.UserSharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class SignInFragment : DialogFragment() {
    private lateinit var binding: SignInFragmentBinding

    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    private lateinit var viewModel: AuthViewModel

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
        viewModel = activityViewModelProvider(AuthViewModelFactory())
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
                        auth.signInWithCredential(
                            GoogleAuthProvider.getCredential(
                                account?.idToken,
                                null
                            )
                        )
                            .addOnFailureListener(requireActivity()) { ex ->
                                debugger(ex.localizedMessage)
                                // todo: show login failed
                                dismiss()
                            }
                            .addOnCompleteListener(requireActivity()) { user ->
                                if (user.isSuccessful) {
                                    val firebaseUser = user.result?.user
                                    val student = firebaseUser?.toUser()
                                    val prefs: UserSharedPreferences = get()
                                    val db: FirebaseFirestore = get()
                                    prefs.login(student?.id)
                                    debugger(student)

                                    ioScope.launch {
                                        val dao: StudentDao = get()
                                        if (student != null) {
                                            dao.insert(student)
                                            Tasks.await(
                                                db.studentDocument(student.id).set(
                                                    student,
                                                    SetOptions.merge()
                                                )
                                            )
                                        }
                                        uiScope.launch {
                                            dismiss()
                                        }
                                    }
                                }
                            }
                    } catch (ex: Exception) {
                        debugger(ex.localizedMessage)
                    }
                }

                else -> {
                    debugger("Login was cancelled")
                }
            }
        }
    }

    companion object {
        private const val RC_AUTH = 9
    }

}

fun FirebaseUser.toUser() = Student(
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
