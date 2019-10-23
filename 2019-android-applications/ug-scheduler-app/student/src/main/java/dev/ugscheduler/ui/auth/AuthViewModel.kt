package dev.ugscheduler.ui.auth

import android.net.Uri
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.ugscheduler.shared.data.Student
import dev.ugscheduler.shared.util.debugger


class AuthViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel() as T
    }

}

class AuthViewModel : ViewModel() {
    private val _userImageUri = MutableLiveData<Uri?>()
    val currentUserImageUri: LiveData<Uri?> = _userImageUri

    private val _userInfo = MutableLiveData<Student?>()
    val currentUserInfo: LiveData<Student?> = _userInfo

    fun onProfileClicked(fm: FragmentManager, loggedIn: Boolean) =
        if (loggedIn) SignOutFragment().show(fm, null) else SignInFragment().show(fm, null)
}