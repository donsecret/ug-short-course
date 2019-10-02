package dev.csshortcourse.assignmenttwo.view.ui.chat

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.viewmodel.AppViewModel
import kotlinx.coroutines.launch

class ChatViewModel(val app: Application) : AppViewModel(app) {

    private val _users = MutableLiveData<MutableList<User>>().apply {
        /*val user1 = User("Samuel Antwi")
        val user2 = User("Isaac Lemar")
        val user3 = User("Eunice Agyei")
        val user4 = User("Leslie Tetteh")
        val userList = mutableListOf<User>()
        userList.add(user1)
        userList.add(user2)
        userList.add(user3)
        userList.add(user4)

        // Pass to live data
        this.value = userList*/

        viewModelScope.launch {
            this@apply.value = repo.getUsers(isConnected())
        }
    }

    // Live user's list to observe
    val users: LiveData<MutableList<User>> = _users
}