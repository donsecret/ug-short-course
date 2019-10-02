package dev.csshortcourse.assignmenttwo.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.AndroidViewModel
import dev.csshortcourse.assignmenttwo.util.debugger
import dev.csshortcourse.assignmenttwo.viewmodel.repository.AppRepository

/**
 * ViewModel instance
 */
open class AppViewModel(app: Application) : AndroidViewModel(app) {
    // Create repository instance
    protected val repo: AppRepository by lazy { AppRepository.get(app) }

    // Connectivity Manager
    private val connectivityManager: ConnectivityManager by lazy { app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }

    // Global connection variables
    private var isConnected: Boolean = false
    private var monitoring: Boolean = false

    // Check network info
    protected fun isConnected(): Boolean {
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }

    init {
        registerNetworkListener()
    }

    private fun registerNetworkListener() {
        monitoring = true
        debugger("Registering network listener")

    }

    private fun unregisterNetworkListener() {
        monitoring = false
        debugger("Unregistering network listener")
    }

    override fun onCleared() {
        super.onCleared()
        unregisterNetworkListener()
    }

}