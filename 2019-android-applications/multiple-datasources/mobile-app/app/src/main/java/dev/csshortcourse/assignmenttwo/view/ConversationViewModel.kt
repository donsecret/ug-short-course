package dev.csshortcourse.assignmenttwo.view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.csshortcourse.assignmenttwo.model.Chat
import dev.csshortcourse.assignmenttwo.model.User
import dev.csshortcourse.assignmenttwo.viewmodel.AppViewModel
import kotlinx.coroutines.launch
import java.util.*

class ConversationViewModel(app: Application) : AppViewModel(app) {

    fun getConversation(): LiveData<MutableList<Chat>> {
        val conversation = MutableLiveData<MutableList<Chat>>()
        viewModelScope.launch {
            conversation.postValue(repo.getMyChats(isConnected))
        }
        return conversation
    }

    fun addMessage(sender: String, recipient: String, message: String) {
        val chat = Chat(UUID.randomUUID().toString(), sender, recipient, message)
        viewModelScope.launch {
            repo.addMessage(chat)
        }
    }

    fun getUser(id: String): LiveData<User> {
        val liveUser = MutableLiveData<User>()
        viewModelScope.launch {
            liveUser.postValue(repo.getUser(isConnected, id))
        }
        return liveUser
    }
}