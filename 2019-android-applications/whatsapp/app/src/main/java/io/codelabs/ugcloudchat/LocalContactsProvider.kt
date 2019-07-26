package io.codelabs.ugcloudchat

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalContactsProvider private constructor(context: Context) :
    LoaderManager.LoaderCallbacks<Cursor> {
    
    /**
     * Returns all the local contacts on the user's phone
     */
    suspend fun getLocalContacts() = withContext(Dispatchers.IO) {

    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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