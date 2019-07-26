package io.codelabs.ugcloudchat.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.codelabs.ugcloudchat.model.Chat
import io.codelabs.ugcloudchat.viewmodel.repository.ChatRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatViewModel constructor(
    application: Application,
    private val repository: ChatRepository
) : AndroidViewModel(application) {
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    fun getMyChatsWith(uid: String) = repository.getMyChatsWith(uid)

    fun sendMessage(chat: Chat) = ioScope.launch { repository.sendMessage(chat) }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}