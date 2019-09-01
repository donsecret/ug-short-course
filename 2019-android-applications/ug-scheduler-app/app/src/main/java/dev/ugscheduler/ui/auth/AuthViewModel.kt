package dev.ugscheduler.ui.auth

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.ugscheduler.shared.data.Student

class AuthViewModelFactory(): ViewModelProvider.NewInstanceFactory() {

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

    fun onSignIn() {}

    fun onSignOut() {}

    fun onCancel() {}

    fun onProfileClicked() {}
}
