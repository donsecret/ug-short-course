package dev.csshortcourse.assignmenttwo.view.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.csshortcourse.assignmenttwo.model.User

class ChatViewModel : ViewModel() {

    private val _users = MutableLiveData<MutableList<User>>().apply {
        val user1 = User("Samuel Antwi")
        val user2 = User("Isaac Lemar")
        val user3 = User("Eunice Agyei")
        val user4 = User("Leslie Tetteh")
        val userList = mutableListOf<User>()
        userList.add(user1)
        userList.add(user2)
        userList.add(user3)
        userList.add(user4)

        // Pass to live data
        this.value = userList
    }
    val users: LiveData<MutableList<User>> = _users
}