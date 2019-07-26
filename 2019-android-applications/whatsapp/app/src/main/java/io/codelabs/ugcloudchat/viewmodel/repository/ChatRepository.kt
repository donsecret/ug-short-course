package io.codelabs.ugcloudchat.viewmodel.repository

import io.codelabs.ugcloudchat.model.database.UGCloudChatDao
import io.codelabs.ugcloudchat.model.preferences.UserSharedPreferences

class ChatRepository private constructor(
    private val dao: UGCloudChatDao,
    private val prefs: UserSharedPreferences
) {


    companion object {
        @Volatile
        private var instance: ChatRepository? = null

        fun getInstance(dao: UGCloudChatDao, prefs: UserSharedPreferences): ChatRepository = instance ?: synchronized(this) {
            instance ?: ChatRepository(dao, prefs).also { instance = it }
        }
    }
}