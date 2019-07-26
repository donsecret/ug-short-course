package io.codelabs.ugcloudchat

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalContactsProvider private constructor(context: Context) {

    suspend fun getLocalContacts() = withContext(Dispatchers.IO) {
        
    }

    companion object {
        @Volatile
        private var instance: LocalContactsProvider? = null

        /**
         * Get singleton instance of the local contacts provider class
         */
        fun getInstance(context: Context): LocalContactsProvider = instance ?: synchronized(this) {
            instance ?: LocalContactsProvider(context).also { instance = it }
        }
    }
}